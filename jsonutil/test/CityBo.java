/*
 * 文件名：CityBo.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CityBo.java
 * 修改人：tianzhong
 * 修改时间：2016年7月15日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

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
public class CityBo {
    private String cityName;

    private Integer cityCode;
    
    private BoostBo boost;
    
    /**
     * 设置boost.
     * 
     * @return 返回boost
     */
    public BoostBo getBoost() {
        return boost;
    }

    /**
     * 获取boost.
     * 
     * @param boost 要设置的boost
     */
    public void setBoost(BoostBo boost) {
        this.boost = boost;
    }

    /**
     * 设置cityName.
     * 
     * @return 返回cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 获取cityName.
     * 
     * @param cityName
     *            要设置的cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 设置cityCode.
     * 
     * @return 返回cityCode
     */
    public Integer getCityCode() {
        return cityCode;
    }

    /**
     * 获取cityCode.
     * 
     * @param cityCode
     *            要设置的cityCode
     */
    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

}
