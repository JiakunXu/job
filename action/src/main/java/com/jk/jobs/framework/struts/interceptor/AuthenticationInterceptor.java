package com.jk.jobs.framework.struts.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.jk.jobs.api.ca.ICAService;
import com.jk.jobs.api.region.bo.Region;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * @author JiakunXu
 * 
 */
@Component
public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -7498838714747075663L;

	private static final String OAUTH = "oauth2";

	private static final String REGION = "region";

	private ICAService caService;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {

		@SuppressWarnings("rawtypes")
		Map session = invocation.getInvocationContext().getSession();
		String passport = (String) session.get("ACEGI_SECURITY_LAST_PASSPORT");

		if (StringUtils.isEmpty(passport)) {
			// TODO
			return OAUTH;
		}

		Region region = (Region) session.get("ACEGI_SECURITY_LAST_REGION");
		if (region == null) {
			// TODO
			// return REGION;
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

		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			return url.toString() + "?" + queryString;
		}

		return url.toString();
	}

	private void setAttribute(String name, Object o) {
		// 获取当前applicationContex
		ActionContext ctx = ActionContext.getContext();
		// Map map = ctx.getSession()
		// 设置当前请求的URL
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		request.setAttribute(name, o);
	}

	/**
	 * actionName.
	 * 
	 * @return
	 */
	private String getActionName() {
		String actionName = null;
		// 获取当前applicationContex
		ActionContext ctx = ActionContext.getContext();
		// Map map = ctx.getSession()
		// 设置当前请求的URL
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuffer url = request.getRequestURL();
		int index = url.lastIndexOf(request.getContextPath()) + request.getContextPath().length();
		actionName = url.substring(index, url.length());
		return actionName;
	}

	public ICAService getCaService() {
		return caService;
	}

	public void setCaService(ICAService caService) {
		this.caService = caService;
	}

}
