package com.jk.jobs.help.action;

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
public class HelpAction extends BaseAction {

	private static final long serialVersionUID = -2578390639563869095L;

	/**
	 * 
	 * @return
	 */
	public String deliver() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String expert() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String job() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String resume() {
		return SUCCESS;
	}

}
