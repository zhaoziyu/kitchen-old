package com.restaurant.dinner.business.dao;

import com.restaurant.dinner.api.pojo.po.admin.TbUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserMapper {
    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);

    TbUser selectByNameOrPhone(
            @Param(value = "code") String code
    );
}