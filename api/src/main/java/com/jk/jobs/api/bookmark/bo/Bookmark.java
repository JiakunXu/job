package com.jk.jobs.api.bookmark.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Bookmark implements Serializable {

	private static final long serialVersionUID = 2891130706698403763L;

	private Long id;

	private Long userId;

	private Long jobId;

	private String modifyUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
