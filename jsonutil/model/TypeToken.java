/*
 * 文件名：TypeToken.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeToken.java
 * 修改人：tianzhong
 * 修改时间：2017年2月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

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
    public T targetType;
    
    /**
     * 添加字段注释.
     */
    public Class<? extends Object> clazz;
    
    /**
     * TODO 添加方法注释.
     * 
     * @param clazz
     * @return
     */
    public Type getType(){
        Type superClass = this.getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("没有找到类型参数.");
        }
        
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        
        return null;
    }
    
    /**
     * 构造函数.
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    public Type[] getTmpType()  {
        System.out.println();
        
        try {
            // ((ParameterizedType)this.clazz.getGenericSuperclass()).getActualTypeArguments()
            System.out.println(this.clazz.getName());
            this.targetType.getClass().getName();
            // Type[] sss = ((ParameterizedType)new TypeToken<List<Map<String, String>>>().getClass().getGenericSuperclass()).getActualTypeArguments();
            // System.out.println(sss.length);
            ////// 3333333333333
            
            
            ////// 1111111111111
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
            
            //////// 2222222222222222
            // Field fd = this.getClass().getDeclaredField("targetType");
            // fd.setAccessible(true);
            // System.out.println(fd.toGenericString());
            // //fd.setAccessible(false);
            //
            // Field fd2 = this.getClass().getField("targetType");
            // System.out.println(fd2.toGenericString());
            //
            // Type[] ts = ((ParameterizedType)fd2.getGenericType()).getActualTypeArguments();
            // System.out.println(ts.length);
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
        // this.targetType = Class<T>.newInstance();
    }

    /**
     * 构造函数.
     * 
     * @param class1
     */
    public TypeToken(Class<? extends Object> cls) {
        this.clazz = cls;
    }
}
