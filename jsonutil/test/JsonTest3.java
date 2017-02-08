/*
 * 文件名：JsoTest2.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsoTest2.java
 * 修改人：tianzhong
 * 修改时间：2016年10月29日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.better517na.forStudy.advanced.reflect.jsonutil.JsonUtilsNew3;
import com.better517na.forStudy.advanced.reflect.jsonutil.model.TypeContainer;
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
public class JsonTest3 {
    class Js{
        String s1;
        String s2;
        String s3;
    }
    @Test
    public void mains() {
//        System.out.println(Integer.MAX_VALUE);
        Js js = new Js();
        js.s1 = "";
        js.s3 = "null";
        
        System.out.println(JsonUtils.toJson(js));
    }

    // @Test
    // public void genericTest(){
    // Response<String> res = new Response<>();
    // Field[] fds = res.getClass().getDeclaredFields();
    // for (Field fd : fds) {
    // System.out.println(fd.getName());
    // System.out.println(fd.getType());
    // }
    // }

    class Response<T> {
        T obj;
        Integer i;
    }
    
    @Test
    public void tessst() throws Exception{
        TypeContainer cn = new TypeContainer(Map.class, String.class, Integer.class);
        if (cn instanceof ParameterizedType) {
            if (cn instanceof Type) {
                System.err.println("777777777777777");
                new TypeToken<List<Integer>>(){ }.getType();
                new Gson().fromJson("", null);
            }
            ((ParameterizedType)cn).getActualTypeArguments();
            
            new Gson().fromJson("", null);
            JsonUtilsNew3.toObject("", String.class, null);
            new TypeContainer(null, null);
        }
    }

    @Test
    public void mapTest() throws Exception {
        Map<List<String>, List<Integer>> map = new HashMap<List<String>, List<Integer>>();
        List<String> l1 = new ArrayList<>();
        l1.add("test1");
        l1.add("test2");
        List<Integer> l11 = new ArrayList<>();
        l11.add(1);
        l11.add(2);

        List<String> l2 = new ArrayList<>();
        l1.add("test11");
        l1.add("test22");
        List<Integer> l22 = new ArrayList<>();
        l11.add(11);
        l11.add(22);

        map.put(l1, l11);
        map.put(l2, l22);

        System.out.println(new Gson().toJson(map));
        System.out.println(JsonUtilsNew3.toJson(map));
    }
    
    @Test
    public void testToObjGenric11(){
        Field[] fds = GenA.class.getDeclaredFields();
        for (Field fd : fds) {
            System.out.println(fd.getName() + "--" + "getType(): " + fd.getType());
            System.out.println(fd.getName() + "--" + "getGenericType(): " + fd.getGenericType());
            System.out.println(fd.getName() + "--" + "getDeclaringClass(): " + fd.getDeclaringClass());
            System.out.println();
        }
    }
     
    @Test
    public void testToObjGenric1() throws Exception{
        GenALocal<String, DefineLocal, Integer> gen = new GenALocal<>();
        gen.kk = "";
        gen.vv = 150;
        gen.name = "GenA Name";
        DefineLocal def = new DefineLocal();
        def.defineInt = 100;
        def.defineName = "define name";
        gen.tt = def;
        gen.def = def;
        
        String json = JsonUtilsNew3.toJson(gen);
        System.out.println(json);
        json = JsonUtils.toJson(gen);
        System.out.println(json);
        
//        gen = JsonUtilsNew3.toObject(json, GenA.class, String.class, Define.class, Integer.class);
//        System.out.println(gen.name);
//        System.out.println(gen.def.defineName);
    }
    
    @Test
    public void testToObjGenric2() throws Exception{
        GenALocal<String, DefineLocal, Map<String, Integer>> gen = new GenALocal<>();
        gen.kk = "";
        gen.vv = new HashMap<>();
        gen.vv.put("匪警", 110);
        gen.vv.put("火警", 120);
        gen.name = "GenA Name";
        DefineLocal def = new DefineLocal();
        def.defineInt = 100;
        def.defineName = "define name";
        gen.tt = def;
        gen.def = def;
        
        String json = JsonUtilsNew3.toJson(gen);
        System.out.println(json);
        json = JsonUtils.toJson(gen);
        System.out.println(json);
        
        gen = JsonUtilsNew3.toObject(json, GenALocal.class, String.class, DefineLocal.class, Integer.class);
//        System.out.println(gen.name);
//        System.out.println(gen.def.defineName);
        gen = JsonUtils.toObject(json, new TypeToken<GenA<String, Define, Map<String, Integer>>>(){}.getType());
        
        System.out.println(gen.name);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    @Test
    public void testToObjGenric23() throws Exception{
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
        
        gen = JsonUtilsNew3.toObject(json, GenA.class, String.class, Define.class, Integer.class);
        System.out.println();
        // gen = JsonUtils.toObject(json, new TypeToken<GenA<String, Define, Integer>>(){}.getType());
        // System.out.println();
        System.out.println(JsonUtils.toJson(gen));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    class GenALocal<K, T, V> {
        public GenALocal() {
        }

        public K kk;

        public T tt;

        public V vv;

        public String name;

        public DefineLocal def;

        // Object this$0;
        // final String this$0 = "ttt";
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

        public DefineLocal getDef() {
            return def;
        }

        public void setDef(DefineLocal def) {
            this.def = def;
        }
    }

    class DefineLocal {
        private String defineName;

        private Integer defineInt;

        public String getDefineName() {
            return defineName;
        }

        public void setDefineName(String defineName) {
            this.defineName = defineName;
        }

        public Integer getDefineInt() {
            return defineInt;
        }

        public void setDefineInt(Integer defineInt) {
            this.defineInt = defineInt;
        }
    }
}
