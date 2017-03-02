/*
 * 文件名：JsonUtilsNew4.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonUtilsNew4.java
 * 修改人：tianzhong
 * 修改时间：2017年2月22日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeToken;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.BoostBo;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.ChengduBo;
import com.better517na.forStudy.util.JsonUtils;

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
public class JsonTest5 {
    
    @Test
    public void xxxx() throws Exception{
        // List<Map<String, String>> a = new ArrayList<>();
        new TypeToken<List<Map<String, String>>>().getTypeContainers();
        // new TypeToken<List<Map<String, String>>>().getClass().getTypeParameters();
    }

    @Test
    public void tttt() {
        ChengduBo chengduBo = new ChengduBo();
        chengduBo.setArea(78.56);
        BoostBo boost = new BoostBo();
        boost.setBoostId(55);
        boost.setBoostName("boost name");
        Map<String, String> mapObj = new HashMap<>();
        mapObj.put("kobe", "los angeles");
        mapObj.put("jordan", "chicago");
        boost.setMapObj(mapObj);
        chengduBo.setBoost(boost);
        chengduBo.setCityCode(456);
        chengduBo.setCityName("chengdu");
        chengduBo.setGovernmentAddress("一环路北一段环球广场");
        List<String> mainStreets = new ArrayList<>();
        mainStreets.add("春熙路");
        mainStreets.add("九眼桥");
        chengduBo.setMainStreets(mainStreets );
        
        String json = JsonUtilsNew3.toJson(chengduBo);
        System.out.println(json);
        json = JsonUtils.toJson(chengduBo);
        System.out.println(json);
        
        ChengduBo cd = JsonUtilsNew3.toObject(json, ChengduBo.class);
        
        System.out.println(cd.getMainStreets().get(0));
    }
}
