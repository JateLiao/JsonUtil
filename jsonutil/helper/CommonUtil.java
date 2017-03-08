/*
 * 文件名：CommonUtil.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： CommonUtil.java
 * 修改人：tianzhong
 * 修改时间：2016年11月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.better517na.forStudy.advanced.reflect.jsonutil.model.SingleJSon;

/**
 * 打辅助的.
 * 
 * @author     tianzhong
 */
@SuppressWarnings("rawtypes")
public class CommonUtil {

    
    /*********************************************** 分 割 线 : 公共方法  ****************************************************************************************************************/

    /**
     * 干掉多余的逗号
     */
    public static void deleteExtraComma(StringBuffer sb) {
        String str = sb.toString();
        sb.setLength(0);
        sb.append(str.replaceAll(",,", ",").replaceAll(",]", "]").replaceAll(",}", "}"));
        sb.setLength(sb.length() - 1);
    }
    
    /**
     * 是否为List类型
     */
    public static boolean isListType(Class<?> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为Map类型
     */
    public static boolean isMapType(Class<?> clazz) {
        if (Map.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是基本类型:四类八种，String，Date，BigDecimal.
     * 
     */
    public static boolean isBasicType(Class type) {
        if (type.isPrimitive() || type == String.class || type == BigDecimal.class || type == Date.class || type == Integer.class || type == Boolean.class 
                || type == Double.class || type == Short.class || type == Long.class || type == Float.class || type == Character.class) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否是自定义model.
     */
    public static boolean isDefinedModel(Class clazz) {
        if (clazz.getName().startsWith("java")) { // package名以java开头，一般不符合规范
            return false;
        }

        return true;
    }

    /**
     * 判空
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } /*else if (obj instanceof String) { // ""这样的空字符串应该被保留下来
            if (((String) obj).isEmpty()) {
                return true;
            }
        } */else if (Collection.class.isAssignableFrom(obj.getClass())) {
            if (((Collection) obj).isEmpty()) {
                return true;
            }
        } else if (Map.class.isAssignableFrom(obj.getClass())) {
            if (((Map) obj).isEmpty()) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * 判空：全为空才是空
     */
    public static boolean isNullOrEmpty(Object... objs) {
        boolean res = true;
        for (Object obj : objs) {
            if (!isNullOrEmpty(obj)) {
                res = false;
                break;
            }
        }

        return res;
    }
    
    /**
     * 判空：全为空才是空
     */
    public static boolean isNullOrEmptyClassArr(Class... cls) {
        boolean res = true;
        if (null == cls || cls.length == 0) {
            return true;
        }
        for (Class c : cls) {
            if (!isNullOrEmpty(c)) {
                res = false;
                break;
            }
        }

        return res;
    }
    
    /**
     * 判空：任意一个为空则为空
     */
    public static boolean isNullOrEmptyByAnyOne(Object... objs) {
        for (Object obj : objs) {
            if (isNullOrEmpty(obj)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * 获得单个String值的数组.
     * 
     * [{},{},{}]
     * 
     * 注意：String字段的值内嵌引号和"},{"时的处理
     */
    public static String[] getSingleStringValueArr(String json) {
        List<String> valList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        boolean existYinhao = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            existYinhao = ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? !existYinhao : existYinhao;
            if (i == json.length() - 1 || (',' == c && '}' == json.charAt(i - 1) && '{' == json.charAt(i + 1) && !existYinhao)) {
                sb.append((i == json.length() - 1) ? c : ""); // 拼接最后一个字符
                valList.add(sb.toString());
                sb.setLength(0);
                continue;
            }
            sb.append(c);
        }
        
        String[] valArr = new String[valList.size()];
        return valList.toArray(valArr);
    }
    
    /**
     * TODO 获取对应的完整json传，针对基本类型及其包装类型之外的复杂类型.
     * 
     * @param str 传入字符串.
     * @param beginStr 开始字段.
     * @param beginSign 开始符号.
     * @return 对应的完整的字符串.
     */
    public static String getFieldStr4Others(String str, String beginStr, String beginSign) {
        if (CommonUtil.isNullOrEmpty(str) || CommonUtil.isNullOrEmpty(beginStr) || CommonUtil.isNullOrEmpty(beginSign)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        String endSign = "{".equals(beginSign) ? "}" : "]"; // 结束标记 
        int count = 1; // 开始标记计数
        boolean existsYinhao = false; // 存在前引号
        String tmp = "\"" + beginStr + "\":" + beginSign;
        int index = str.indexOf(tmp);
        if (-1 == index && !"".equals(beginStr)) {
            return null;
        } else if (-1 == index && "".equals(beginStr)) {
            index = 0;
        } else {
            sb.append(beginSign);
            index += tmp.length();
        }
        
        // index = (-1 == index && "".equals(beginStr)) ? 0 : index + tmp.length();
        
        while (index < str.length() && count > 0) {
            char c = str.charAt(index);
            tmp = String.valueOf(c);
            sb.append(c);
            existsYinhao = ('"' == c && '\\' != str.charAt(index - 1)) ? !existsYinhao : existsYinhao;
            // existsYinhao = "\"".equals(tmp) ? !existsYinhao : existsYinhao;
            if (!existsYinhao) { // 引号内的"}","]"不计入统计
                count = beginSign.equals(tmp) ? count + 1 : (endSign.equals(tmp) ? count - 1 : count);
            }
            index++;
        }
        // sb.append(("".equals(beginStr)) ? str.charAt(index - 1) : "");
        
        return sb.toString();
    }
    
    /**
     * TODO 获取对应的完整json传，针对基本类型.
     * 
     * 注意：String类型值内的引号，{，,，[等符号的处理
     * 
     * @param str 传入字符串.
     * @param fieldName 正则.
     * @param needYinhao 是否需要引号
     *        String，Char，Date等类型的值是引号包含的；Integer，Double等类型的值是没有引号包围的 .
     * @return 对应的完整的字符串.
     */
    public static String getFieldStr4Basic(String str, String fieldName, boolean needYinhao) {
        String regex = "\"" + fieldName + "\":";
        String result = null;
        // Pattern pattern = needYinhao ? Pattern.compile(regex + "\".*?\"") : Pattern.compile(regex + ".+?(,|\\}|\\])");
        // Matcher matcher = pattern.matcher(str);
        // while (matcher.find()) {
        // result = matcher.group(0);
        // result = result.substring((needYinhao ? result.indexOf(regex) + 1 : result.indexOf(regex)) + regex.length(), result.length() - 1);
        // break;
        // }
        
        /** 新逻辑 **/
        StringBuffer sb = new StringBuffer();
        boolean existsYinhao = false;
        int index = str.indexOf(regex);
        if (-1 == index) {
            return result;
        }
        index += regex.length();
        if (!needYinhao) { // 不需要引号：BigDecimal, Integer, int...
            for (int i = index; i < str.length(); i++) {
                char c = str.charAt(i);
                if (',' == c || '}' == c || ']' == c) {
                    break;
                }
                sb.append(c);
            }
            return sb.toString();
        }
        for (int i = index; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(c);
            existsYinhao = ('"' == c && '\\' != str.charAt(i - 1)) ? !existsYinhao : existsYinhao;
            if (!existsYinhao) {
                break;
            }
        }
        result = sb.toString();
        if ('"' == result.charAt(0) && '"' == result.charAt(result.length() - 1) && result.length() > 2) { // result.length() > 2: 值为""的这种情况就不用再去掉首位引号了
            result = result.substring(1, result.length() - 1);
        }
        
        return result;
    }
    
   /**
    * TODO 获取每个字段对应的单个json信息，以Map形式返回
    */
    public static Map<String, SingleJSon> getSingleJsonValueByMap(String json) {
        Map<String, SingleJSon> res = new HashMap<>();
        
        List<SingleJSon> list = getSingleJsonValue(json);
        for (SingleJSon sin : list) {
            res.put(sin.getFieldName(), sin);
        }
        
        return res;
    }
    
    /**
     * TODO 获取json对.
     * 
     * 比如：{"123":"123","321":"321"}==》得到List[0]:"123":"123", List[1]:"321":"321"
     */
    public static List<SingleJSon> getSingleJsonValue(String json) {
        List<SingleJSon> list = new ArrayList<>();
        int signCount = 0; // 标识计数
        boolean existsYinhao = false;
        boolean confirmColon = false; // 已经确定冒号
        boolean singleDone = false; // 完成单个
        int yinhaoCount = 0; // 引号计数
        int colonIndex = 0; // 冒号位置（从0开始计算）
        String beginSign = null; // 开始标识
        StringBuffer sb = new StringBuffer();
        json = json.substring(1, json.length() - 1);
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            sb.append(c);
            colonIndex++;
            existsYinhao = ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? !existsYinhao : existsYinhao;
            yinhaoCount += ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? 1 : 0;
            if (yinhaoCount == 2 && !existsYinhao && ':' == json.charAt(Math.min(i + 1, json.length() - 1))) {
                list.add(new SingleJSon());
                list.get(Math.max(0, list.size() - 1)).setColonIndex(colonIndex);
                confirmColon = true;
                char b = json.charAt(i + 2);
                beginSign = "\"{[".contains(String.valueOf(b)) ? String.valueOf(b) : null;
            }
            
            if (!confirmColon) {
                continue;
            }
            if (null == beginSign) {
                if (!existsYinhao && (c == ',' || i == json.length() - 1)) {
                    singleDone = true;
                }
            } else if ("\"".equals(beginSign)) {
                // signCount += ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? 1 : 0;
                if (!existsYinhao && (c == ',' || i == json.length() - 1) && yinhaoCount == 4) {
                    singleDone = true;
                }
            } else if ("{".equals(beginSign)) {
                signCount += (!existsYinhao && '{' == c) ? 1 : 0;
                signCount -= (!existsYinhao && '}' == c) ? 1 : 0;
                if (!existsYinhao && (c == ',' || i == json.length() - 1) && signCount == 0) {
                    singleDone = true;
                }
            } else if ("[".equals(beginSign)) {
                signCount += (!existsYinhao && '[' == c) ? 1 : 0;
                signCount -= (!existsYinhao && ']' == c) ? 1 : 0;
                if (!existsYinhao && (c == ',' || i == json.length() - 1) && signCount == 0) {
                    singleDone = true;
                }
            }
            
            if (singleDone) {
                if (',' == sb.toString().charAt(sb.length() - 1)) {
                    sb.setLength(sb.length() - 1);
                }
                int cci = list.get(list.size() - 1).getColonIndex(); // 当前引号位置
                list.get(list.size() - 1).setValue(sb.toString());
                list.get(list.size() - 1).setFieldName(sb.toString().substring(1, cci - 1));
                String fieldValue = sb.toString().charAt(cci + 1) == '"' ? sb.toString().substring(cci + 2, sb.length() - 1) : sb.toString().substring(cci + 1, sb.length());
                list.get(list.size() - 1).setFieldValue(fieldValue);
                sb.setLength(0);
                yinhaoCount = 0;
                colonIndex = 0;
                beginSign = null;
                confirmColon = false;
                singleDone = false;
            }
        }
        
        return list;
    }
    
    /**
     * TODO 获取List类型的json的每个元素的单独的json，并以List形式返回.
     * 
     * ["","",""] 
     * [x,x,x] 
     * [{},{},{}] 
     * [[],[],[]]
     */
    public static List<String> getSingleJsonFromList(String json){
        List<String> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1);
        StringBuffer sb = new StringBuffer();
        String beginSign = null;
        boolean existsYinhao = false;
        boolean singleDone = false; // 完成单个
        beginSign = "\"{[".contains(String.valueOf(json.charAt(0))) ? String.valueOf(json.charAt(0)) : beginSign;
        int signCount = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            sb.append(c);
            existsYinhao = ('"' == c && '\\' != json.charAt(Math.max(i - 1, 0))) ? !existsYinhao : existsYinhao;
            
            if (null == beginSign) {
                if (',' == c || i == json.length() - 1) {
                    singleDone = true;
                }
            } else if ("\"".equals(beginSign)) {
                if (!existsYinhao && (',' == c || i == json.length() - 1)) {
                    singleDone = true;
                }
            } else if ("{".equals(beginSign)) {
                signCount += (!existsYinhao && '{' == c) ? 1 : 0;
                signCount -= (!existsYinhao && '}' == c) ? 1 : 0;
                if (!existsYinhao && signCount == 0 && (i == json.length() - 1 || c == ',')) {
                    singleDone = true;
                }
                
            } else if ("[".equals(beginSign)) {
                signCount += (!existsYinhao && '[' == c) ? 1 : 0;
                signCount -= (!existsYinhao && ']' == c) ? 1 : 0;
                if (!existsYinhao && (signCount == 0 || i == json.length() - 1)) {
                    singleDone = true;
                }
            }
            
            if (singleDone) {
                if (',' == sb.toString().charAt(sb.length() - 1)) {
                    sb.setLength(sb.length() - 1);
                }
                list.add(sb.toString());
                sb.setLength(0);
                singleDone = false;
                signCount = 0;
            }
        }
        
        return list;
    }
    


    /**
     * TODO 返回hashcode或者0.
     */
    public static int hashCodeOrZero(Object o) {
      return o != null ? o.hashCode() : 0;
    }
}
