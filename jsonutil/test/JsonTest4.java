/*
 * 文件名：JsonTest4.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonTest4.java
 * 修改人：tianzhong
 * 修改时间：2017年2月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeContainer;
import com.better517na.forStudy.util.JsonUtils;

/**
 * TODO 添加类的一句话简单描述.
 * @author     tianzhong
 */
@SuppressWarnings("unchecked")
public class JsonTest4 {
    
    
    @Test
    public void toObjTypeContainnerTest1(){
        // K, T, V
        GenA<String, Define, GenA<List<Define>, Map<String, Define>, Define>> gen = new GenA<>();
        Define d1 = new Define();
        d1.setDefineInt(500);
        d1.setDefineName("Define-d1-name");
        Define d2 = new Define();
        d2.setDefineInt(555);
        d2.setDefineName("Define-d2-\"name\"");
        List<Define> defList = new ArrayList<>();
        defList.add(d1);
        defList.add(d2);
        gen.setDef(d1);
        gen.setDefList(defList);
        Map<String, Define> defMap = new HashMap<>();
        defMap.put("d1", d1);
        defMap.put("d2", d2);
        gen.setDefMap(defMap);
        gen.setKk("kk-value:kk");
        Map<String, String> map = new HashMap<>();
        map.put("Kobe", "Los Angeles Lakers");
        map.put("Jordan", "Chicago Bulls");
        map.put("Yao", "Houston Rockets");
        gen.setMap(map );
        gen.setName("GenA-Name:Super All Star");
        gen.setTt(d1);
        GenA<List<Define>, Map<String, Define>, Define> genG = new GenA<>();
        genG.setDef(d2);
        genG.setDefList(defList);
        genG.setDefMap(defMap);
        genG.setKk(defList);
        genG.setMap(map);
        genG.setName("genG-Name:KAKAROTE");
        genG.setTt(defMap);
        genG.setVv(d1);
        gen.setVv(genG);
        
        String json = JsonUtilsNew3.toJson(gen);
        System.out.println(json);
        json = JsonUtils.toJson(gen);
        System.out.println(json);
        
        // String, Define, GenA<String, Integer, Define>
        TypeContainer tc = new TypeContainer(GenA.class, new TypeContainer(List.class, Define.class), new TypeContainer(Map.class, String.class, Define.class), Define.class);
        GenA<String, Define, GenA<String, Integer, Define>> genNew = JsonUtilsNew3.toObject(json, GenA.class, String.class, Define.class, tc);
        
        System.out.println(JsonUtilsNew3.toJson(genNew));
        System.out.println(genNew.getName());
    }
}
