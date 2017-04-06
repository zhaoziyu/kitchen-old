package com.restaurant.dinner.api.constant.state;

/**
 * tb_user表的state 用户状态映射
 *
 * @date 2016-09-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum UserStateEnum {
    DELETE("删除", -1),
    LOCKED("锁定", 0),
    NORMAL("正常", 1),
    PASSWORD_EXPIRE("密码失效", 2);

    private int _code;
    private String _desc;

    UserStateEnum(String desc, int code) {
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
