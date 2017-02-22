/*
 * 文件名：JsoTest2.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsoTest2.java
 * 修改人：tianzhong
 * 修改时间：2016年10月29日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew2;
import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.AddressCodeBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.BoostBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.CityBo;

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
public class JsonTest2 {
    public static void main(String[] args) throws Exception {
        AddressCodeBo bo = new AddressCodeBo();
        bo.setKeyID("thrift_test_keyi\"d");
        bo.setCode(34028);
        bo.setProvinceName("四川");
        // bo.setState(1);
        bo.setStateName("state");
        bo.setAddTime(new Date());

        CityBo city = new CityBo();
        city.setCityCode(10816);
        // city.setCityName("绵阳");
        city.setBoost(new BoostBo(250, "Deric Rose 7"));
        bo.setCity(city);
        
        BoostBo b1 = new BoostBo(50, "Ultra\" },{Boost");
        BoostBo b2 = new BoostBo(100, "Crazy Light");

        List<BoostBo> bl = new ArrayList<>();
        bl.add(b1);
        bl.add(b2);
        bo.setBoosts(bl);
        
        String json = JsonUtilsNew3.toJson(bo);
        System.out.println("本地：" + json);
//        json = JsonUtils.toJson(bo);
//        System.out.println("Gson：" + json);
        
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        
//        List<String> sssss = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
//        List<String> sssss2 = JsonUtilsNew2.toObject(json, List.class, String.class);
        
        AddressCodeBo cityBo = JsonUtilsNew2.toObject(json, AddressCodeBo.class);
        
        System.out.println(cityBo.getBoosts().get(0).getBoostName());
    }

//    @Test
//    public void genericTest(){
//        Response<String> res = new Response<>();
//        Field[] fds = res.getClass().getDeclaredFields();
//        for (Field fd : fds) {
//            System.out.println(fd.getName());
//            System.out.println(fd.getType());
//        }
//    }
    
    class Response<T> {
        T obj;
        Integer i;
    }
}
