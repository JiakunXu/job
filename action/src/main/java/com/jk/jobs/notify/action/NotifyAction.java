package com.jk.jobs.notify.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.notify.INotifyService;
import com.jk.jobs.api.notify.bo.Notify;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class NotifyAction extends BaseAction {

	private static final long serialVersionUID = -2476834600926301892L;

	@Resource
	private INotifyService notifyService;

	private List<Notify> notifyList;

	/**
	 * 
	 * @return
	 */
	public String list() {
		notifyList = notifyService.getNotifyList(this.getUser().getUserId());

		return SUCCESS;
	}

	public List<Notify> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<Notify> notifyList) {
		this.notifyList = notifyList;
	}

}
