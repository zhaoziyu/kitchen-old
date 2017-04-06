package com.restaurant.dinner.admin.controller.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面导航
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @RequestMapping(value = "/welcome")
    public String welcome(ModelMap map) {
        return "welcome";
    }
}
