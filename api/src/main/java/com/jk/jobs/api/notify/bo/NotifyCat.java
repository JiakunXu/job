package com.jk.jobs.api.notify.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class NotifyCat implements Serializable {

	private static final long serialVersionUID = 2633937333678600221L;

	private Long notifyCId;

	private String notifyCName;

	public Long getNotifyCId() {
		return notifyCId;
	}

	public void setNotifyCId(Long notifyCId) {
		this.notifyCId = notifyCId;
	}

	public String getNotifyCName() {
		return notifyCName;
	}

	public void setNotifyCName(String notifyCName) {
		this.notifyCName = notifyCName;
	}

}
