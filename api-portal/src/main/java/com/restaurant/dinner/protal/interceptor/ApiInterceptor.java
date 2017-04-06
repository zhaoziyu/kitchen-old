package com.restaurant.dinner.protal.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录接口调用日志；
 * 安全过滤；
 *
 * @date 2016-09-21
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // TODO 验证签名信息
        // TODO 记录调用日志（先写入缓存，空闲时间统一往数据库写）
        System.out.println("TODO 验证签名信息");
        System.out.println("TODO 记录接口调用日志");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
