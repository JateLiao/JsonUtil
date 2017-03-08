/*
 * 文件名：GenericArrayTypeImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GenericArrayTypeImpl.java
 * 修改人：tianzhong
 * 修改时间：2017年3月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model.reflect;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * TODO 添加类的一句话简单描述.
 * 
 * @author     tianzhong
 */
public class GenericArrayTypeImpl implements GenericArrayType,Serializable {
    /**
     * 添加字段注释.
     */
    private static final long serialVersionUID = 1L;
    private final Type componentType;
    
    public GenericArrayTypeImpl(Type ct) {
        this.componentType = ct;
    }

    @Override
    public Type getGenericComponentType() {
        return componentType;
    }

    @Override
    public int hashCode() {
        return componentType.hashCode();
    }
}
