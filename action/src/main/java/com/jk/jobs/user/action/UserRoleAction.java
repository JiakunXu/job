package com.jk.jobs.user.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class UserRoleAction extends BaseAction {

	private static final long serialVersionUID = -2093998178883324339L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
