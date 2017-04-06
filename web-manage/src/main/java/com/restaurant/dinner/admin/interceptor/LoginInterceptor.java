package com.restaurant.dinner.admin.interceptor;

import com.restaurant.dinner.admin.ProjectProperties;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录安全过滤器
 *
 * @date 2016-09-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Object userId = session.getAttribute(ProjectProperties.SESSION_USER_ID);

        String uri = httpServletRequest.getRequestURI();

        if ("/login".equals(uri)) {
            if (userId == null) {
                return true;
            } else {
                httpServletResponse.sendRedirect("/");// TODO 可以允许用户访问登录页面，登录页面显示已经登录的用户信息
                return false;
            }
        }

        if (userId == null) {
            if (!"/".equals(uri)) {
                httpServletResponse.sendRedirect("/login");
            } else {
                httpServletResponse.sendRedirect("/login");
            }

            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
