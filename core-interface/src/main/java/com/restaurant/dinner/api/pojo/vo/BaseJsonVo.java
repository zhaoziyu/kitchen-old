package com.restaurant.dinner.api.pojo.vo;

import java.io.Serializable;

/**
 * Json***Vo的基类
 * 提炼了公共返回参数
 *
 * @date 2017-04-28
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public abstract class BaseJsonVo implements Serializable {
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
}
