/*
 * 文件名：GenA.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GenA.java
 * 修改人：tianzhong
 * 修改时间：2016年12月23日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.List;
import java.util.Map;

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
public class GenA<K, T, V> {
    public GenA() {
    }

    private K kk;

    private T tt;

    private V vv;

    private String name;

    private Define def;

    private Map<String, String> map;

    private Map<String, Define> defMap;

    private List<Define> defList;

    /**
     * 设置defList.
     * 
     * @return 返回defList
     */
    public List<Define> getDefList() {
        return defList;
    }

    /**
     * 获取defList.
     * 
     * @param defList
     *            要设置的defList
     */
    public void setDefList(List<Define> defList) {
        this.defList = defList;
    }

    public Map<String, Define> getDefMap() {
        return defMap;
    }

    public void setDefMap(Map<String, Define> defMap) {
        this.defMap = defMap;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public K getKk() {
        return kk;
    }

    public void setKk(K kk) {
        this.kk = kk;
    }

    public T getTt() {
        return tt;
    }

    public void setTt(T tt) {
        this.tt = tt;
    }

    public V getVv() {
        return vv;
    }

    public void setVv(V vv) {
        this.vv = vv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Define getDef() {
        return def;
    }

    public void setDef(Define def) {
        this.def = def;
    }

    // Object this$0;
    // final String this$0 = "ttt";
}
