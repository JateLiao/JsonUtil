/*
 * 文件名：TypeHelper.java
 * 版权：Copyright 2007-2017 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TypeHelper.java
 * 修改人：tianzhong
 * 修改时间：2017年3月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.model.reflect;

/**
 * TODO 添加类的一句话简单描述.
 * 
 * @author     tianzhong
 */
public class TypeHelper {

    /**
     * TODO 返回hashcode或者0.
     */
    public static int hashCodeOrZero(Object o) {
      return o != null ? o.hashCode() : 0;
    }

}
