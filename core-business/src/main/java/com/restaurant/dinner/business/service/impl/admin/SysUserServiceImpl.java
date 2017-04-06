package com.restaurant.dinner.business.service.impl.admin;

import com.restaurant.dinner.api.constant.CommonConstant;
import com.restaurant.dinner.api.constant.state.SysUserStateEnum;
import com.restaurant.dinner.api.pojo.po.admin.TbLogsSysUserLogin;
import com.restaurant.dinner.api.pojo.po.admin.TbSysUser;
import com.restaurant.dinner.api.recipe.admin.SysUserService;
import com.restaurant.dinner.business.dao.admin.TbLogsSysUserLoginMapper;
import com.restaurant.dinner.business.dao.admin.TbSysUserMapper;
import com.restaurant.dinner.market.common.security.KitEncryptionCustom;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @date 2016-09-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private TbSysUserMapper tbSysUserMapper;
    @Autowired
    private TbLogsSysUserLoginMapper tbLogsSysUserLoginMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public TbSysUser loginByNameOrPhone(String code, String password) throws Exception {
        boolean pass = false;
        // 根据用户名和手机号查询用户信息
        TbSysUser tbSysUser = tbSysUserMapper.selectByNameOrPhone(code);
        String cPassword = KitEncryptionCustom.encrypt(password, tbSysUser.getDynamicSalt());
        if (tbSysUser != null) {
            // 用户状态是否正常
            boolean stateNormal = SysUserStateEnum.NORMAL.getCode() == tbSysUser.getState();
            // 密码是否正确
            boolean passwordCorrect = cPassword.equals(tbSysUser.getLoginPassword());
            if (stateNormal && passwordCorrect) {
                pass = true;
                // 更新盐值和新的加密密码
                Pair<String, String> encryptResult = KitEncryptionCustom.encrypt(password);
                String newSalt = encryptResult.getValue0();
                String newCiphertext = encryptResult.getValue1();
                tbSysUser.setDynamicSalt(newSalt);
                tbSysUser.setLoginPassword(newCiphertext);
                tbSysUserMapper.updateByPrimaryKeySelective(tbSysUser);
            }

            // 记录登录日志 TODO 记录其它登录信息，例如登录时的ip地址、登录的国家城市等
            TbLogsSysUserLogin bean = new TbLogsSysUserLogin();
            bean.setUserId(tbSysUser.getId());
            bean.setLoginTime(new Date());
            if (pass) {
                bean.setLoginPass(CommonConstant.YES);
            } else {
                bean.setLoginPass(CommonConstant.NO);
            }
            int index = tbLogsSysUserLoginMapper.insert(bean);
            if (index <= 0) {
                System.out.println("系统错误");
            }
        }

        if (pass) {
            return tbSysUser;
        } else {
            return null;
        }
    }
}
