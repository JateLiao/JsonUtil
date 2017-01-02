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
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO 利用反射生成json，以及解析json.
 * 
 * @author tianzhong
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class JsonUtils {

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

        try {
            Class objClass = obj.getClass();

            if (isBasicType(objClass)) { // 四类八种基本类型，以及String
                sb.append("\"").append(obj.toString()).append("\",");
            } else if (isListType(objClass)) { // List类型
                sb.append("[");
                List<Object> listObj = (List<Object>) obj;
                for (Object o : listObj) {
                    constructJsonValue(o, sb);
                    sb.append(",");
                }
                sb.append("]");
            } else if (isDefinedModel(objClass)) { // 其他自定义类型
                sb.append("{");
                Field[] fields = objClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);

                    if (isBasicType(field.getType())) { // 四类八种基本类型，以及String
                        constructBasicTypeJsonValue(obj, field, sb, false);
                    } else if (isListType(field.getType())) { // List类型
                        constructListJsonValue(obj, field, sb, false);
                    } else if (isDefinedModel(field.getType())) { // 其他自定义类型
                        constructModelJsonValue(obj, field, sb, false);
                    }

                    field.setAccessible(false);
                }
                sb.append("}");
            } else if (isMapType(objClass)) {
                sb.append("{");

                sb.append("}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本类型.
     */
    private static void constructBasicTypeJsonValue(Object obj, Field field, StringBuffer sb, boolean isFromList) throws IllegalArgumentException, IllegalAccessException {
        Class clazz = isFromList ? obj.getClass() : field.getType();
        if (!isFromList) { // 不是来自List的字段，需要字段名
            String fieldName = field.getName();
            sb.append("\"" + fieldName + "\":");
        }

        if (!isFromList && isNullOrEmpty(field.get(obj))) { // 字段值为空
            sb.append(getDefaultValue(clazz) + ",");
            return;
        }

        if (clazz == Date.class) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            sb.append("\"" + dateFormat.format(isFromList ? obj : field.get(obj)) + "\",");
        } else if (clazz == BigDecimal.class || clazz == Boolean.class || clazz == boolean.class || clazz == Integer.class || clazz == int.class) {
            sb.append((isFromList ? obj : field.get(obj)) + ",");
        } else {
            sb.append("\"" + (isFromList ? obj : field.get(obj)) + "\",");
        }
    }

    /**
     * 自定义model转化.
     */
    private static void constructModelJsonValue(Object obj, Field field, StringBuffer sb, boolean isFromList) throws Exception {
        Class clazz = isFromList ? obj.getClass() : field.getType();

        if (!isFromList) { // 不是来自List的字段
            String fieldName = field.getName();
            sb.append("\"" + fieldName + "\":");
        }

        sb.append("{");

        if (isNullOrEmpty((isFromList ? obj : field.get(obj)))) {
            sb.append("},");
            return;
        }

        Field[] fds = clazz.getDeclaredFields();
        for (Field fd : fds) {
            fd.setAccessible(true);

            if (isBasicType(fd.getType())) { // 四类八种基本类型，以及String
                constructBasicTypeJsonValue((isFromList ? obj : field.get(obj)), fd, sb, false);
            } else if (isListType(fd.getType())) { // List类型
                constructListJsonValue((isFromList ? obj : field.get(obj)), fd, sb, false);
            } else if (isDefinedModel(fd.getType())) { // 其他自定义类型
                constructModelJsonValue((isFromList ? obj : field.get(obj)), fd, sb, false);
            }

            fd.setAccessible(false);
        }
        sb.append("},");
    }

    /**
     * List转化.
     */
    private static void constructListJsonValue(Object obj, Field field, StringBuffer sb, boolean isFromList) throws Exception {
        Object value = isFromList ? obj : field.get(obj);

        if (!isFromList) {
            String fieldName = field.getName();
            sb.append("\"" + fieldName + "\":");
        }

        sb.append("[");

        if (isNullOrEmpty(value)) {
            sb.append("],");
            return;
        }

        List<Object> list = (List<Object>) value;
        for (Object object : list) {
            Class clazz = object.getClass();
            if (isBasicType(clazz)) {
                constructBasicTypeJsonValue(object, null, sb, true);
            } else if (isDefinedModel(clazz)) {
                constructModelJsonValue(object, null, sb, true);
            } else if (isListType(clazz)) {
                constructListJsonValue(object, null, sb, true);
            }
        }

        sb.append("],");
    }

    /**
     * 干掉多余的逗号
     */
    private static void deleteExtraComma(StringBuffer sb) {
        String str = sb.toString();
        sb.setLength(0);
        sb.append(str.replaceAll(",,", ",").replaceAll(",]", "]").replaceAll(",}", "}"));
    }

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
        if (type.isPrimitive() || type == String.class || type == BigDecimal.class || type == Date.class || type == Integer.class) {
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
     * 获取基本类型对应的默认值.
     * 
     * 不需要：int,Integer,short,Short,boolean,Boolean,BigDecimal.
     */
    private static String getDefaultValue(Class clazz) {
        if (clazz == int.class || clazz == short.class || clazz == Short.class) {
            return "0";
        } else if (clazz == Integer.class || clazz == BigDecimal.class || clazz == Date.class || clazz == String.class) {
            return "null";
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            return "false";
        }

        return "\"\"";
    }

    /**
     * 判空
     */
    private static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            if (((String) obj).isEmpty()) {
                return true;
            }
        }

        if (Collection.class.isAssignableFrom(obj.getClass())) {
            if (((Collection) obj).isEmpty()) {
                return true;
            }
        }

        if (Map.class.isAssignableFrom(obj.getClass())) {
            if (((Map) obj).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /*********************************************** 分 割 线 ********************************************************************************************/

    /**
     * TODO Json转Model,同样很简陋.
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Class fieldClazz = field.getType();

                if (isBasicType(fieldClazz)) {
                    buildBasicTypeValue(json, field, t);
                } else if (isDefinedModel(fieldClazz)) {
                    buildModelTypeValue(json, field, t);
                } else if (isListType(fieldClazz)) {
                    buildListTypeValue(json, field, t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    /**
     * 基本字段类型值构造.
     */
    private static <T> void buildBasicTypeValue(String json, Field field, T t) throws Exception {
        String fieldName = field.getName();
        Class clazz = field.getType();
        // 正则提取字段值
        Matcher matcher = Pattern.compile("\"" + fieldName + "\":(.*?),").matcher(json);
        if (!matcher.find()) {
            return;
        }

        boolean isStrNull = false; // 标记String字段值为"null"字符串还是为空
        String valueStr = matcher.group(1);

        if (valueStr.length() >= 2) {
            // 去掉最后的"{" 和 "[", 解决方法待定
            String lastTwo = valueStr.substring(valueStr.length() - 2, valueStr.length()).replace("}", "").replace("]", "");
            valueStr = valueStr.substring(0, valueStr.length() - 2) + lastTwo;

            // 去掉首尾的引号
            if (valueStr.charAt(0) == '"' && valueStr.charAt(valueStr.length() - 1) == '"') {
                if (clazz == String.class) {
                    isStrNull = true;
                }
                valueStr = valueStr.substring(1, valueStr.length() - 1);
            }

            // 字段值为null的处理
            if ("null".equals(valueStr) && !isStrNull) {
                return;
            }
        }

        // 赋值
        Method method = null;
        switch (clazz.getName()) {
            case "java.util.Date":
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueStr);
                method = createMethod(t, fieldName, clazz);
                method.invoke(t, date);
                break;
            case "java.math.BigDecimal":
                method = createMethod(t, fieldName, clazz);
                method.invoke(t, new BigDecimal(valueStr));
                break;
            case "java.lang.String":
                method = createMethod(t, fieldName, clazz);
                method.invoke(t, valueStr);
                break;
            case "java.lang.Integer":
            case "int":
                method = createMethod(t, fieldName, clazz);
                method.invoke(t, new Integer(valueStr));
                break;
            case "java.lang.Boolean":
            case "boolean":
                method = createMethod(t, fieldName, clazz);
                method.invoke(t, ("true".equalsIgnoreCase(valueStr)) ? true : false);
                break;
            case "java.lang.Short":
            case "short":
                break;
            case "java.lang.Byte":
            case "byte":
                break;

            default:
                break;
        }
    }

    /**
     * 自定义Model字段类型值构造.
     */
    private static <T> void buildModelTypeValue(String json, Field field, T t) throws Exception {
        String fieldName = field.getName();
        Object obj = field.getType().newInstance();

        Field[] fds = obj.getClass().getDeclaredFields();
        for (Field fd : fds) {
            Class fdClazz = fd.getType();

            if (isBasicType(fdClazz)) {
                buildBasicTypeValue(json, fd, obj);
            } else if (isDefinedModel(fdClazz)) {
                buildModelTypeValue(json, fd, obj);
            } else if (isListType(fdClazz)) {
                buildListTypeValue(json, fd, obj);
            }
        }

        Method method = createMethod(t, fieldName, obj.getClass());
        method.invoke(t, obj);
    }

    /**
     * List字段类型值构造.
     */
    private static <T> void buildListTypeValue(String json, Field field, T t) throws Exception {
        // 获取List泛型参数类型
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Class paramClazz = (Class) pt.getActualTypeArguments()[0];

        // 开始一波递归
        if (isBasicType(paramClazz)) {

        } else if (isDefinedModel(paramClazz)) {

        } else if (isListType(paramClazz)) {

        }
    }

    /**
     * TODO 获取方法.
     */
    private static <T> Method createMethod(T t, String fieldName, Class clazz) throws NoSuchMethodException, SecurityException {
        return t.getClass().getMethod(getMethodName(fieldName), clazz);
    }

    /**
     * TODO 构造方法名.
     */
    private static String getMethodName(String fieldName) {
        char[] cs = fieldName.toCharArray();
        cs[0] -= 32; // 方法首字母大写
        return "set" + String.valueOf(cs);

    }
}
