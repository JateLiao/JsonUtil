/*
 * 文件名：WildcardTypeImpl.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： WildcardTypeImpl.java
 * 修改人：tianzhong
 * 修改时间：2017年3月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model.reflect;

import static com.google.gson.internal.$Gson$Preconditions.checkArgument;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import com.better517na.forStudy.advanced.reflect.jsonutil.exception.JsonUtilException;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.ReflectUtil;
import com.google.gson.internal.$Gson$Types;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * <pre>
 * </pre>
 * 
 * @author     tianzhong
 */
public class WildcardTypeImpl implements WildcardType, Serializable {
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
            this.lowerBound = TypeToken.canonicalize(lowerBounds[0]);
            this.upperBound = Object.class;

        } else {
            checkNotNull(upperBounds[0]);
            if (ReflectUtil.isNotPrimitive(upperBounds[0])) {
                throw new JsonUtilException();
            }
            // checkNotPrimitive(upperBounds[0]);
            this.lowerBound = null;
            this.upperBound = TypeToken.canonicalize(upperBounds[0]);
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
