package com.restaurant.dinner.api.pojo.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 视图层返回json格式对象的容器类（即Controller中接口的返回类型）
 * 所有通过json进行返回数据的对象均需要放到此类的result属性中
 *
 * @date 2016-09-04
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class JsonObjectVo<T extends Object> implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 8141800867521702858L;
    /**
     * 执行的结果
     * true：执行成功
     * false：执行失败
     */
    private boolean success;

    /**
     * 接口返回码
     */
    private String code;

    /**
     * 接口返回码说明
     */
    private String msg;

    /**
     * 业务返回码
     */
    private String bizCode;

    /**
     * 业务返回码说明
     */
    private String bizMsg;

    /**
     * 返回的数据
     * @data属性可以是继承于Java底层Object的任意对象类型，例如：
     * 可以是一个String、List、HashMap等Java框架中自带的数据结构类型；
     * 可以是自定义的一个包含多类型、多属性的对象；
     */
    private T data;



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizMsg() {
        return bizMsg;
    }

    public void setBizMsg(String bizMsg) {
        this.bizMsg = bizMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
