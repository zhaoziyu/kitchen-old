package com.restaurant.dinner.business.service.impl;

import com.restaurant.dinner.api.constant.state.UserStateEnum;
import com.restaurant.dinner.api.pojo.po.admin.TbUser;
import com.restaurant.dinner.api.recipe.UserService;
import com.restaurant.dinner.business.dao.TbUserMapper;
import com.restaurant.dinner.market.common.security.KitEncryptionCustom;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户相关操作
 *
 * @date 2017-01-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public TbUser loginByNameOrPhone(String code, String password) throws Exception {
        boolean pass = false;
        // 根据用户名和手机号查询用户信息
        TbUser tbUser = tbUserMapper.selectByNameOrPhone(code);
        String cPassword = KitEncryptionCustom.encrypt(password, tbUser.getDynamicSalt());
        if (tbUser != null) {
            // 用户状态是否正常
            boolean stateNormal = UserStateEnum.NORMAL.getCode() == tbUser.getState();
            // 密码是否正确
            boolean passwordCorrect = cPassword.equals(tbUser.getLoginPassword());
            if (stateNormal && passwordCorrect) {
                pass = true;
                // 更新盐值和新的加密密码
                Pair<String, String> encryptResult = KitEncryptionCustom.encrypt(password);
                String newSalt = encryptResult.getValue0();
                String newCiphertext = encryptResult.getValue1();
                tbUser.setDynamicSalt(newSalt);
                tbUser.setLoginPassword(newCiphertext);
                tbUserMapper.updateByPrimaryKeySelective(tbUser);
            }
        }

        if (pass) {
            return tbUser;
        } else {
            return null;
        }
    }
}
