/*
 * 文件名：Test2.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： Test2.java
 * 修改人：tianzhong
 * 修改时间：2016年12月


28日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.advanced.reflect.jsonutil.helper.CommonUtil;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.SingleJSon;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeToken;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.Define;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.model.GenA;
import com.better517na.forStudy.util.JsonUtils;

/**
 * TODO 用来测试泛型嵌套的主要测试类.
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
    
    @Test
    public void test3(){
        List<Define> list = new ArrayList<>();
        Define d = new Define();
        d.setDefineInt(111);
        d.setDefineName("name 111");
        

        Define d2 = new Define();
        d2.setDefineInt(222);
        d2.setDefineName("name 222");

        Define d3 = new Define();
        d3.setDefineInt(333);
        d3.setDefineName("name 333");
        
        list.add(d3);
        list.add(d2);
        list.add(d);
        
        String json = JsonUtils.toJson(list);
        List<String> sss = CommonUtil.getSingleJsonFromList(json);
        for (String string : sss) {
            System.out.println(string);
        }
    }
    

    
    @Test
    public void testsss(){
        new TypeToken<List<Map<String, String>>>().getType();
        GenA<String, Define, Integer> gen = new GenA<>();
        gen.setKk("");
        gen.setVv(120);
        gen.setName("GenA Name");
        
        Define def = new Define();
        def.setDefineInt(100);
        def.setDefineName("define \"name\":\"xxx\"");
        gen.setTt(def);
        gen.setDef(def);
        
        Map<String, String> map = new HashMap<>();
        map.put("Kobe", "Los Angeles Lakers");
        map.put("Jordan", "Chicago Bulls");
        map.put("Yao", "Houston Rockets");
        gen.setMap(map);
        
        Map<String, Define> defMap = new HashMap<>();
        defMap.put("123", def);
        gen.setDefMap(defMap);
        
        List<Define> defs = new ArrayList<>();
        Define d2 = new Define();
        d2.setDefineInt(555);
        d2.setDefineName("Special.Def2 Name\",\"{}");
        defs.add(d2);
        defs.add(def);
        gen.setDefList(defs);
        
        String json = JsonUtilsNew3.toJson(gen);
        System.out.println(json);
        json = JsonUtils.toJson(gen);
        System.out.println(json);
        
        // json = "{\"HotelCode\":\"H638975\",\"HotelName\":\"格林豪泰(成都火车北站北北城天街商务酒店)\",\"HotelAddress\":\"金牛区五福桥东路6号\",\"MinPrice\":158.0,\"PictureUrl\":\"http://pavo.elongstatic.com/i/Hotel120_120/0000CbCX.jpg\",\"HotelLevel\":\"1\",\"HotelService\":\"\",\"Latitude\":\"30.718843000\",\"Longitude\":\"104.071599000\",\"ResultSupp\":{\"Rooms\":{\"GreenTree\":{\"2_false\":{\"RoomCode\":\"2_false\",\"RoomName\":\"1.5米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"4_false\":{\"RoomCode\":\"4_false\",\"RoomName\":\"标准房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.2米/2张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"1_false\":{\"RoomCode\":\"1_false\",\"RoomName\":\"1.5米特价大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5米/张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"3_false\":{\"RoomCode\":\"3_false\",\"RoomName\":\"1.8米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.8/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"5_false\":{\"RoomCode\":\"5_false\",\"RoomName\":\"商务套房\",\"Floor\":\"3-4\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"6_false\":{\"RoomCode\":\"6_false\",\"RoomName\":\"1.8米商务大床房\",\"Floor\":\"3\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"7_false\":{\"RoomCode\":\"7_false\",\"RoomName\":\"豪华家庭房\",\"Floor\":\"5-7\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1..8米/1张+1.1米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null}}},\"Plans\":{\"GreenTree\":{\"PP_100601726BC4557EEB3C275DDE40FBAC\":{\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_75ADF30B6441840C74E80AD83862D8A3\":{\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_4808B27A2E865D5EDB0906D9FF89EB29\":{\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_EFFE57A79D30D5DA04555585632718B0\":{\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_6C2CBC40AD29851BEE38034025D11EFF\":{\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\":{\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_B894010939C45D0FBC52B1B2649A98F9\":{\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0}}},\"Prices\":{\"GreenTree\":[{\"RoomCode\":\"2_false\",\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"Prices\":{\"2016-12-15\":{\"RackRate\":209.0,\"Price\":184.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"4_false\",\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"1_false\",\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"Prices\":{\"2016-12-15\":{\"RackRate\":179.0,\"Price\":158.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"3_false\",\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"5_false\",\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"Prices\":{\"2016-12-15\":{\"RackRate\":299.0,\"Price\":263.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"6_false\",\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"7_false\",\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null}],\"RedTree\":[{\"RoomCode\":\"2_false\",\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"Prices\":{\"2016-12-15\":{\"RackRate\":209.0,\"Price\":184.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"4_false\",\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"1_false\",\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"Prices\":{\"2016-12-15\":{\"RackRate\":179.0,\"Price\":158.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"3_false\",\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"5_false\",\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"Prices\":{\"2016-12-15\":{\"RackRate\":299.0,\"Price\":263.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"6_false\",\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"7_false\",\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null}]}},\"PhoneNum\":\"028-65195998\",\"ShortDescription\":\"酒店开业时间:2014-04-23\\n格林豪泰成都五块石商务酒店是格林豪泰酒店管理集团在成都成立的第二家高品位高性价比的商务连锁酒店。酒店硬件按四星级标准设计装修，豪华大方，精致考究，风格独特，是别具一格的高端商务酒店。酒店位于成都市金牛区五块石五福桥东路6号，地理位置优越，交通便捷，距离火车北站3公里，距五块石汽车客运中心站2公里、成都东站21公里，距离华侨城欢乐谷4公里，距成都双流机场50分钟车程、天府广场市中心14分钟车程。酒店周边美食商铺围绕，更有龙湖北城天街入驻，大型远东百货公司，各大西南批发市场集聚之地，大西南茶城及百货商品批发。酒店拥有大床房、标准房、商务大床房、商务套房、商务大床房以及家庭房，配备自助餐厅、会议厅和地下停车场。格林豪泰成都五块石商务酒店是您商旅的首选之地。酒店本着全方位的服务，主动式的服务，让每个客人感受到关心、关爱的服务理念，为商务活动、旅游休闲的客人提供 “ 超健康，超舒适，超价值，超期望” 的四超服务。格林豪泰，只需感受！\\n\",\"FreeWIFI\":null,\"BoardRoom\":null,\"DiningRoom\":null,\"Parking\":null,\"SwimmingPool\":null,\"Gymnasium\":null}";
        // json = "{\"HotelCode\":\"H638975\",\"MinPrice\":158.0,\"HotelLevel\":\"1\",\"HotelService\":\"\",\"Latitude\":\"30.718843000\",\"Longitude\":\"104.071599000\",\"ResultSupp\":{\"Rooms\":{\"GreenTree\":{\"2_false\":{\"RoomCode\":\"2_false\",\"RoomName\":\"1.5米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"4_false\":{\"RoomCode\":\"4_false\",\"RoomName\":\"标准房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.2米/2张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"1_false\":{\"RoomCode\":\"1_false\",\"RoomName\":\"1.5米特价大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5米/张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"3_false\":{\"RoomCode\":\"3_false\",\"RoomName\":\"1.8米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.8/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"5_false\":{\"RoomCode\":\"5_false\",\"RoomName\":\"商务套房\",\"Floor\":\"3-4\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"6_false\":{\"RoomCode\":\"6_false\",\"RoomName\":\"1.8米商务大床房\",\"Floor\":\"3\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"7_false\":{\"RoomCode\":\"7_false\",\"RoomName\":\"豪华家庭房\",\"Floor\":\"5-7\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1..8米/1张+1.1米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null}}},\"PhoneNum\":\"028-65195998\",\"ShortDescription\":\"酒店开业时间:2014-04-23\\n格林豪泰成都五块石商务酒店是格林豪泰酒店管理集团在成都成立的第二家高品位高性价比的商务连锁酒店。酒店硬件按四星级标准设计装修，豪华大方，精致考究，风格独特，是别具一格的高端商务酒店。酒店位于成都市金牛区五块石五福桥东路6号，地理位置优越，交通便捷，距离火车北站3公里，距五块石汽车客运中心站2公里、成都东站21公里，距离华侨城欢乐谷4公里，距成都双流机场50分钟车程、天府广场市中心14分钟车程。酒店周边美食商铺围绕，更有龙湖北城天街入驻，大型远东百货公司，各大西南批发市场集聚之地，大西南茶城及百货商品批发。酒店拥有大床房、标准房、商务大床房、商务套房、商务大床房以及家庭房，配备自助餐厅、会议厅和地下停车场。格林豪泰成都五块石商务酒店是您商旅的首选之地。酒店本着全方位的服务，主动式的服务，让每个客人感受到关心、关爱的服务理念，为商务活动、旅游休闲的客人提供 “ 超健康，超舒适，超价值，超期望” 的四超服务。格林豪泰，只需感受！\\n\",\"FreeWIFI\":null,\"BoardRoom\":null,\"DiningRoom\":null,\"Parking\":null,\"SwimmingPool\":null,\"Gymnasium\":null}";
        
        List<SingleJSon> list = CommonUtil.getSingleJsonValue(json);
        
        for (SingleJSon v : list) {
            System.out.println("json串：" + v.getValue());
            System.err.println(v.getValue().substring(0, v.getColonIndex()));
            System.err.println(v.getValue().substring(v.getColonIndex() + 1, v.getValue().length()));
        }
    }
    
    @Test
    public void tess1(){
        String s = "{\"HotelCode\":\"H638975\",\"HotelName\":\"格林豪泰(成都火车北站北北城天街商务酒店)\",\"HotelAddress\":\"金牛区五福桥东路6号\",\"MinPrice\":158.0,\"PictureUrl\":\"http://pavo.elongstatic.com/i/Hotel120_120/0000CbCX.jpg\",\"HotelLevel\":\"1\",\"HotelService\":\"\",\"Latitude\":\"30.718843000\",\"Longitude\":\"104.071599000\",\"ResultSupp\":{\"Rooms\":{\"GreenTree\":{\"2_false\":{\"RoomCode\":\"2_false\",\"RoomName\":\"1.5米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"4_false\":{\"RoomCode\":\"4_false\",\"RoomName\":\"标准房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.2米/2张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"1_false\":{\"RoomCode\":\"1_false\",\"RoomName\":\"1.5米特价大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.5米/张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"3_false\":{\"RoomCode\":\"3_false\",\"RoomName\":\"1.8米大床房\",\"Floor\":\"3-7\",\"Intent\":\"免费\",\"Area\":\"35㎡；\",\"BedType\":\"1.8/米\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"5_false\":{\"RoomCode\":\"5_false\",\"RoomName\":\"商务套房\",\"Floor\":\"3-4\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"6_false\":{\"RoomCode\":\"6_false\",\"RoomName\":\"1.8米商务大床房\",\"Floor\":\"3\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1.8米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null},\"7_false\":{\"RoomCode\":\"7_false\",\"RoomName\":\"豪华家庭房\",\"Floor\":\"5-7\",\"Intent\":\"免费\",\"Area\":\"45㎡；\",\"BedType\":\"1..8米/1张+1.1米/1张\",\"Description\":null,\"Remark\":\"全部 明窗\",\"MaxCount\":-1,\"RoomRemark\":null}}},\"Plans\":{\"GreenTree\":{\"PP_100601726BC4557EEB3C275DDE40FBAC\":{\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_75ADF30B6441840C74E80AD83862D8A3\":{\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_4808B27A2E865D5EDB0906D9FF89EB29\":{\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_EFFE57A79D30D5DA04555585632718B0\":{\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_6C2CBC40AD29851BEE38034025D11EFF\":{\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\":{\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0},\"PP_B894010939C45D0FBC52B1B2649A98F9\":{\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"PlanName\":\"有窗\",\"Breakfast\":null,\"Limit\":\"; \",\"Remark\":\"无早\",\"PayNow\":\"0\",\"IsSpeical\":\"0\",\"PayType\":0}}},\"Prices\":{\"GreenTree\":[{\"RoomCode\":\"2_false\",\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"Prices\":{\"2016-12-15\":{\"RackRate\":209.0,\"Price\":184.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"4_false\",\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"1_false\",\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"Prices\":{\"2016-12-15\":{\"RackRate\":179.0,\"Price\":158.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"3_false\",\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"5_false\",\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"Prices\":{\"2016-12-15\":{\"RackRate\":299.0,\"Price\":263.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"6_false\",\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"7_false\",\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null}],\"RedTree\":[{\"RoomCode\":\"2_false\",\"PlanCode\":\"PP_100601726BC4557EEB3C275DDE40FBAC\",\"Prices\":{\"2016-12-15\":{\"RackRate\":209.0,\"Price\":184.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"4_false\",\"PlanCode\":\"PP_75ADF30B6441840C74E80AD83862D8A3\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"1_false\",\"PlanCode\":\"PP_4808B27A2E865D5EDB0906D9FF89EB29\",\"Prices\":{\"2016-12-15\":{\"RackRate\":179.0,\"Price\":158.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"3_false\",\"PlanCode\":\"PP_EFFE57A79D30D5DA04555585632718B0\",\"Prices\":{\"2016-12-15\":{\"RackRate\":229.0,\"Price\":202.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"5_false\",\"PlanCode\":\"PP_6C2CBC40AD29851BEE38034025D11EFF\",\"Prices\":{\"2016-12-15\":{\"RackRate\":299.0,\"Price\":263.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"6_false\",\"PlanCode\":\"PP_4D4C0A1EC6687AD1A58474DCCD0F01C1\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null},{\"RoomCode\":\"7_false\",\"PlanCode\":\"PP_B894010939C45D0FBC52B1B2649A98F9\",\"Prices\":{\"2016-12-15\":{\"RackRate\":308.0,\"Price\":271.0,\"Refund\":0.0,\"Reduce\":0.0}},\"Rule\":\"入住前1天18点前免费退订\",\"RoomCount\":1,\"Source\":\"0\",\"ServicePrice\":0.0,\"PayType\":0,\"Policys\":null}]}},\"PhoneNum\":\"028-65195998\",\"ShortDescription\":\"酒店开业时间:2014-04-23\\n格林豪泰成都五块石商务酒店是格林豪泰酒店管理集团在成都成立的第二家高品位高性价比的商务连锁酒店。酒店硬件按四星级标准设计装修，豪华大方，精致考究，风格独特，是别具一格的高端商务酒店。酒店位于成都市金牛区五块石五福桥东路6号，地理位置优越，交通便捷，距离火车北站3公里，距五块石汽车客运中心站2公里、成都东站21公里，距离华侨城欢乐谷4公里，距成都双流机场50分钟车程、天府广场市中心14分钟车程。酒店周边美食商铺围绕，更有龙湖北城天街入驻，大型远东百货公司，各大西南批发市场集聚之地，大西南茶城及百货商品批发。酒店拥有大床房、标准房、商务大床房、商务套房、商务大床房以及家庭房，配备自助餐厅、会议厅和地下停车场。格林豪泰成都五块石商务酒店是您商旅的首选之地。酒店本着全方位的服务，主动式的服务，让每个客人感受到关心、关爱的服务理念，为商务活动、旅游休闲的客人提供 “ 超健康，超舒适，超价值，超期望” 的四超服务。格林豪泰，只需感受！\\n\",\"FreeWIFI\":null,\"BoardRoom\":null,\"DiningRoom\":null,\"Parking\":null,\"SwimmingPool\":null,\"Gymnasium\":null}";
        // String ss = getFieldStr4Others(s, "Prices", "{");
        // System.out.println("ss: " + ss);
        String sss = CommonUtil.getFieldStr4Basic(s, "MinPrice", false);
        System.out.println("sss: " + sss);
    }
}
