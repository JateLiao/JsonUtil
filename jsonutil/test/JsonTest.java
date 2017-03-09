/*
 * 文件名：JsonTest.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonTest.java
 * 修改人：tianzhong
 * 修改时间：2016年7月14日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew2;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.ReflectUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.AddressCodeBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.BoostBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.CityBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.Define;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.GenA;
import com.better517na.forStudy.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
@SuppressWarnings({"unchecked", "unused" })
public class JsonTest {
    public static void main(String[] args) {
        AddressCodeBo bo = new AddressCodeBo();
        bo.setKeyID("thrift_test_keyid");
        bo.setCode(34028);
        bo.setProvinceName("四川");
        // bo.setState(1);
        bo.setStateName("state");
        bo.setAddTime(new Date());

        List<String> list = new ArrayList<>();
        list.add("k\",\"");
        list.add("o");
        list.add("b");
        list.add("e");
        bo.setList(list);

        CityBo city = new CityBo();
        city.setCityCode(10816);
        // city.setCityName("绵阳");
        city.setBoost(new BoostBo(250, "Deric Rose 7"));
        bo.setCity(city);
        
        BoostBo b1 = new BoostBo(50, "Ultra Boost");
        BoostBo b2 = new BoostBo(100, "Crazy Light");

        List<BoostBo> bl = new ArrayList<>();
        bl.add(b1);
        bl.add(b2);
        bo.setBoosts(bl);
        
        Map<String, String> mapObj = new HashMap<>();
        mapObj.put("onepiece", "luffy");
        mapObj.put("naruto", "hatchi");
        mapObj.put("bleach", "stone");
        b1.setMapObj(mapObj);
        b2.setMapObj(mapObj);
        
        List<List<BoostBo>> objList = new ArrayList<List<BoostBo>>();
        objList.add(bl);
        objList.add(bl);
        bo.setObjList(objList);

        List<List<String>> strList = new ArrayList<List<String>>();
        strList.add(list);
        strList.add(list);
        bo.setStrList(strList);

        Map<String, Map<String, List<List<BoostBo>>>> maps = new HashMap<>();
        // maps.put(3, "wade");
        // maps.put(23, "jordan");
        // maps.put(24, "kobe");
        
        Map<String, List<List<BoostBo>>> map1 = new HashMap<>();
        map1.put("objList", objList);
        maps.put("mapInMap", map1);

        String json = JsonUtilsNew2.toJson(list);
        System.out.println("本地：" + json);
        json = JsonUtils.toJson(list);
        System.out.println("Gson：" + json);
        
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        
        List<String> sssss = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
        List<String> sssss2 = JsonUtilsNew2.toObject(json, List.class, String.class);
        
        System.out.println(sssss.size() + sssss2.size());
        
//        String jssson = "{\"boosts\":[{\"boostId\":50,\"boostName\":\"Ultra Boost\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}},{\"boostId\":100,\"boostName\":\"Crazy Light\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}}]}";
//        AddressCodeBo cityBo = JsonUtilsNew2.toObject(jssson, AddressCodeBo.class);
//        
//        System.out.println(cityBo.getBoosts().get(0).getBoostName());
    }
    
    @Test
    public void tttttt(){
        GenA<String, Define, GenA<String, Integer, Define>> gen = new GenA<>();
        String k = "";
        Define t = new Define();
        GenA<String, Integer, Define> gg = new GenA<>();
        gen.setKk(k);
        gen.setTt(t);
        gen.setVv(gg);
        Field[] fds = gen.getClass().getDeclaredFields();
        for (Field fd : fds) {
            if (!ReflectUtil.isGenericTypeField(fd)) {
                continue;
            }
            ParameterizedType pt = ((ParameterizedType)fd.getGenericType());
        }
    }
}
