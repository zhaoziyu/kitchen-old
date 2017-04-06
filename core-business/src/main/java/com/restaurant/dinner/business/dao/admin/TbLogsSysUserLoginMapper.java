package com.restaurant.dinner.business.dao.admin;

import com.restaurant.dinner.api.pojo.po.admin.TbLogsSysUserLogin;
import org.springframework.stereotype.Repository;

@Repository
public interface TbLogsSysUserLoginMapper {
    int insert(TbLogsSysUserLogin record);

    int insertSelective(TbLogsSysUserLogin record);

    TbLogsSysUserLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLogsSysUserLogin record);

    int updateByPrimaryKey(TbLogsSysUserLogin record);
}