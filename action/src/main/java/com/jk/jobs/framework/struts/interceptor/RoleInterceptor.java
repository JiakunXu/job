package com.jk.jobs.framework.struts.interceptor;

import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * @author JiakunXu
 * 
 */
@Component
public class RoleInterceptor implements Interceptor {

	private static final long serialVersionUID = -7498838714747075663L;

	private static final String ROLE = "role";

	@Resource
	protected Properties env;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {

		@SuppressWarnings("rawtypes")
		Map session = invocation.getInvocationContext().getSession();
		Long roleId = (Long) session.get("ACEGI_SECURITY_LAST_USER_ROLE");

		if (roleId == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().setAttribute("ACEGI_SECURITY_REDIRECT_URL", getUrl());

			// TODO
			return ROLE;
		}

		return invocation.invoke();
	}

	private String getUrl() {
		// 获取当前applicationContex
		ActionContext ctx = ActionContext.getContext();
		// Map map = ctx.getSession()
		// 设置当前请求的URL
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuffer url = request.getRequestURL();
		int index = url.lastIndexOf(request.getContextPath()) + request.getContextPath().length();

		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			return env.getProperty("appUrl") + url.substring(index, url.length()) + "?" + queryString;
		}

		return env.getProperty("appUrl") + url.substring(index, url.length());
	}

}
