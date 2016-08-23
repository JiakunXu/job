package com.jk.jobs.api.user.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserJob implements Serializable {

	private static final long serialVersionUID = -2212484428411343955L;

	private Long id;

	private Long userId;

	private Long jobId;

	/**
	 * 应聘时间.
	 */
	private String createDate;

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
