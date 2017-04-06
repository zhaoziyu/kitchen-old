package com.restaurant.dinner.api.pojo.po.admin;

import com.restaurant.dinner.api.pojo.po.PoBase;

import java.util.Date;

/**
 * 用户登录日志表 - tb_logs_sys_user_login
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class TbLogsSysUserLogin extends PoBase<TbLogsSysUserLogin> {
    private Integer id;

    private Integer userId;

    private Date loginTime;

    private Integer loginPass;

    private Date createTime;

    private Date updateTime;

    @Override
    public void cloneSelf(TbLogsSysUserLogin bean) {
        this.setId(bean.getId());
        this.setUserId(bean.getUserId());
        this.setLoginTime(bean.getLoginTime());
        this.setLoginPass(bean.getLoginPass());
        this.setCreateTime(bean.getCreateTime());
        this.setUpdateTime(bean.getUpdateTime());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(Integer loginPass) {
        this.loginPass = loginPass;
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