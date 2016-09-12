package com.jk.jobs.api.notify.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Notify implements Serializable {

	private static final long serialVersionUID = 86400696513326334L;

	private Long notifyId;

	private Long userId;

	private Long notifyCId;

	private String content;

	private String createDate;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private String notifyCName;

	public Long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getNotifyCId() {
		return notifyCId;
	}

	public void setNotifyCId(Long notifyCId) {
		this.notifyCId = notifyCId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getNotifyCName() {
		return notifyCName;
	}

	public void setNotifyCName(String notifyCName) {
		this.notifyCName = notifyCName;
	}

}
