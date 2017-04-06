package com.restaurant.dinner.admin.controller;

import com.restaurant.dinner.admin.ProjectProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 管理后台主页面控制器
 *
 * @date 2016-09-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    @RequestMapping(value = "/login")
    public String loginPage(ModelMap map) {
        map.put("projectName", ProjectProperties.PROJECT_NAME);
        map.put("loginSubhead", ProjectProperties.PROJECT_LOGIN_SUBHEAD);
        return "login";
    }

    @RequestMapping("/")
    public String main(ModelMap map, HttpSession httpSession) {
        map.put("projectName", ProjectProperties.PROJECT_NAME);
        map.put("loginName", httpSession.getAttribute(ProjectProperties.SESSION_USER_FULL_NAME));
        map.put("userId", httpSession.getAttribute(ProjectProperties.SESSION_USER_ID));
        return "main";
    }
}
