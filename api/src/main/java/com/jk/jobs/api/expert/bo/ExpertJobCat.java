package com.jk.jobs.api.expert.bo;

import com.jk.jobs.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ExpertJobCat extends SearchInfo {

	private static final long serialVersionUID = -5583202858459389869L;

	private Long id;

	private Long expertId;

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

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
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
