/*
 * 文件名：BoostBo.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BoostBo.java
 * 修改人：tianzhong
 * 修改时间：2016年7月15日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test.model;

import java.util.Map;

/**
 * TODO 添加类的一句话简单描述.
 *
 * @author tianzhong
 */
public class BoostBo {
    private Integer boostId;

    private String boostName;

    private Map<String, String> mapObj;

    /**
     * 设置mapObj.
     * 
     * @return 返回mapObj
     */
    public Map<String, String> getMapObj() {
        return mapObj;
    }

    /**
     * 获取mapObj.
     * 
     * @param mapObj
     *            要设置的mapObj
     */
    public void setMapObj(Map<String, String> mapObj) {
        this.mapObj = mapObj;
    }

    /**
     * 构造函数.
     * 
     */
    public BoostBo(Integer id, String name) {
        this.boostId = id;
        this.boostName = name;
    }

    /**
     * 构造函数.
     * 
     */
    public BoostBo() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 设置boostId.
     * 
     * @return 返回boostId
     */
    public Integer getBoostId() {
        return boostId;
    }

    /**
     * 获取boostId.
     * 
     * @param boostId
     *            要设置的boostId
     */
    public void setBoostId(Integer boostId) {
        this.boostId = boostId;
    }

    /**
     * 设置boostName.
     * 
     * @return 返回boostName
     */
    public String getBoostName() {
        return boostName;
    }

    /**
     * 获取boostName.
     * 
     * @param boostName
     *            要设置的boostName
     */
    public void setBoostName(String boostName) {
        this.boostName = boostName;
    }
}
