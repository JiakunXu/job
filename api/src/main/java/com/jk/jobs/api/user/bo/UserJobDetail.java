package com.jk.jobs.api.user.bo;

import com.jk.jobs.api.resume.bo.ResumeDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserJobDetail extends ResumeDetail {

	private static final long serialVersionUID = 9161679107202805821L;

	private Long id;

	private Long userJobId;

	private String modifyUser;

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

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
