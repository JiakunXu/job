package com.jk.jobs.user.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -7752149287093239697L;

	@Resource
	private IUserService userService;

	private String userName;

	/**
	 * 
	 * @return
	 */
	public String detail() {
		return SUCCESS;
	}

	/**
	 * 修改用户名.
	 * 
	 * @return
	 */
	public String setUserName() {
		return SUCCESS;
	}

	public String updateUserName() {
		User user = this.getUser();

		BooleanResult result = userService.setUserName(user.getUserId(), userName);

		if (result.getResult()) {
			user.setUserName(userName.trim());

			HttpSession session = this.getSession();
			session.setAttribute("ACEGI_SECURITY_LAST_LOGINUSER", user);

			this.setResourceResult("设置名字成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
