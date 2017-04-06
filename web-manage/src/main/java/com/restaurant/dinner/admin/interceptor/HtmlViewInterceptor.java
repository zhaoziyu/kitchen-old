package com.restaurant.dinner.admin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTML 视图拦截器
 * 用于拦截.html请求，并设置其编码方式
 *
 * @date 2017-01-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class HtmlViewInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 设置请求html资源的编码方式为utf-8
        // HTML页面的<head>中需要配套增加<meta charset="utf-8">和<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
