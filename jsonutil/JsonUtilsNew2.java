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
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 利用反射生成json，以及解析json.
 * 
 * @author tianzhong
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class JsonUtilsNew2 {

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
     * @return
     */
    public static String toJson(Object obj) {
        StringBuffer sb = new StringBuffer();
        constructJsonValue(obj, sb);
        deleteExtraComma(sb); // 干掉多余的逗号
        return sb.toString();
    }

    private static void constructJsonValue(Object obj, StringBuffer sb) {
        if (isNullOrEmpty(obj)) {
            return;
        }

        try {
            Class objClass = obj.getClass();

            if (isBasicType(objClass)) { // 四类八种基本类型，以及String
                if (objClass == int.class || objClass == short.class || objClass == Short.class || objClass == Integer.class || objClass == BigDecimal.class || objClass == boolean.class
                        || objClass == Boolean.class) {
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
            } else if (isListType(objClass)) { // List类型
                sb.append("[");
                List<Object> listObj = (List<Object>) obj;
                for (Object o : listObj) {
                    constructJsonValue(o, sb);
                    sb.append(",");
                }
                sb.append("],");
            } else if (isDefinedModel(objClass)) { // 其他自定义类型
                sb.append("{");
                Field[] fields = objClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (isNullOrEmpty(field.get(obj))) {
                        field.setAccessible(false);
                        continue;
                    }

                    sb.append("\"").append(field.getName()).append("\":");
                    constructJsonValue(field.get(obj), sb);
                    field.setAccessible(false);
                }
                sb.append("},");
            } else if (isMapType(objClass)) {
                sb.append("{");

                Map<Object, Object> mapObj = (Map<Object, Object>) obj;
                for (Map.Entry<Object, Object> entry : mapObj.entrySet()) {
                    sb.append("\"").append(entry.getKey().toString()).append("\":");
                    constructJsonValue(entry.getValue(), sb);
                }

                sb.append("},");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 干掉多余的逗号
     */
    private static void deleteExtraComma(StringBuffer sb) {
        String str = sb.toString();
        sb.setLength(0);
        sb.append(str.replaceAll(",,", ",").replaceAll(",]", "]").replaceAll(",}", "}"));
        sb.setLength(sb.length() - 1);
    }

    
    
    /*********************************************** 分 割 线 : 公共方法  ****************************************************************************************************************/
    
    /**
     * 是否为List类型
     */
    private static boolean isListType(Class<?> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为Map类型
     */
    private static boolean isMapType(Class<?> clazz) {
        if (Map.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是基本类型:四类八种，String，Date，BigDecimal.
     * 
     */
    private static boolean isBasicType(Class type) throws IllegalArgumentException, IllegalAccessException {
        if (type.isPrimitive() || type == String.class || type == BigDecimal.class || type == Date.class || type == Integer.class || type == Boolean.class 
                || type == Double.class || type == Short.class || type == Long.class || type == Float.class || type == Character.class) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否是自定义model.
     */
    private static boolean isDefinedModel(Class clazz) {
        if (clazz.getName().startsWith("java")) {
            return false;
        }

        return true;
    }

    /**
     * 判空
     */
    private static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            if (((String) obj).isEmpty()) {
                return true;
            }
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            if (((Collection) obj).isEmpty()) {
                return true;
            }
        } else if (Map.class.isAssignableFrom(obj.getClass())) {
            if (((Map) obj).isEmpty()) {
                return true;
            }
        }

        return false;
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
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        T t = null;

        try {
            // t = clazz.newInstance();
            if (isBasicType(clazz)) {
                return (T) instanceBasicObject(json, null, clazz);
            } else if (isDefinedModel(clazz)) {
                t = (T) instanceModelObject(json, clazz);
            }/* else if (isListType(clazz)) {
                t = (T) instanceListObject(json, clazz, t);
            } else if (isMapType(clazz)) {
                t = (T) instanceMapObject(json, clazz, t);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
    
    /**
     * TODO 重载：针对List，以及其他自定义泛型.
     * 
     * @param json json.
     * @param clazz 外层类型.
     * @param genericClazz 泛型参数类型.
     * @return obj.
     */
    public static <T> T toObject(String json, Class<T> clazz, Class genericClazz) {
        T t = null;
        
        try {
            instanceGenericObject(json, clazz, genericClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return t;
    }
    
    /**
     * TODO 重载：针对Map.
     * 
     * @param json json.
     * @param clazz 外层类型.
     * @param keyClazz 泛型参数类型1.
     * @param valueClazz 泛型参数类型2.
     * @return obj.
     */
    public static <T> T toObject(String json, Class<T> clazz, Class keyClazz, Class valueClazz) {
        
        if (!isMapType(clazz)) {
            return null;
        }
        
        T t = null;
        
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return t;
    }

    /**
     * 基本字段类型值构造.
     */
    private static Object instanceBasicObject(String json, String fieldName, Class clazz) throws Exception {
        Object obj = null;
        if (null != fieldName && !"".equals(fieldName)) {
            String tmpValue = null;
            switch (clazz.getName()) {
                case "java.util.Date":
                    tmpValue = getFieldStr4Basic(json, fieldName, true);
                    if (null == tmpValue) {
                        return null;
                    }
                    if (10 == tmpValue.trim().length()) { // 2016-01-01
                        tmpValue += " 00:00:00";
                    }
                    obj = df.parse(tmpValue);
                    break;
                case "java.math.BigDecimal":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue || "".equals(tmpValue) || "null".equals(tmpValue)) {
                        return null;
                    }
                    obj = new BigDecimal(tmpValue);
                    break;
                case "java.lang.String":
                    tmpValue = getFieldStr4Basic(json, fieldName, true);
                    if (null == tmpValue) {
                        return null;
                    }
                    obj = tmpValue;
                    break;
                case "java.lang.Integer":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue || "".equals(tmpValue) || "null".equals(tmpValue)) {
                        return null;
                    }
                    obj = Integer.valueOf(tmpValue);
                    break;
                case "int":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return 0;
                    }
                    obj = Integer.valueOf(tmpValue);
                    break;
                case "java.lang.Boolean":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return null;
                    }
                    obj = Boolean.valueOf(tmpValue);
                    break;
                case "boolean":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return false;
                    }
                    obj = Boolean.valueOf(tmpValue);
                    break;
                case "java.lang.Short":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return null;
                    }
                    obj = Short.valueOf(tmpValue);
                    break;
                case "short":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return 0;
                    }
                    obj = Short.valueOf(tmpValue);
                    break;
                case "java.lang.Byte":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return null;
                    }
                    obj = Byte.valueOf(tmpValue);
                    break;
                case "byte":
                    tmpValue = getFieldStr4Basic(json, fieldName, false);
                    if (null == tmpValue) {
                        return 0;
                    }
                    obj = Byte.valueOf(tmpValue);
                    break;
                default:
                    break;
            }
        }

        return obj;
    }
    
    
    // 数字（包括浮点，正负）：\"pageSize\":-?\\d+(\\.?\\d?),?
    // 字符串类型：\"keyID\":\".*?\", 

    /**
     * 自定义Model字段类型值构造.
     */
    private static Object instanceModelObject(String json, Class clazz) throws Exception {
        if (null == json || "".equals(json)) {
            return null;
        }
        Object localObj = clazz.newInstance(); 

        Field[] fds = clazz.getDeclaredFields();
        for (Field fd : fds) {
            Class fdClz = fd.getType();
            Object obj = null;

            if (isBasicType(fdClz)) {
                obj = instanceBasicObject(json, fd.getName(), fdClz);
            } else if (isDefinedModel(fdClz)) {
                String modelJson = getFieldStr4Others(json, fd.getName(), "{");
                obj = instanceModelObject(modelJson, fdClz);
            } else if (isListType(fdClz)) {
                String listJson = getFieldStr4Others(json, fd.getName(), "[");
                obj = instanceGenericObject(listJson, List.class, getClass(fd.getGenericType(), 0));
            } else if (isMapType(fdClz)) {
                String mapJson = getFieldStr4Others(json, fd.getName(), "{");
                obj = instanceMapObject(mapJson, fdClz, localObj);
            }

            Method method = createSetterMethod(localObj, fd.getName(), fdClz);
            method.invoke(localObj, obj);
        }

        // Method method = createMethod(t, fd.getName(), fdClz);
        // method.invoke(localObj, obj);
        // t = (T) localObj;
        return localObj;
    }

    /**
     * List以及泛型类型字段类型值构造.
     */
    private static Object instanceGenericObject(String json, Class rawClazz, Class genericClazz) throws Exception {
        // 进入这个方法，json的格式：["":"",{},[]]
        if (null == json || "".equals(json)) {
            return null;
        }
        Object obj = null;
        
        if (isListType(rawClazz)) { // List类型
            if (isBasicType(genericClazz)) { // ["","",""]
                obj = buildBasicTypeList(json, genericClazz);
            } else if (isDefinedModel(genericClazz)) {
                List<Object> objList = new ArrayList<Object>();
                // getFieldStr4Others(json, "", "[");
                String[] valArr = getSingleStringValueArr(json.substring(1, json.length() - 1));
                for (String val : valArr) {
                    objList.add(instanceModelObject(val, genericClazz));
                }
                obj = objList;
            } else if (isListType(genericClazz)) {
                
            } else if (isMapType(genericClazz)) {
                
            }
        } else { // 其他普通泛型类型
            if (isBasicType(genericClazz)) {
                
            } else if (isDefinedModel(genericClazz)) {
                
            } else if (isListType(genericClazz)) {
                
            } else if (isMapType(genericClazz)) {
                
            }
        }
        
        return obj;
    }

    /**
     * 获得单个String值得数组.
     * 
     * [{},{},{}]
     * 
     * 注意：String字段的值内嵌引号和"},{"时的处理
     */
    private static String[] getSingleStringValueArr(String json) {
        List<String> valList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        boolean existYinhao = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            existYinhao = ('"' == c && '\\' != json.charAt(i - 1)) ? !existYinhao : existYinhao;
            if (i == json.length() - 1 || (',' == c && '}' == json.charAt(i - 1) && '{' == json.charAt(i + 1) && !existYinhao)) {
                sb.append((i == json.length() - 1) ? c : ""); // 拼接最后一个字符
                valList.add(sb.toString());
                sb.setLength(0);
                continue;
            }
            sb.append(c);
        }
        
        String[] valArr = new String[valList.size()];
        return valList.toArray(valArr);
    }

    /**
     * TODO 添加方法注释.
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
    private static void buildBasicStringList(List<Object> objList, String value) {
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
     * Map类型值构造.
     */
    private static <T> Object instanceMapObject(String json, Class clazz, T t) {
     // 进入这个方法，json的格式：{"":"",{},[]}
        if (null == json || "".equals(json)) {
            return null;
        }
        t = (T) new HashMap<Object, Object>();
        
        
        
        return t;
    }

    /**
     * TODO 获取setter方法.
     */
    private static <T> Method createSetterMethod(T t, String fieldName, Class clazz) throws NoSuchMethodException, SecurityException {
        return t.getClass().getMethod(getSetterMethodName(fieldName), clazz);
    }

    /**
     * TODO 构造Setter方法名.
     */
    private static String getSetterMethodName(String fieldName) {
        char[] cs = fieldName.toCharArray();
        cs[0] -= 32; // 方法首字母大写
        return "set" + String.valueOf(cs);

    }
    
    /**
     * TODO 获取对应的完整json传，针对基本类型及其包装类型之外的复杂类型.
     * 
     * @param str 传入字符串.
     * @param beginStr 开始字段.
     * @param beginSign 开始符号.
     * @return 对应的完整的字符串.
     */
    private static String getFieldStr4Others(String str, String beginStr, String beginSign) {
        StringBuffer sb = new StringBuffer();
        String endSign = "{".equals(beginSign) ? "}" : "]"; // 结束标记 
        int count = 1; // 开始标记计数
        boolean existsYinhao = false; // 存在前引号
        String tmp = "\"" + beginStr + "\":" + beginSign;
        int index = str.indexOf(tmp);
        if (-1 == index && !"".equals(beginStr)) {
            return null;
        } else if (-1 == index && "".equals(beginStr)) {
            index = 0;
        } else {
            sb.append(beginSign);
            index += tmp.length();
        }
        
        // index = (-1 == index && "".equals(beginStr)) ? 0 : index + tmp.length();
        
        while (index < str.length() && count > 0) {
            char c = str.charAt(index);
            tmp = String.valueOf(c);
            sb.append(c);
            existsYinhao = ('"' == c && '\\' != str.charAt(index - 1)) ? !existsYinhao : existsYinhao;
            // existsYinhao = "\"".equals(tmp) ? !existsYinhao : existsYinhao;
            if (!existsYinhao) { // 引号内的"}","]"不计入统计
                count = beginSign.equals(tmp) ? count + 1 : (endSign.equals(tmp) ? count - 1 : count);
            }
            index++;
        }
        // sb.append(("".equals(beginStr)) ? str.charAt(index - 1) : "");
        
        return sb.toString();
    }
    
    /**
     * TODO 获取对应的完整json传，针对基本类型.
     * 
     * 注意：String类型值内的引号，{，,，[等符号的处理
     * 
     * @param str 传入字符串.
     * @param fieldName 正则.
     * @param needYinhao 是否需要引号
     *        String，Char，Date等类型的值是引号包含的；Integer，Double等类型的值是没有引号包围的 .
     * @return 对应的完整的字符串.
     */
    private static String getFieldStr4Basic(String str, String fieldName, boolean needYinhao) {
        String regex = "\"" + fieldName + "\":";
        String result = null;
        // Pattern pattern = needYinhao ? Pattern.compile(regex + "\".*?\"") : Pattern.compile(regex + ".+?(,|\\}|\\])");
        // Matcher matcher = pattern.matcher(str);
        // while (matcher.find()) {
        // result = matcher.group(0);
        // result = result.substring((needYinhao ? result.indexOf(regex) + 1 : result.indexOf(regex)) + regex.length(), result.length() - 1);
        // break;
        // }
        
        /** 新逻辑 **/
        StringBuffer sb = new StringBuffer();
        boolean existsYinhao = false;
        int index = str.indexOf(regex);
        if (-1 == index) {
            return result;
        }
        index += regex.length();
        for (int i = index; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(c);
            existsYinhao = ('"' == c && '\\' != str.charAt(i - 1)) ? !existsYinhao : existsYinhao;
            if (!existsYinhao) {
                break;
            }
        }
        result = sb.toString();
        if ('"' == result.charAt(0) && '"' == result.charAt(result.length() - 1)) {
            result = result.substring(1, result.length() - 1);
        }
        
        return result;
    }

    /**
     * Type -> Class
     */
    private static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    } 
    private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }
}
