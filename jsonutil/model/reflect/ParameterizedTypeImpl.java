/*
 * 文件名：ParameterizedTypeImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ParameterizedTypeImpl.java
 * 修改人：tianzhong
 * 修改时间：2017年3月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model.reflect;

import static com.google.gson.internal.$Gson$Preconditions.checkArgument;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import com.better517na.forStudy.advanced.reflect.jsonutil.exception.JsonUtilException;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.CommonUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.ReflectUtil;
import com.google.gson.internal.$Gson$Types;

/**
 * TODO 添加类的一句话简单描述.
 * 
 * @author     tianzhong
 */
public class ParameterizedTypeImpl implements ParameterizedType,Serializable {
    private static final long serialVersionUID = 1L;
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;
    
    public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) throws JsonUtilException {
        // require an owner type if the raw type needs it
        if (rawType instanceof Class<?>) {
            Class<?> rawTypeAsClass = (Class<?>) rawType;
            boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
            checkArgument(ownerType != null || isStaticOrTopLevelClass);
        }

        this.ownerType = ownerType == null ? null : TypeToken.canonicalize(ownerType);
        this.rawType = TypeToken.canonicalize(rawType);
        this.typeArguments = typeArguments.clone();
        for (int t = 0; t < this.typeArguments.length; t++) {
            if (CommonUtil.isNullOrEmpty(this.typeArguments[t])) {
                throw new JsonUtilException("ParameterizedTypeImpl Null");
            }
            // checkNotNull(this.typeArguments[t]);
            if (!ReflectUtil.isNotPrimitive(this.typeArguments[t])) {
                throw new JsonUtilException("ParameterizedTypeImpl throw a Exception");
            }
            // checkNotPrimitive(this.typeArguments[t]);
            this.typeArguments[t] = TypeToken.canonicalize(this.typeArguments[t]);
        }
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.typeArguments;
    } 
    
    @Override
    public Type getRawType() {
        return this.rawType;
    }

    @Override
    public Type getOwnerType() {
        return this.ownerType;
    }


    @Override public boolean equals(Object other) {
      return other instanceof ParameterizedType
          && $Gson$Types.equals(this, (ParameterizedType) other);
    }

    @Override public int hashCode() {
      return Arrays.hashCode(typeArguments)
          ^ rawType.hashCode()
          ^ TypeHelper.hashCodeOrZero(ownerType);
    }
}
