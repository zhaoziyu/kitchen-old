package com.restaurant.dinner.api.recipe.admin;

import com.restaurant.dinner.api.pojo.po.admin.TbSysUser;

/**
 * 用户相关服务接口
 *
 * @date 2016-09-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface SysUserService {

    /**
     * 通过用户名或手机号登录
     * @param code 用户名或手机号
     * @param password 登录密码
     * @return 是否登录成功
     */
    TbSysUser loginByNameOrPhone(String code, String password)  throws Exception;

}
