package com.jk.jobs.api.user.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserJobCat implements Serializable {

	private static final long serialVersionUID = 1370083109385833245L;

	private Long id;

	private Long userJobId;

	/**
	 * 模块.
	 */
	private Long jobCId;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private String jobCName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserJobId() {
		return userJobId;
	}

	public void setUserJobId(Long userJobId) {
		this.userJobId = userJobId;
	}

	public Long getJobCId() {
		return jobCId;
	}

	public void setJobCId(Long jobCId) {
		this.jobCId = jobCId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getJobCName() {
		return jobCName;
	}

	public void setJobCName(String jobCName) {
		this.jobCName = jobCName;
	}

}
