package com.restaurant.dinner.api.constant.state;

/**
 * tb_sys_user表的state 用户状态
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum SysUserStateEnum {
    DELETE("删除", -1),
    LOCKED("锁定", 0),
    NORMAL("正常", 1);

    private int _code;
    private String _desc;

    SysUserStateEnum(String desc, int code) {
        this._desc = desc;
        this._code = code;
    }

    public int getCode() {
        return this._code;
    }
    public String getDesc() {
        return this._desc;
    }

    @Override
    public String toString() {
        return String.valueOf(this._code);
    }
}
