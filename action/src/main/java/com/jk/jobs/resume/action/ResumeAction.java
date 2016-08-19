package com.jk.jobs.resume.action;

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
public class ResumeAction extends BaseAction {

	private static final long serialVersionUID = -1350198812498987637L;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

}
