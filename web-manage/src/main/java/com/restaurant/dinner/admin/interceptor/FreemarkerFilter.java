package com.restaurant.dinner.admin.interceptor;

import com.restaurant.dinner.admin.util.SpringMvcContextUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Freemarker过滤器
 * Application Lifecycle Listener implementation class FreemarkerFilter
 *
 * @date 2016-11-26
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class FreemarkerFilter implements Filter {

	private Locale locale;

	public void init(FilterConfig filterConfig) throws ServletException {
		String localeStr = filterConfig.getInitParameter("locale");
		if(StringUtils.hasText(localeStr)){
			locale = new Locale(localeStr);
		}else {
			locale = Locale.getDefault();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		try {
			String name = req.getRequestURI();
			name = name.substring(1, name.lastIndexOf(".ftl"));
			FreeMarkerViewResolver viewResolver = SpringMvcContextUtil.getBean(FreeMarkerViewResolver.class);
			View view = viewResolver.resolveViewName(name, locale);
			view.render(null, req, res);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
