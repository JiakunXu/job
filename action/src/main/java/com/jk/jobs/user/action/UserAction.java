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
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -7752149287093239697L;

	/**
	 * 
	 * @return
	 */
	public String detail() {
		return SUCCESS;
	}

}
