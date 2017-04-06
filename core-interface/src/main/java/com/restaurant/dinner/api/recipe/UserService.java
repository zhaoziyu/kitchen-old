package com.restaurant.dinner.api.recipe;

import com.restaurant.dinner.api.pojo.po.admin.TbUser;

/**
 * 用户相关操作
 *
 * @date 2017-01-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface UserService {
    /**
     * 通过用户名或手机号登录
     *
     * @param code 用户名或手机号
     * @param password 登录密码
     * @return 是否登录成功
     */
    TbUser loginByNameOrPhone(String code, String password)  throws Exception;
}
