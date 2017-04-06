package com.restaurant.dinner.protal.controller;

import com.restaurant.dinner.api.pojo.vo.JsonVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 服务接口主页面控制器
 *
 * @date 2016-12-31
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping(value = "/")
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public JsonVo<String> main(ModelMap map, HttpSession httpSession) {
        JsonVo<String> result = new JsonVo<>();
        result.setSuccess(true);
        result.setMsg("调用成功");
        result.setData("欢迎访问微服务接口");
        return result;
    }

}
