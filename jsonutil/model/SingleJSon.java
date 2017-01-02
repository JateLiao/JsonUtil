/*
 * 文件名：SingleJSon.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SingleJSon.java
 * 修改人：tianzhong
 * 修改时间：2016年12月28日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model;

/**
 * TODO 单个json对，以及一些信息
 * 
 * @author     tianzhong
 */
public class SingleJSon {
    /**
     * json.
     */
    private String value;
    /**
     * 冒号的位置.
     */
    private Integer colonIndex;

    /**
     * 设置value.
     * 
     * @return 返回value
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取value.
     * 
     * @param value
     *            要设置的value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 设置colonIndex.
     * 
     * @return 返回colonIndex
     */
    public Integer getColonIndex() {
        return colonIndex;
    }

    /**
     * 获取colonIndex.
     * 
     * @param colonIndex
     *            要设置的colonIndex
     */
    public void setColonIndex(Integer colonIndex) {
        this.colonIndex = colonIndex;
    }
}
