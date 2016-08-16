package com.jk.jobs.facebook.action;

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
public class FacebookAction extends BaseAction {

	private static final long serialVersionUID = 7805255345775229607L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
