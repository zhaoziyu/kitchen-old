package com.restaurant.dinner.protal.controller;

import com.restaurant.dinner.api.pojo.po.admin.TbUser;
import com.restaurant.dinner.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.api.recipe.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录接口
 *
 * @date 2017-01-03
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final static String LOGIN_MODE_NAME_OR_PHONE = "1";//登录方式：用户名或手机号（默认）
    private final static String LOGIN_MODE_PHONE_IDENTIFYING_CODE = "2";//登录方式：手机验证码
    private final static String LOGIN_MODE_QR_CODE = "3";//登录方式：二维码扫描

    @Autowired
    private UserService userService;

    @RequestMapping("/doLogin")
    @ResponseBody
    public JsonObjectVo<Object> doLogin(HttpServletRequest request, HttpSession httpSession) {
        JsonObjectVo<Object> jsonObjectVo = new JsonObjectVo<>();
        String loginMode = request.getParameter("mode");//登录方式（根据登录方式判断所需的请求参数）
        TbUser user = null;
        if (loginMode == null || loginMode.isEmpty() || LOGIN_MODE_NAME_OR_PHONE.equals(loginMode)) {
            // 登录方式：用户名或手机号（默认）
            String logincode = request.getParameter("code");
            if(logincode == null) {
                jsonObjectVo.setSuccess(false);
                jsonObjectVo.setMsg("未输入用户名");
                return jsonObjectVo;
            }
            String password = request.getParameter("password");
            if(password == null) {
                jsonObjectVo.setSuccess(false);
                jsonObjectVo.setMsg("未输入密码");
                return jsonObjectVo;
            }

            try {
                user = userService.loginByNameOrPhone(logincode, password);
            } catch (Exception e) {
                e.printStackTrace();

                jsonObjectVo.setSuccess(false);
                jsonObjectVo.setMsg(e.getMessage());
                return jsonObjectVo;
            }

        } else if (LOGIN_MODE_PHONE_IDENTIFYING_CODE.equals(loginMode)) {
            // 登录方式：手机验证码
        } else if (LOGIN_MODE_QR_CODE.equals(loginMode)) {
            // 登录方式：二维码扫描
        } else {
            jsonObjectVo.setSuccess(false);
            jsonObjectVo.setMsg("登录方式不存在");
            return jsonObjectVo;
        }

        if (user != null) {
            // TODO 若api-portal为单点（单机）部署，则在Session中写入登录信息
            // TODO 若api-portal为集群部署，则需要在Redis中写入指定失效时间的用户登录信息
            // TODO 需要配置登录状态验证的过滤器（interceptor），根据实际业务，过滤相关接口的访问

            jsonObjectVo.setSuccess(true);
            return jsonObjectVo;
        } else {
            jsonObjectVo.setSuccess(false);
            jsonObjectVo.setMsg("用户名或密码错误");
            return jsonObjectVo;
        }
    }

    @RequestMapping("/doLogout")
    @ResponseBody
    public JsonObjectVo<Object> doLogout(HttpServletRequest request, HttpSession httpSession) {

        //String id = request.getParameter("id");
        boolean isExist = true;// TODO 判断在Session或缓存中用户ID是否存在
        if (isExist) {
            // TODO 若api-portal为单点（单机）部署，则需要在Session中删除用户登录信息
            // TODO 若api-portal为集群部署，则需要在Redis中删除用户登录信息
        }

        JsonObjectVo<Object> jsonObjectVo = new JsonObjectVo<>();
        jsonObjectVo.setSuccess(true);

        return jsonObjectVo;
    }
}
