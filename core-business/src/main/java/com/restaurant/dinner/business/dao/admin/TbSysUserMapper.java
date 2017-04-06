package com.restaurant.dinner.business.dao.admin;

import com.restaurant.dinner.api.pojo.po.admin.TbSysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbSysUserMapper {
    int insert(TbSysUser record);

    int insertSelective(TbSysUser record);

    TbSysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbSysUser record);

    int updateByPrimaryKey(TbSysUser record);

    TbSysUser selectByNameOrPhone(
            @Param(value = "code") String code
    );
}