package com.restaurant.dinner.protal.controller;

import com.restaurant.dinner.api.pojo.vo.JsonObjectVo;
import com.restaurant.dinner.protal.ProjectConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 控制异常（400、404、500等）消息的输出
 *
 * @date 2016-12-26
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/handlerByCode")
    @ResponseBody
    public JsonObjectVo<Object> handlerByCode(HttpServletRequest request, HttpSession httpSession) {
        JsonObjectVo<Object> jsonObjectVo = new JsonObjectVo<>();
        jsonObjectVo.setSuccess(false);

        String errorCode = request.getParameter("code");
        String errorMsg = "";
        if (errorCode == null || errorCode.equals("")) {
            errorMsg = "web.xml中，缺少错误码配置";
        } else {
            if (ProjectConstant.ERROR_CODE_400.equals(errorCode)) {
                errorMsg = "请求参数的语法格式有误（400）";
            } else if (ProjectConstant.ERROR_CODE_404.equals(errorCode)) {
                errorMsg = "请求的资源不存在（404）";
            } else if (ProjectConstant.ERROR_CODE_500.equals(errorCode)) {
                errorMsg = "程序异常（500）";
            } else {
                errorMsg = "Project Constant 未配置相关异常处理器";
            }
        }
        jsonObjectVo.setMsg(errorMsg);

        return jsonObjectVo;
    }
}
