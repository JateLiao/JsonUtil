/*
 * 文件名：TypeToken.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeToken.java
 * 修改人：tianzhong
 * 修改时间：2017年2月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import com.better517na.forStudy.advanced.reflect.jsonutil.exception.JsonUtilException;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeContainer;

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
    
    private Type type;
    
    /**
     * 添加字段注释.
     */
    public Class<? extends Object> clazz;
    
    /**
     * TODO 添加方法注释.
     * 
     * @param clazz .
     * @return .
     * @throws JsonUtilException .
     */
    public Type getType() {
        return this.type;
    }
    
    /**
     * TODO 添加方法注释.
     * 
     * @param parameterizedType
     * @return
     * @throws JsonUtilException 
     */
    public static Type canonicalize(Type type) throws JsonUtilException {
        if (type instanceof Class) {
            Class<?> c = (Class<?>) type;
            return c.isArray() ? new GenericArrayTypeImpl(canonicalize(c.getComponentType())).getGenericComponentType() : c;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new ParameterizedTypeImpl(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType) type;
            return new GenericArrayTypeImpl(gat.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            WildcardType w = (WildcardType) type;
            return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
        }
        
        return type;
    }
    
    public static int hashCodeOrZero(Object o) {
      return o != null ? o.hashCode() : 0;
    }

    /**
     * return TypeContainers instance.
     */
    public Type[] getTypeContainers()  {
        Type type = this.getType();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType pt = (ParameterizedType) type;
        Type[] ts = new Type[pt.getActualTypeArguments().length];
        // if (pt.getRawType() instanceof Class) {
        // ts[0] = (Class<?>)pt.getRawType();
        // }
        int index = 0;
        for (Type at : pt.getActualTypeArguments()) {
            if (at instanceof Class) {
                ts[index++] = (Class<?>)at;
            } else if (at instanceof ParameterizedTypeImpl) {
                ParameterizedType tpTmp = (ParameterizedType) at;
                TypeContainer tc = createRealTypeContainer(tpTmp);
                // 递归获取所有类型
                ts[index++] = tc;
            }
        }
        return ts;
    }

    /**
     * TODO 递归把ParameterType的参数类型转换成TypeContainer.
     */
    private TypeContainer createRealTypeContainer(ParameterizedType type) {
        Type[] tpArr = new Type[type.getActualTypeArguments().length + 1];
        tpArr[0] = type.getRawType();
        int index = 1;
        for (Type tmpType : type.getActualTypeArguments()) {
            if (tmpType instanceof Class) {
                tpArr[index++] = tmpType;
            } else if (tmpType instanceof ParameterizedType) {
                tpArr[index++] = createRealTypeContainer((ParameterizedType) tmpType);
            }
        }
        TypeContainer tc = new TypeContainer(tpArr[0], Arrays.copyOfRange(tpArr, 1, tpArr.length));
        return tc;
    }

    /**
     * 构造函数.
     */
    public TypeToken(T t) {
        this.targetType = t;
    } 

    /**
     * 构造函数.
     * @throws JsonUtilException 
     */
    public TypeToken() throws JsonUtilException {
        this.type = getSuperClassTypeParameter(this.getClass());
    }

    /**
     * TODO 添加方法注释.
     * 
     * @param class1
     * @return
     * @throws JsonUtilException
     */
    private Type getSuperClassTypeParameter(Class<?> class1) throws JsonUtilException {
        Type superClass = class1.getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new JsonUtilException("没有找到类型参数.");
        }

        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        return canonicalize(parameterizedType.getActualTypeArguments()[0]);
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
