package com.restaurant.dinner.request.domain;

/**
 * 请求服务接口的领域模型（示例）
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DemoDomain {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
