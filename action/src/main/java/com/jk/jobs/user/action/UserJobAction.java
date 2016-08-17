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
public class UserJobAction extends BaseAction {

	private static final long serialVersionUID = 2578807238664967114L;

	/**
	 * 
	 * @return
	 */
	public String resume() {
		return SUCCESS;
	}

}
