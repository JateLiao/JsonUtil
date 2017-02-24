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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
    private T targetType;
    
    @SuppressWarnings("hiding")
    class TypeLoader<T> {
        public TypeToken<T> typeLoader; 

        /**
         * TODO 添加方法注释.
         * 
         * @return .
         * @throws Exception .
         */
        public Field getTypeLoader() throws Exception {
            return this.getClass().getDeclaredFields()[0];
        }
    }

    /**
     * 构造函数.
     */
    @SuppressWarnings("rawtypes")
    public TypeToken() {
        System.out.println();
        
        try {
            Type[] params = ((ParameterizedType)new TypeLoader<T>().getTypeLoader().getGenericType()).getActualTypeArguments();
            //Type[] sss = ((ParameterizedType)new TypeLoader<T>(){ }.getClass().getGenericSuperclass()).getActualTypeArguments();
            
            for (Type type : params) {
                if (type instanceof Class) {
                    System.out.println(((Class)type).getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数.
     */
    public TypeToken(T t) {
        this.targetType = t;
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

    public Type[] getType() {
        if (targetType == null) {
            return null;
        }
        Type[] res = new Type[10];
        
        return res;
    }
}
