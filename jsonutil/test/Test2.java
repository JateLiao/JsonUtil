/*
 * 文件名：Test2.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： Test2.java
 * 修改人：tianzhong
 * 修改时间：2016年12月28日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.util.JsonUtils;

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
public class Test2 {

    @Test
    public void test1(){
        Map<Integer, String> map = new HashMap<>();
        map.put(24, "Kobe Brayant");
        map.put(15, "World Peace");
        map.put(16, "Paul Gasol");
        map.put(17, "Andre Bynum");
        map.put(7, "Lamar Odom");

        System.out.println(JsonUtils.toJson(map));
        System.out.println(JsonUtilsNew3.toJson(map));
    }

    @Test
    public void test2(){
        Map<Define, String> map = new HashMap<>();
        Define d = new Define();
        d.setDefineInt(123);
        d.setDefineName("123");
        System.out.println(d.hashCode());
        map.put(d, "123");
        System.out.println(JsonUtilsNew3.toJson(map));
        
        // System.out.println(JsonUtils.toJson(map));
    }
}
