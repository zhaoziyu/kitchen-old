package com.restaurant.dinner.api.pojo.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 视图层返回json格式集合的容器类（即Controller中接口的返回类型）
 * 所有通过json进行返回数据的对象均需要放到此类的records属性中
 *
 * @date 2017-04-28
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class JsonRecordVo<T extends Collection> implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -2104776899007974599L;

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
     * @records属性可以是继承于java.util.List的任意对象类型，例如：
     * 可以是一个ArrayList、HashMap等Java框架中自带的数据结构类型；
     */
    private T records;

    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总条目数量
     */
    private int total;

    /**
     * 总页数
     */
    private int pages;

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

    public T getRecords() {
        return records;
    }

    public void setRecords(T records) {
        this.records = records;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
