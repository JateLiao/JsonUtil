/*
 * 文件名：ClassContainer.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ClassContainer.java
 * 修改人：tianzhong
 * 修改时间：2016年12月1日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * TODO 类容器，专门为了泛型类型.
 * 
 * @author     tianzhong
 */
public class TypeContainer implements ParameterizedType {
    
    /**
     * 弱类型.
     */
    private Type rawClazz;
    
    /**
     * 泛型参数数组.
     */
    private Type[] genericTypes;
    
    /**
     * 构造函数.
     * 
     */
    public TypeContainer(Type raw, Type... genC) {
        this.rawClazz = raw;
        this.genericTypes = genC;
    }

    /**
     * 构造函数.
     * 
     */
    public TypeContainer() {
        
    }

    /**
     * 设置rawClazz.
     * 
     * @return 返回rawClazz
     */
    public Type getRawClazz() {
        return rawClazz;
    }

    /**
     * 获取rawClazz.
     * 
     * @param rawClazz 要设置的rawClazz
     */
    public void setRawClazz(Type rawClazz) {
        this.rawClazz = rawClazz;
    }

    /**
     * 设置genericTypes.
     * 
     * @return 返回genericTypes
     */
    public Type[] getGenericTypes() {
        return genericTypes;
    }

    /**
     * 获取genericTypes.
     * 
     * @param genericTypes 要设置的genericTypes
     */
    public void setGenericTypes(Type[] genericTypes) {
        this.genericTypes = genericTypes;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public Type[] getActualTypeArguments() {
        return genericTypes;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public Type getOwnerType() {
        return null;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public Type getRawType() {
        return rawClazz;
    }
}
