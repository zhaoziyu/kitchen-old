package com.restaurant.dinner.api.pojo.vo.demo;

/**
 * Demo
 *
 * @date 2016-11-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DemoPerson {

    private String name;
    private String phone;

    public DemoPerson() {

    }
    public DemoPerson(String name, String phone) {
        this.setName(name);
        this.setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
