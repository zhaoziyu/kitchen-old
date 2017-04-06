package com.restaurant.dinner.admin.controller.base;

import com.restaurant.dinner.admin.ProjectProperties;
import com.restaurant.dinner.api.pojo.po.admin.TbSysUser;
import com.restaurant.dinner.api.pojo.vo.JsonVo;
import com.restaurant.dinner.api.recipe.admin.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录接口
 *
 * @date 2016-11-06
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    private final static String LOGIN_MODE_NAME_OR_PHONE = "1";//登录方式：用户名或手机号（默认）
    private final static String LOGIN_MODE_PHONE_IDENTIFYING_CODE = "2";//登录方式：手机验证码
    private final static String LOGIN_MODE_QR_CODE = "3";//登录方式：二维码扫描

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/doLogin")
    @ResponseBody
    public JsonVo<Object> doLogin(HttpServletRequest request, HttpSession httpSession) {
        JsonVo<Object> jsonVo = new JsonVo<>();
        String loginMode = request.getParameter("mode");//登录方式（根据登录方式判断所需的请求参数）
        TbSysUser user = null;
        if (loginMode == null || LOGIN_MODE_NAME_OR_PHONE.equals(loginMode)) {
            // 登录方式：用户名或手机号（默认）
            String logincode = request.getParameter("code");
            if (logincode == null) {
                jsonVo.setSuccess(false);
                jsonVo.setMsg("未输入用户名");
                return jsonVo;
            }
            String password = request.getParameter("password");
            if (password == null) {
                jsonVo.setSuccess(false);
                jsonVo.setMsg("未输入密码");
                return jsonVo;
            }
            try {
                user = sysUserService.loginByNameOrPhone(logincode, password);
            } catch (Exception e) {
                e.printStackTrace();

                jsonVo.setSuccess(false);
                jsonVo.setMsg(e.getMessage());
                return jsonVo;
            }

        } else if (LOGIN_MODE_PHONE_IDENTIFYING_CODE.equals(loginMode)) {
            // 登录方式：手机验证码
        } else if (LOGIN_MODE_QR_CODE.equals(loginMode)) {
            // 登录方式：二维码扫描
        } else {
            jsonVo.setSuccess(false);
            jsonVo.setMsg("登录方式不存在");
            return jsonVo;
        }

        if (user != null) {
            httpSession.setAttribute(ProjectProperties.SESSION_USER_FULL_NAME, user.getFullName());
            httpSession.setAttribute(ProjectProperties.SESSION_USER_ID, user.getId());
            jsonVo.setSuccess(true);
            return jsonVo;
        } else {
            jsonVo.setSuccess(false);
            jsonVo.setMsg("用户名或密码错误");
            return jsonVo;
        }
    }

    @RequestMapping("/doLogout")
    public String doLogout(ModelMap map, HttpSession httpSession) {
        httpSession.removeAttribute(ProjectProperties.SESSION_USER_FULL_NAME);
        httpSession.removeAttribute(ProjectProperties.SESSION_USER_ID);

        return "redirect:/login";// 跳转到[/login]处理器
    }
}
