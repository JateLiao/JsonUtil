/*
 * 文件名：TypeToken.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeToken.java
 * 修改人：tianzhong
 * 修改时间：2017年2月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

import static com.google.gson.internal.$Gson$Preconditions.checkArgument;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import com.better517na.forStudy.advanced.reflect.jsonutil.exception.JsonUtilException;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.CommonUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.ReflectUtil;
import com.google.gson.internal.$Gson$Types;

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
    private static Type canonicalize(Type type) throws JsonUtilException {
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
    
    /**
     * @author tianzhong
     */
    private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
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

            this.ownerType = ownerType == null ? null : canonicalize(ownerType);
            this.rawType = canonicalize(rawType);
            this.typeArguments = typeArguments.clone();
            for (int t = 0; t < this.typeArguments.length; t++) {
                if (CommonUtil.isNullOrEmpty(this.typeArguments[t])) {
                    throw new JsonUtilException("ParameterizedTypeImpl Null");
                }
                // checkNotNull(this.typeArguments[t]);
                if (ReflectUtil.isNotPrimitive(this.typeArguments[t])) {
                    throw new JsonUtilException("ParameterizedTypeImpl throw a Exception");
                }
                // checkNotPrimitive(this.typeArguments[t]);
                this.typeArguments[t] = canonicalize(this.typeArguments[t]);
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
              ^ hashCodeOrZero(ownerType);
        }
    }

    static int hashCodeOrZero(Object o) {
      return o != null ? o.hashCode() : 0;
    }
    
    /**
     * @author tianzhong
     */
    @SuppressWarnings("serial")
    private static final class GenericArrayTypeImpl implements GenericArrayType,Serializable {
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
    
    /**
     * The WildcardType interface supports multiple upper bounds and multiple
     * lower bounds. We only support what the Java 6 language needs - at most one
     * bound. If a lower bound is set, the upper bound must be Object.class.
     */
    private static final class WildcardTypeImpl implements WildcardType, Serializable {
        private static final long serialVersionUID = 1L;
        private final Type upperBound;
        private final Type lowerBound;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) throws JsonUtilException {
            checkArgument(lowerBounds.length <= 1);
            checkArgument(upperBounds.length == 1);

            if (lowerBounds.length == 1) {
                checkNotNull(lowerBounds[0]);
                // checkNotPrimitive(lowerBounds[0]);
                if (ReflectUtil.isNotPrimitive(lowerBounds[0])) {
                    throw new JsonUtilException("WildcardTypeImpl throw a Exception");
                }
                checkArgument(upperBounds[0] == Object.class);
                this.lowerBound = canonicalize(lowerBounds[0]);
                this.upperBound = Object.class;

            } else {
                checkNotNull(upperBounds[0]);
                if (ReflectUtil.isNotPrimitive(upperBounds[0])) {
                    throw new JsonUtilException();
                }
                // checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = canonicalize(upperBounds[0]);
            }
        }

        public Type[] getUpperBounds() {
            return new Type[]{upperBound};
        }

        public Type[] getLowerBounds() {
            return lowerBound != null ? new Type[]{lowerBound} : new Type[]{};
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof WildcardType && $Gson$Types.equals(this, (WildcardType) other);
        }

        @Override
        public int hashCode() {
            // this equals Arrays.hashCode(getLowerBounds()) ^ Arrays.hashCode(getUpperBounds());
            return (lowerBound != null ? 31 + lowerBound.hashCode() : 1) ^ (31 + upperBound.hashCode());
        }
    }

    /**
     * return TypeContainers instance.
     */
    public Type[] getTypeContainers()  {
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
