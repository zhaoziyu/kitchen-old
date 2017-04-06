package com.restaurant.dinner.api.pojo.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 视图层返回json格式对象的容器类（即Controller中接口的返回类型均为JsonVo对象）
 * 所有通过json进行返回数据的对象均需要放到此类的result属性中
 *
 * @date 2016-09-04
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class JsonVo<T extends Object> implements Serializable {

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
     * 执行结果的说明
     */
    private String msg;

    /**
     * 错误消息集合
     *
     * tips：
     * #可用于 输入信息的验证，返回每个输入错误的消息
     * #可用于 错误代码和代码对应的描述
     */
    private Map<String, String> errors = new HashMap<String, String>();

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
