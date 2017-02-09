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
 * @author tianzhong
 */
public class SingleJSon {
    /**
     * json.
     */
    private String value;

    /**
     * 冒号的位置（从0开始计算）.
     */
    private Integer colonIndex;

    /**
     * 字段名.
     */
    private String fieldName;

    /**
     * 设置fieldName.
     * 
     * @return 返回fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 获取fieldName.
     * 
     * @param fieldName
     *            要设置的fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 字段值.
     */
    private String fieldValue;

    /**
     * 设置fieldValue.
     * 
     * @return 返回fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * 获取fieldValue.
     * 
     * @param fieldValue
     *            要设置的fieldValue
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

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
