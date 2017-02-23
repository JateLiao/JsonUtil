/*
 * 文件名：TypeToken.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeToken.java
 * 修改人：tianzhong
 * 修改时间：2017年2月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

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

    /**
     * 构造函数.
     */
    public TypeToken() {

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
        Type[] res = new Type[10];
        
        return res;
    }
}
