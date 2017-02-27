/*
 * 文件名：TypeToken.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeToken.java
 * 修改人：tianzhong
 * 修改时间：2017年2月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author tianzhong
 */
public class TypeToken<T> {

    /**
     * 添加字段注释.
     */
    public T targetType;

    /**
     * 
     * TODO 辅助获取T.class.
     */
    public T tmpMethod() {
        return null;
    }
    
    /**
     * 构造函数.
     */
    public Type getGenericType() {
        System.out.println();
        
        // Type[] sss = ((ParameterizedType)new TypeToken<List<Map<String, String>>>().getClass().getGenericSuperclass()).getActualTypeArguments();
        // System.out.println(sss.length);
        
        try {
            // Type[] params = ((ParameterizedType)new TypeLoader<T>().getTypeLoader().getGenericType()).getActualTypeArguments();
            // //Type[] sss = ((ParameterizedType)new TypeLoader<T>(){ }.getClass().getGenericSuperclass()).getActualTypeArguments();
            //
            // for (Type type : params) {
            // if (type instanceof Class) {
            // System.out.println(((Class)type).getName());
            // }
            // }
            
            // Method tmpMethod=TypeToken.class.getMethod("tmpMethod");
            // Type[] types=tmpMethod.getGenericParameterTypes();
            // System.out.println(types[0].toString());
            
            Field fd = this.getClass().getDeclaredField("targetType");
            fd.setAccessible(true);
            System.out.println(fd.toGenericString());
            //fd.setAccessible(false);
            
            Field fd2 = this.getClass().getField("targetType");
            System.out.println(fd2.toGenericString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * 构造函数.
     */
    public TypeToken(T t) {
        this.targetType = t;
    }

    /**
     * 构造函数.
     */
    public TypeToken() {
    }

    /**
     * 设置targetType.
     * 
     * @return 返回targetType
     */
    public T getTargetType() {
        return targetType;
    }

    /**
     * 获取targetType.
     * 
     * @param targetType
     *            要设置的targetType
     */
    public void setTargetType(T targetType) {
        this.targetType = targetType;
    }
}
