/*
 * 文件名：JsonUtils.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonUtils.java
 * 修改人：tianzhong
 * 修改时间：2016年7月14日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.better517na.forStudy.advanced.reflect.jsonutil.exception.JsonUtilException;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.CommonUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.ReflectUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeContainer;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.SingleJSon;

/**
 * TODO 利用反射生成json，以及解析json.
 * 
 * @author tianzhong
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class JsonUtilsNew3 {

    /**
     * 日期格式化.
     */
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
    
    /*********************************************** 分 割 线 : Model -> Json  ****************************************************************************************************************/
    
    /**
     * 
     * TODO 简陋的json转化.
     * 
     * @param obj
     * @return .
     * @throws Exception .
     */
    public static String toJson(Object obj) {
        String res = null;
        try {
            paramNullCheck(obj);
            StringBuffer sb = new StringBuffer();
            constructJsonValue(obj, sb);
            CommonUtil.deleteExtraComma(sb); // 干掉多余的逗号
            res = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            res = "";
        }
        return res;
    }

    private static void constructJsonValue(Object obj, StringBuffer sb) throws Exception {
        if (CommonUtil.isNullOrEmpty(obj)) {
            return;
        }

        Class objClass = obj.getClass();

        if (CommonUtil.isBasicType(objClass)) { // 四类八种基本类型，以及String
            if (objClass == int.class || objClass == short.class || objClass == Short.class || objClass == Integer.class || objClass == BigDecimal.class 
                    || objClass == boolean.class || objClass == Boolean.class || objClass == Double.class || objClass == double.class) {
                sb.append(obj).append(",");
            } else if (objClass == Date.class) {
                sb.append("\"" + df.format(obj) + "\",");
            } else { // 注意字符串内的引号处理
                if (String.class == objClass) {
                    String value = (String) obj;
                    value = value.replaceAll("\"", "\\\\\"");
                    obj = value;
                }
                sb.append("\"").append(obj).append("\",");
            }
        } else if (CommonUtil.isListType(objClass)) { // List类型
            sb.append("[");
            List<Object> listObj = (List<Object>) obj;
            for (Object o : listObj) {
                constructJsonValue(o, sb);
                sb.append(",");
            }
            sb.append("],");
        } else if (CommonUtil.isDefinedModel(objClass)) { // 其他自定义类型
            sb.append("{");
            Field[] fields = ReflectUtil.getAllFieldsArr(objClass);
            for (Field field : fields) {
                field.setAccessible(true);
                // 存在内部类时，过滤掉默认保留的this$0对象
                if (field.getName().matches(".*this\\$[0-9].*")) {
                    System.out.println();
                    continue;
                }
                if (CommonUtil.isNullOrEmpty(field.get(obj))) {
                    field.setAccessible(false);
                    continue;
                }

                sb.append("\"").append(field.getName()).append("\":");
                constructJsonValue(field.get(obj), sb);
                field.setAccessible(false);
            }
            sb.append("},");
        } else if (CommonUtil.isMapType(objClass)) {
            sb.append("{");

            Map<Object, Object> mapObj = (Map<Object, Object>) obj;
            for (Map.Entry<Object, Object> entry : mapObj.entrySet()) {
                sb.append("\"").append(entry.getKey().toString()).append("\":");
                constructJsonValue(entry.getValue(), sb);
            }

            sb.append("},");
        }
    }

    /*********************************************** 分 割 线 : Json -> Model ****************************************************************************************************************/

    /**
     * TODO Json转Model,同样很简陋.
     * 
     * 提取每个节点，比如List则提取[...], model或者Map则提取{...}
     * 
     * @param json 参数.
     * @param clazz 目标类型.
     * @return object.
     * @throws Exception .
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        T t = null;

        try {
            paramNullCheck(clazz);
            // t = clazz.newInstance();
            if (CommonUtil.isBasicType(clazz)) {
                return (T) instanceBasicObject(json, null, clazz);
            } else if (CommonUtil.isDefinedModel(clazz)) {
                t = (T) instanceModelObject(json, clazz);
            }/* else if (isListType(clazz)) {
                t = (T) instanceListObject(json, clazz, t);
            } else if (isMapType(clazz)) {
                t = (T) instanceMapObject(json, clazz, t);
            }*/
        } catch (Exception e) {
            t = null;
            e.printStackTrace();
        }

        return t;
    }
    
    /**
     * TODO 重载：针对List,Map,以及其他自定义泛型.
     * 
     * 适用于：存在多个泛型参数的model转换，并且参数类型不再含有泛型结构
     * 
     * @param json json.
     * @param clazz 外层类型.
     * @param genericClazz 泛型参数类型.
     * @return obj.
     * @throws Exception .
     */
    public static <T> T toObject(String json, Class<T> clazz, Class... genericClazzs) {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        T t = null;
        
        try {
            paramNullCheck(clazz, genericClazzs);
            // 判断泛型参数的个数
            if (clazz.getTypeParameters().length != genericClazzs.length) {
                throw new JsonUtilException("泛型参数的数量与定义的" + clazz.getName() + "的泛型参数数量不一致!");
            }
            // ParameterizedType parameterizedType = new MyParameterizedType(clazz, genericClazzs);
            t = (T) instanceGenericObject(json, clazz, genericClazzs);
        } catch (Exception e) {
            t = null;
            e.printStackTrace();
        }
        
        return t;
    }
    
    /**
     * TODO 重载：针对List,Map,以及其他自定义泛型，支持混合的泛型类型，泛型嵌套，本质上这个方法也是为泛型类型做的.
     * 
     * 比如：ClassA<K, T, V> ==> ClassA<ClassB, List<ClassB>, Map<String, ClassB>>
     * 
     * @param json json.
     * @param clazz 外层类型.
     * @param genericClazz 泛型参数类型.
     * @return obj.
     * @throws Exception .
     */
    public static <T> T toObject(String json, Class<T> clazz, Type... genericClazzs) {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        Object t = null;
        try {
            paramNullCheck(clazz, genericClazzs);
            t = instanceGenericObject(json, clazz, genericClazzs);
        } catch (Exception e) {
            t = null;
            e.printStackTrace();
        }
        return clazz.cast(t);
    }
    
    /**
     * TODO 支持TypeToken.
     * 
     * 比如：ClassA<K, T, V> ==> ClassA<ClassB, List<ClassB>, Map<String, ClassB>>
     * 
     * @param json json.
     * @param clazz 外层类型.
     * @param genericClazz 泛型参数类型.
     * @return obj.
     * @throws Exception .
     */
    public static <T> T toObject(String json, Type... types) {
        return toObject(json, (Class<T>) types[0], Arrays.copyOfRange(types, 1, types.length));
    }
    
    /***************************************************************************************************************************************************/

    /**
     * 基本字段类型值构造.
     */
    private static Object instanceBasicObject(String json, String fieldName, Class clazz) throws Exception {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(clazz);
        Object obj = null;

        String tmpValue = null;
        switch (clazz.getName()) {
            case "java.util.Date":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json.substring(1, json.length() - 1) : CommonUtil.getFieldStr4Basic(json, fieldName, true);
                if (10 == tmpValue.trim().length()) { // 2016-01-01
                    tmpValue += " 00:00:00";
                }
                obj = df.parse(tmpValue);
                break;
            case "java.math.BigDecimal":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue || "".equals(tmpValue) || "null".equals(tmpValue)) {
                    return null;
                }
                obj = new BigDecimal(tmpValue);
                break;
            case "java.lang.String":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json.substring(1, json.length() - 1) : CommonUtil.getFieldStr4Basic(json, fieldName, true);
                tmpValue = tmpValue.contains("\\") ? tmpValue.replaceAll("\\\\", "") : tmpValue;
                if (null == tmpValue) {
                    return null;
                }
                obj = "\"\"".equals(tmpValue) ? "" : tmpValue;
                break;
            case "java.lang.Integer":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue || "".equals(tmpValue) || "null".equals(tmpValue)) {
                    return null;
                }
                obj = Integer.valueOf(tmpValue);
                break;
            case "int":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return 0;
                }
                obj = Integer.valueOf(tmpValue);
                break;
            case "java.lang.Boolean":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return null;
                }
                obj = Boolean.valueOf(tmpValue);
                break;
            case "boolean":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return false;
                }
                obj = Boolean.valueOf(tmpValue);
                break;
            case "java.lang.Short":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return null;
                }
                obj = Short.valueOf(tmpValue);
                break;
            case "short":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return 0;
                }
                obj = Short.valueOf(tmpValue);
                break;
            case "java.lang.Byte":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json.substring(1, json.length() - 1) : CommonUtil.getFieldStr4Basic(json, fieldName, true);
                if (null == tmpValue) {
                    return null;
                }
                obj = Byte.valueOf(tmpValue);
                break;
            case "byte":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return 0;
                }
                obj = Byte.valueOf(tmpValue);
                break;
            case "java.lang.Double":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return null;
                }
                obj = Double.valueOf(tmpValue);
                break;
            case "double":
                tmpValue = CommonUtil.isNullOrEmpty(fieldName) ? json : CommonUtil.getFieldStr4Basic(json, fieldName, false);
                if (null == tmpValue) {
                    return 0.0;
                }
                obj = Double.valueOf(tmpValue);
                break;
            default:
                break;
        }
    

        return obj;
    }
    
    
    // 数字（包括浮点，正负）：\"pageSize\":-?\\d+(\\.?\\d?),?
    // 字符串类型：\"keyID\":\".*?\", 

    /**
     * 自定义Model字段类型值构造.
     */
    private static Object instanceModelObject(String json, Class clazz) throws Exception {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(clazz);
        Object localObj = clazz.newInstance(); 

        Map<String, SingleJSon> sinMap = CommonUtil.getSingleJsonValueByMap(json);
        Field[] fds = ReflectUtil.getAllFieldsArr(clazz); // clazz.getDeclaredFields();
        for (Field fd : fds) {
            Class fdClz = fd.getType();
            Object obj = null;

            if (CommonUtil.isBasicType(fdClz)) {
                obj = instanceBasicObject(json, fd.getName(), fdClz);
            } else if (CommonUtil.isDefinedModel(fdClz)) {
                String modelJson = sinMap.get(fd.getName()).getFieldValue(); // CommonUtil.getFieldStr4Others(json, fd.getName(), "{");
                obj = instanceModelObject(modelJson, fdClz);
            } else if (ReflectUtil.isGeneicType(fd.getGenericType())) { // 字段是泛型类型 
                Class[] cls = ReflectUtil.getGeneriParamsFromField(fd);
                if (CommonUtil.isListType(fdClz)) {
                    String listJson = sinMap.get(fd.getName()).getFieldValue(); // CommonUtil.getFieldStr4Others(json, fd.getName(), "[");
                    obj = instanceGenericObject(listJson, List.class, cls); // getClass(fd.getGenericType(), 0)
                } else if (CommonUtil.isMapType(fdClz)) {
                    String mapJson = sinMap.get(fd.getName()).getFieldValue(); // CommonUtil.getFieldStr4Others(json, fd.getName(), "{");
                    // obj = instanceMapObject(mapJson, fdClz, localObj);
                    obj = instanceGenericObject(mapJson, fdClz, cls);
                } else { // 其他：自定义泛型类型
                    String modelJson = CommonUtil.getFieldStr4Others(json, fd.getName(), "{");
                    obj = instanceGenericObject(modelJson, fdClz, cls);
                }
            }

            Method method = ReflectUtil.createSetterMethod(localObj, fd.getName(), fdClz);
            method.invoke(localObj, obj);
        }
        
        return localObj;
    }
    
    /**
     * 泛型类型字段类型值构造.
     */
    private static Object instanceGenericObject(String json, Class rawClazz, Class[] genericClazz) throws Exception {
        // 进入这个方法，json的格式：["":"",{},[]] 
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(rawClazz, genericClazz);
        if (rawClazz.getTypeParameters().length > genericClazz.length) { // 取前面的类型，后面多余的忽略
            throw new JsonUtilException("泛型参数个数与真实所需个数不一致!");
        }
        Object obj = null;
        
        // 根据外层类型来做
        if (CommonUtil.isListType(rawClazz)) { // List类型
            if (CommonUtil.isBasicType(genericClazz[0])) { // ["","",""]
                obj = buildBasicTypeList(json, genericClazz[0]);
            } else if (CommonUtil.isDefinedModel(genericClazz[0])) {
                List<Object> objList = new ArrayList<Object>();
                String[] valArr = CommonUtil.getSingleStringValueArr(json.substring(1, json.length() - 1));
                for (String val : valArr) {
                    objList.add(instanceModelObject(val, genericClazz[0]));
                }
                obj = objList;
            }/* else if (CommonUtil.isListType(genericClazz[0])) { // List嵌套List
                String listJson = CommonUtil.getFieldStr4Others(json, "", "[");
                
            } else if (CommonUtil.isMapType(genericClazz[0])) { // List嵌套Map
                
            }*/
        } else if (CommonUtil.isMapType(rawClazz)) { // Map类型：key就不支持复杂类型了，不怎么实用，value支持更复杂的类型
            // {"":"",{}:{},"":{}}
            obj = instanceMapObject(json, genericClazz[0], genericClazz[1]);
        } else { // 其他普通泛型类型
            obj = buildCommonGenricObject(json, rawClazz, genericClazz);
        }
        
        return obj;
    }
    

    /**
     * 重载楼上的方法，以支持泛型的嵌套等较为复杂的泛型类型.
     * 
     * 确定泛型的字段(包括顺序)->获取对应的泛型类型->转obj
     */
    private static Object instanceGenericObject(String json, Class clazz, Type[] genericClazzs) throws Exception {
        Class clz = Class.forName(clazz.getName());
        // 实例化目标对象
        Object obj = null;
        if (CommonUtil.isListType(clazz)) {
            return instanceListObject(json, genericClazzs[0]);
            // obj = new ArrayList<>();
        } else if (CommonUtil.isMapType(clazz)) {
            return instanceMapObject(json, (Class) genericClazzs[0], (Class) genericClazzs[1]);
            // obj = new HashMap<>();
        } else {
            obj = clz.newInstance(); 
        }
        Map<String, Type> typeMap = new HashMap<>();
        TypeVariable[] types = clazz.getTypeParameters();
        
        if (types.length != genericClazzs.length) {
            throw new JsonUtilException("泛型参数个数与真实所需个数不一致!");
        }
        for (int i = 0; i < genericClazzs.length; i++) {
            typeMap.put(types[i].getName(), genericClazzs[i]);
        }
        Map<Field, Type> fieldMap = new HashMap<>(); // 存下字段对应的类型
        
        List<SingleJSon> singles = CommonUtil.getSingleJsonValue(json);
        Map<String, SingleJSon> sinMap = new HashMap<>();
        for (SingleJSon sin : singles) {
            sinMap.put(sin.getFieldName(), sin);
        }
        Field[] fds = ReflectUtil.getAllFieldsArr(clazz); // clazz.getDeclaredFields();
        for (Field fd : fds) {
            fd.setAccessible(true);
            
            Object tmpObj = null;
            if (ReflectUtil.isGenericTypeField(fd.getGenericType())) { // 是泛型字段
                String tmp = fd.toGenericString();
                fieldMap.put(fd, typeMap.get(tmp.substring(tmp.indexOf(" ") + 1, tmp.lastIndexOf(" "))));
                continue;
            }
            
            Class[] cls = null;
            if (CommonUtil.isListType(fd.getType()) || CommonUtil.isMapType(fd.getType())) {
                cls = ReflectUtil.getGeneriParamsFromField(fd);
            }
            tmpObj = instanceObject(sinMap.get(fd.getName()).getFieldValue(), fd, cls); // CommonUtil.getFieldStr4Others(json, fd.getName(), "[");
            
            fd.set(obj, tmpObj);
            
            fd.setAccessible(false);
        }
        
        // 处理泛型字段
        for (Entry<Field, Type> en : fieldMap.entrySet()) {
            Object tmpObj = null;
            Field fd = en.getKey();
            String currentJson = sinMap.get(fd.getName()).getFieldValue(); // 当前json
            fd.setAccessible(true);
            if (en.getValue() instanceof Class) { // 简单泛型
                Class type = (Class) en.getValue();
                Class[] cls = new Class[1];
                if (CommonUtil.isListType(type) || CommonUtil.isMapType(type)) {
                    cls = ReflectUtil.getGeneriParamsFromField(fd);
                } else { // if (en.getValue() instanceof Class) {
                    cls[0] = (Class) en.getValue();
                }
                tmpObj = instanceObject(currentJson, fd, true, type, cls);
            } else if (en.getValue() instanceof TypeContainer) { // 复杂泛型
                /*此处实为实现泛型嵌套的支持，若嵌套再嵌套，则递归*/
                TypeContainer cc = (TypeContainer) en.getValue();
                tmpObj = toObject(currentJson, (Class)cc.getRawType(), cc.getActualTypeArguments());
            }
            fd.set(obj, tmpObj);
            fd.setAccessible(false);
        }
        
        return obj;
    }
    
    /**
     * 普通的obj构建.
     */
    private static Object instanceObject(String json, Field field, Class...cls) throws Exception {
        Object obj = null;
        
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        
        if (CommonUtil.isBasicType(field.getType())) {
            String basicJson = "\"" + field.getName() + "\":";
            if (field.getType() == String.class || field.getType() == Date.class || field.getType() == Character.class || field.getType() == char.class) {
                basicJson += "\"" + json + "\"";
            } else {
                basicJson += CommonUtil.getFieldStr4Basic(json, field.getName(), false);
            }
            obj = instanceBasicObject(basicJson, field.getName(), field.getType());
        } else if (CommonUtil.isDefinedModel(field.getType())) {
            obj = instanceModelObject(json, field.getType());
        } else if (CommonUtil.isMapType(field.getType())) {
            obj = instanceMapObject(json, cls[0], cls[1]);
        } else if (CommonUtil.isListType(field.getType())) {
            obj = instanceListObject(json, cls[0]);
        }
        
        return obj;
    }
    
    /**
     * 普通的obj构建.
     */
    private static Object instanceObject(String json, Field field, Boolean isGeneric, Class rawClazz, Class...cls) throws Exception {
        Object obj = null;
        
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        
        if (CommonUtil.isBasicType(field.getType()) || (isGeneric && CommonUtil.isBasicType(rawClazz))) {
            String basicJson = "\"" + field.getName() + "\":";
            // json += "\"" + field.getName() + "\":";
            if (field.getType() == String.class || (isGeneric && rawClazz == String.class) 
                    || field.getType() == Date.class || (isGeneric && rawClazz == Date.class)
                    || field.getType() == Character.class || (isGeneric && rawClazz == Character.class)
                    || field.getType() == char.class || (isGeneric && rawClazz == char.class)) {
                basicJson += "\"" + json + "\"";
            } else {
                basicJson += json; // CommonUtil.getFieldStr4Basic(json, field.getName(), false);
            }
            obj = instanceBasicObject(basicJson, field.getName(), isGeneric ? rawClazz : field.getType());
        } else if (CommonUtil.isDefinedModel(field.getType()) || (isGeneric && CommonUtil.isDefinedModel(rawClazz))) {
            obj = instanceModelObject(json, isGeneric ? rawClazz : field.getType());
        } else if (CommonUtil.isMapType(field.getType()) || (isGeneric && CommonUtil.isMapType(rawClazz))) {
            obj = instanceMapObject(json, cls[0], cls[1]);
        } else if (CommonUtil.isListType(field.getType()) || (isGeneric && CommonUtil.isListType(rawClazz))) {
            obj = instanceListObject(json, cls[0]);
        }
        
        return obj;
    }
    
    /**
     * Map类型值构造.
     */
    private static Map instanceMapObject(String json, Type keyClazz, Type valueClazz) throws Exception {
        // 进入这个方法，json的格式：{"":"",{},[]}
        // key默认为String先，更复杂的key以后做支持
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(keyClazz, valueClazz);
        Map<Object, Object> map = new HashMap<>();
        
        List<SingleJSon> singles = CommonUtil.getSingleJsonValue(json);
        for (SingleJSon single : singles) {
            String key = single.getValue().substring(1, single.getColonIndex() - 1);
            Object valObj = null;
            if (valueClazz instanceof TypeContainer) {
                TypeContainer cc = (TypeContainer) valueClazz;
                Class rawClazz = null;
                Class[] generics = null;
                if (cc.getRawType() instanceof Class) {
                    rawClazz = (Class) cc.getRawClazz();
                }
                if (cc.getActualTypeArguments() instanceof Class[]) {
                    generics = (Class[]) cc.getActualTypeArguments();
                }
                valObj = toObject(single.getValue().substring(single.getColonIndex() + 1, single.getValue().length()), rawClazz, generics);
            } else if (valueClazz instanceof Class) {
                Class clazz = (Class) valueClazz;
                valObj = toObject(single.getValue().substring(single.getColonIndex() + 1, single.getValue().length()), clazz);
            }  
            map.put(key, valObj);
            
        }
        
        return map;
    }
    
    /**
     * TODO 其他普通泛型类型，不考虑泛型嵌套泛型的情况.
     * 
     * @param json .
     * @param rawClazz .
     * @param genericClazz .
     * @return Object.
     * @throws Exception . 
     */
    private static Object buildCommonGenricObject(String json, Class rawClazz, Class[] genericClazz) throws Exception {
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(rawClazz, genericClazz);
        // 确定反省参数个数->找出泛型字段->确定泛型字段类型->赋值
        Class clazz = Class.forName(rawClazz.getName());
        Object obj = clazz.newInstance(); 
        Map<String, Class> typeMap = new HashMap<>();
        TypeVariable[] types = rawClazz.getTypeParameters();
        if (types.length != genericClazz.length) {
            throw new JsonUtilException("泛型参数个数与真实所需个数不一致!");
        }
        for (int i = 0; i < genericClazz.length; i++) {
            typeMap.put(types[i].getName(), genericClazz[i]);
        }
        Map<Field, Class> fieldMap = new HashMap<>(); // 存下字段对应的类型
        
        Field[] fds = ReflectUtil.getAllFieldsArr(rawClazz); // rawClazz.getDeclaredFields();
        List<SingleJSon> singles = CommonUtil.getSingleJsonValue(json);
        Map<String, SingleJSon> sinMap = new HashMap<>();
        for (SingleJSon sin : singles) {
            sinMap.put(sin.getFieldName(), sin);
        }
        for (Field fd : fds) {
            fd.setAccessible(true);
            Object tmpObj = null;
            if (ReflectUtil.isGenericTypeField(fd.getGenericType())) {
                String tmp = fd.toGenericString();
                fieldMap.put(fd, typeMap.get(tmp.substring(tmp.indexOf(" ") + 1, tmp.lastIndexOf(" "))));
                continue;
            }
            
            Class[] cls = null;
            if (CommonUtil.isListType(fd.getType()) || CommonUtil.isMapType(fd.getType())) {
                cls = ReflectUtil.getGeneriParamsFromField(fd);
            }
            tmpObj = instanceObject(sinMap.get(fd.getName()).getFieldValue(), fd, cls);
            
            fd.set(obj, tmpObj);
            // Method method = ReflectHelper.createSetterMethod(obj, fd.getName(), fdClazz);
            // method.invoke(obj, tmpObj);
            fd.setAccessible(false);
        }
        
        // 处理泛型字段
        for (Entry<Field, Class> e : fieldMap.entrySet()) {
            e.getKey().setAccessible(true);
            Object tmpObj = null;
            
            Class clazzObj = e.getValue();
            if (CommonUtil.isBasicType(clazzObj)) {
                boolean isNeedYinhao = false;
                if (clazzObj == String.class || clazzObj == char.class || clazzObj == Character.class || clazzObj == Date.class) {
                    isNeedYinhao = true;
                }
                String basicJson = CommonUtil.getFieldStr4Basic(json, e.getKey().getName(), isNeedYinhao);
                basicJson = "\"" + e.getKey().getName() + "\":" + basicJson;
                tmpObj = instanceBasicObject(basicJson, e.getKey().getName(), clazzObj); 
            } else if (CommonUtil.isDefinedModel(rawClazz)) {
                String modelJson = CommonUtil.getFieldStr4Others(json, e.getKey().getName(), "{");
                tmpObj = instanceModelObject(modelJson, clazzObj);
            }
            
            e.getKey().set(obj, tmpObj);
            // Method method = ReflectHelper.createSetterMethod(obj, e.getKey().getName(), clazzObj);
            // method.invoke(obj, tmpObj);
            e.getKey().setAccessible(false);
        }
        
        return obj;
    }

    /**
     * TODO 构造List：默认ArrayList，实际上也只能做成ArrayList，懒得做成其他的了.
     * 
     * @param json .
     * @param class1 .
     * @return .
     */
    private static Object instanceListObject(String json, Type type) {
        List<Object> list = new ArrayList<>();
        List<String> singleJson = CommonUtil.getSingleJsonFromList(json);
        for (String single : singleJson) {
            // List<Object> tmpList = toObject(single, List.class, type);
            // list.add(tmpList.get(0));

            if (type instanceof Class) {
                list.add(toObject(single, (Class) type));
            } else if (type instanceof TypeContainer) {
                TypeContainer tc = (TypeContainer) type;
                list.add(toObject(single, (Class) tc.getRawClazz(), tc.getActualTypeArguments()));
            }
        }
        
        return list;
    }

    /**
     * TODO 构造基本类型的List.
     * 
     * @param json
     * @param genericClazz
     * @return
     * @throws Exception 
     */
    private static List<Object> buildBasicTypeList(String json, Class genericClazz) throws Exception {
        /**
         * ["",""]
         * [, ,]
         */
        if (CommonUtil.isNullOrEmpty(json)) {
            return null;
        }
        paramNullCheck(genericClazz);
        List<Object> objList = new ArrayList<>();
        String value = json.substring(1, json.length() - 1);
        if (value.length() < 1) {
            return objList;
        }
        
        if (Integer.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Integer.valueOf(str));
            }
        } else if (BigDecimal.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(new BigDecimal(str));
            }
        } else if (Date.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(df.parse(str));
            }
        } else if (Boolean.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Boolean.valueOf(str));
            }
        } else if (Double.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Double.valueOf(str));
            }
        } else if (Short.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Short.valueOf(str));
            }
        } else if (Long.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Long.valueOf(str));
            }
        } else if (Float.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Float.valueOf(str));
            }
        } else if (Character.class == genericClazz) {
            String[] valArr = value.split(",");
            for (String str : valArr) {
                objList.add(Character.valueOf(str.charAt(0)));
            }
        } else if (String.class == genericClazz) {
            buildBasicStringList(objList, value);
        } 
        
        return objList;
    }
    
    /**
     * TODO 构建List<String>.
     */
    private static void buildBasicStringList(List<Object> objList, String value) throws Exception {
        paramNullCheck(value);
        // "ac"s"ca","sdas,sads","joisc" 
        // 以引号作为阶段标准，而非逗号
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (i == value.length() - 1) {
                objList.add(sb.substring(0, sb.length()));
                break;
            }
            // if (',' == c && i >= 2 && '\\' != c && '"' != c && !"\\\"".equals(value.substring(i-2, i-1))) { // 到这里去到一个完整的String
            if (',' == c && i >= 2 && !('\\' == value.charAt(i - 2) && '"' == value.charAt(i - 1))) { // 到这里去到一个完整的String    
                objList.add(sb.substring(0, sb.length()));
                sb.setLength(0);
                continue;
            }
            sb.append(c);
        }
    }
    
    /**
     * TODO 参数校验.
     */
    private static void paramNullCheck(Object... objs) throws Exception {
        if (CommonUtil.isNullOrEmptyByAnyOne(objs)) {
            throw new JsonUtilException("必要参数不能为空");
        }
    }
}
