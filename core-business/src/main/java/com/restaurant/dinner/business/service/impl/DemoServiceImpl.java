package com.restaurant.dinner.business.service.impl;

import com.restaurant.dinner.api.pojo.po.admin.TbSysUser;
import com.restaurant.dinner.api.pojo.vo.demo.DemoPerson;
import com.restaurant.dinner.api.recipe.demo.DemoService;
import com.restaurant.dinner.business.dao.admin.TbSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Demo
 *
 * @date 2016-12-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;

    @Override
    public DemoPerson getDemoPerson(int userId) {
        TbSysUser tbSysUser = tbSysUserMapper.selectByPrimaryKey(userId);
        DemoPerson demoPerson = new DemoPerson();
        demoPerson.setName(tbSysUser.getFullName() + "SysUserMapper");
        demoPerson.setPhone(tbSysUser.getPhone());
        return demoPerson;
    }
}
