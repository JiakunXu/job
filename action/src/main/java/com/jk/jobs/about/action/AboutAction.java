package com.jk.jobs.about.action;

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
public class AboutAction extends BaseAction {

	private static final long serialVersionUID = -8147743528568443408L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String agreement() {
		return SUCCESS;
	}

}
