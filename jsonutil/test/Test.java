/*
 * 文件名：Test.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： Test.java
 * 修改人：tianzhong
 * 修改时间：2016年7月15日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.better517na.forStudy.advanced.reflect.jsonutil.model.ClassContainer;
import com.better517na.forStudy.advanced.reflect.jsonutil.test.JsonTest3.GenALocal;
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
public class Test {
    public static void main(String[] args) {
        // 
        String remark = "{\"keyID\":\"thrift_test_keyid\",\"provinceName\":\"四川\",\"city\":{\"cityCode\":10816,\"boost\":{\"boostId\":250,\"boostName\":\"Deric Rose 7\"}},\"code\":34028,\"isAccountAuthedDesc\":false,\"state\":0,\"stateName\":\"state\",\"addTime\":\"2016-10-13 16:50:01\",\"list\":[\"k\",\"o\",\"b\",\"e\"],\"boosts\":[{\"boostId\":50,\"boostName\":\"Ultra Boost\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}},{\"boostId\":100,\"boostName\":\"Crazy Light\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}}],\"strList\":[[\"k\",\"o\",\"b\",\"e\"],[\"k\",\"o\",\"b\",\"e\"]],\"objList\":[[{\"boostId\":50,\"boostName\":\"Ultra Boost\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}},{\"boostId\":100,\"boostName\":\"Crazy Light\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}}],[{\"boostId\":50,\"boostName\":\"Ultra Boost\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}},{\"boostId\":100,\"boostName\":\"Crazy Light\",\"mapObj\":{\"bleach\":\"stone\",\"onepiece\":\"luffy\",\"naruto\":\"hatchi\"}}]]}";
        Matcher matcher = Pattern.compile("\"keyID\":\".*?\",").matcher(remark);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        String str = getFieldStr(remark, "objList", "[");
        System.out.println(str);
    }
    
    static String getFieldStr(String str, String beginStr, String beginSign) {
        StringBuffer sb = new StringBuffer();
        String endSign = "{".equals(beginSign) ? "}" : "]"; // 结束标记
        int count = 1; // 开始标记计数
        boolean existsYinhao = false; // 存在前引号
        String tmp = "\"" + beginStr + "\":" + beginSign;
        int index = str.indexOf(tmp) + tmp.length();
        sb.append(tmp);
        while (index < str.length() && count > 0) {
            tmp = String.valueOf(str.charAt(index));
            sb.append(tmp);
            existsYinhao = "\"".equals(tmp) ? !existsYinhao : existsYinhao;
            if (!existsYinhao) {
                count = beginSign.equals(tmp) ? count + 1 : (endSign.equals(tmp) ? count - 1 : count);
            }
            index++;
        }
        
        return sb.toString();
    }
    
    @org.junit.Test
    public void t1() {
        Field[] fds = GTest.class.getDeclaredFields();
        for (Field fd : fds) {
            System.out.println(fd.getGenericType());
        }
    }
    
    class GTest {
        List<String> list;
        char s;
    }

    @org.junit.Test
    public void ttttttttt2() {
        Type[] ttt = {
                String.class, new ClassContainer(GenALocal.class, String.class, Integer.class)
        };
        for (Type type : ttt) {
            String sss = type.toString();
            if (type instanceof Class) {
                sss += ": Class";
            } else if (type instanceof ClassContainer) {
                sss += ": ClassContainer";
            }
            
            System.out.println(sss);
        }
    }
    
    @org.junit.Test
    public void ttttttttt3() {
        Map<String, String> map = new HashMap<>();
        map.put("Ko\",\"be", "Los Ange\",\"les Lakers");
        map.put("Jordan", "Chicago Bulls");
        map.put("Yao", "Houston Rockets");
        String json = JsonUtils.toJson(map);
        System.out.println(json);
        List<String> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1);
        StringBuffer sb = new StringBuffer();
        boolean existYinhao = false; // 
        int yinhaoCount = 0;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            sb.append(c);
            existYinhao = ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? !existYinhao : existYinhao;
            yinhaoCount += ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? 1 : 0;
            if (i < 1) {
                continue;
            }
            if (!existYinhao && yinhaoCount == 4) {
                if ((',' == c && '"' == json.charAt(i - 1) && '"' == json.charAt(i + 1)) || i == json.length() - 1) {
                    if (',' == sb.charAt(sb.length() - 1)) {
                        list.add(sb.toString().substring(0, sb.length() - 1));
                    } else {
                        list.add(sb.toString());
                    }
                    sb.setLength(0);
                    existYinhao = false;
                    yinhaoCount = 0;
                }
            }
        }
        
        for (String string : list) {
            System.out.println(string);
        }
    } 
}
