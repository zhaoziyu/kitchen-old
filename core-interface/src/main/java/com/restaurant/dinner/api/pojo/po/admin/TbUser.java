package com.restaurant.dinner.api.pojo.po.admin;

import com.restaurant.dinner.api.pojo.po.PoBase;

import java.util.Date;

/**
 * 客户（业务用户）基本信息 - tb_user
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TbUser extends PoBase<Object> {
    private Integer id;

    private String loginName;

    private String loginPassword;

    private String dynamicSalt;

    private String fullName;

    private String nickname;

    private String email;

    private String phone;

    private String headImgUri;

    private Date registerTime;

    private Byte state;

    private Date createTime;

    private Date updateTime;

    @Override
    public void cloneSelf(Object bean) throws CloneNotSupportedException {
        throw new CloneNotSupportedException("未实现cloneSelf");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getDynamicSalt() {
        return dynamicSalt;
    }

    public void setDynamicSalt(String dynamicSalt) {
        this.dynamicSalt = dynamicSalt == null ? null : dynamicSalt.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getHeadImgUri() {
        return headImgUri;
    }

    public void setHeadImgUri(String headImgUri) {
        this.headImgUri = headImgUri == null ? null : headImgUri.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}