/*
 * 文件名：ChengduBO.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： ChengduBO.java
 * 修改人：tianzhong
 * 修改时间：2017年2月22日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test.model;

import java.util.List;

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
public class ChengduBo extends CityBo {

    /**
     * 添加字段注释.
     */
    private Double area;
    
    /**
     * 添加字段注释.
     */
    private List<String> mainStreets;
    
    /**
     * 添加字段注释.
     */
    private String governmentAddress;

    /**
     * 设置area.
     * 
     * @return 返回area
     */
    public Double getArea() {
        return area;
    }

    /**
     * 获取area.
     * 
     * @param area
     *            要设置的area
     */
    public void setArea(Double area) {
        this.area = area;
    }

    /**
     * 设置mainStreets.
     * 
     * @return 返回mainStreets
     */
    public List<String> getMainStreets() {
        return mainStreets;
    }

    /**
     * 获取mainStreets.
     * 
     * @param mainStreets
     *            要设置的mainStreets
     */
    public void setMainStreets(List<String> mainStreets) {
        this.mainStreets = mainStreets;
    }

    /**
     * 设置governmentAddress.
     * 
     * @return 返回governmentAddress
     */
    public String getGovernmentAddress() {
        return governmentAddress;
    }

    /**
     * 获取governmentAddress.
     * 
     * @param governmentAddress
     *            要设置的governmentAddress
     */
    public void setGovernmentAddress(String governmentAddress) {
        this.governmentAddress = governmentAddress;
    } 
}
