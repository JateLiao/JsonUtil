/*
 * 文件名：ReflectHelper.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ReflectHelper.java
 * 修改人：tianzhong
 * 修改时间：2016年11月9日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.helper;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * 反射相关的一些工具集.
 * 
 * @author     tianzhong
 */
@SuppressWarnings("rawtypes")
public class ReflectUtil {
    
    /**
     * Type -> Class
     */
    public static Class getClass(Type type, int i) {
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
    

    /**
     * TODO 获取setter方法.
     */
    public static <T> Method createSetterMethod(T t, String fieldName, Class clazz) throws NoSuchMethodException, SecurityException {
        return t.getClass().getMethod(getSetterMethodName(fieldName), clazz);
    }

    /**
     * TODO 构造Setter方法名.
     */
    public static String getSetterMethodName(String fieldName) {
        char[] cs = fieldName.toCharArray();
        cs[0] -= 32; // 方法首字母大写
        return "set" + String.valueOf(cs);

    }
    
    /**
     * TODO 获取泛型字段内的泛型参数类型数组。比如Map<String, Integer>则获取到String，Integer的Class数组.
     */
    public static Class[] getGeneriParamsFromField(Field fd) {
        Type[] params = ((ParameterizedType)fd.getGenericType()).getActualTypeArguments();
        Class[] cls = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Class) {
                cls[i] = (Class) params[i];
            }
        }
        return cls;
    }

    /**
     * TODO 判断这个类型是否是泛型类型，比如：List<E>这样的类型.
     * 
     * @param type 类型.
     * @return true/false.
     */
    public static boolean isGeneicType(Type type) {
        return type instanceof ParameterizedType;
    }
    
    /**
     * TODO 判断这个字段是否是泛型类型字段，比如 <T> T obj.
     * 
     * @param type .
     * @return .
     */
    public static boolean isGenericTypeField(Type type) {
        return type instanceof Object && type instanceof TypeVariable;
    }
    
    /**
     * TODO 重载：判断这个字段是否是泛型类型字段，比如 <T> T obj.
     * 
     * @param field .
     * @return .
     */
    public static boolean isGenericTypeField(Field field) {
        return !(field.getType() == field.getGenericType());
    }
}
