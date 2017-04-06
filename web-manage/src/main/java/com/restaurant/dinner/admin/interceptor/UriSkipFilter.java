package com.restaurant.dinner.admin.interceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * URI正确性验证过滤器
 * 对于直接请求.ftl的URI，直接返回404
 *
 * @date 2016-09-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class UriSkipFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 跳转到404页面
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void destroy() {

    }
}