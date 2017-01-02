/*
 * 文件名：JsonUtilException.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： JsonUtilException.java
 * 修改人：tianzhong
 * 修改时间：2016年11月8日
 * 修改内容：新增
 */
package com.better517na.forStudy.advanced.reflect.jsonutil.exception;

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
public class JsonUtilException extends Exception {

    /**
     * 添加字段注释.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 构造函数.
     * 
     */
    public JsonUtilException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数.
     * 
     */
    public JsonUtilException() {
        super();
    }

}
