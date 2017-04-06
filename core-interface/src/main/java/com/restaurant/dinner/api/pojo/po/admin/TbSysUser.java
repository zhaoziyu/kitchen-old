package com.restaurant.dinner.api.pojo.po.admin;

import com.restaurant.dinner.api.pojo.po.PoBase;

import java.util.Date;

/**
 * 系统用户（管理用户、员工）基本信息 - tb_sys_user
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TbSysUser extends PoBase<TbSysUser> {

    private Integer id;

    private String loginName;

    private String loginPassword;

    private String dynamicSalt;

    private String fullName;

    private String email;

    private String phone;

    private Date registerTime;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    @Override
    public void cloneSelf(TbSysUser bean) {
        this.setId(bean.getId());
        this.setLoginName(bean.getLoginName());
        this.setLoginPassword(bean.getLoginPassword());
        this.setDynamicSalt(bean.getDynamicSalt());
        this.setFullName(bean.getFullName());
        this.setEmail(bean.getEmail());
        this.setPhone(bean.getPhone());
        this.setRegisterTime(bean.getRegisterTime());
        this.setState(bean.getState());
        this.setCreateTime(bean.getCreateTime());
        this.setUpdateTime(bean.getUpdateTime());
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
        this.dynamicSalt = dynamicSalt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
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