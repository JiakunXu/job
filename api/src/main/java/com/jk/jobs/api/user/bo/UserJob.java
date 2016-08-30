package com.jk.jobs.api.user.bo;

import java.util.List;

import com.jk.jobs.api.resume.bo.Resume;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserJob extends Resume {

	private static final long serialVersionUID = -2212484428411343955L;

	private Long userJobId;

	private Long userId;

	private Long jobId;

	private String type;

	/**
	 * 应聘时间.
	 */
	private String createDate;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<UserJobDetail> userJobDetailList;

	private List<UserJobCat> userJobCatList;

	public Long getUserJobId() {
		return userJobId;
	}

	public void setUserJobId(Long userJobId) {
		this.userJobId = userJobId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<UserJobDetail> getUserJobDetailList() {
		return userJobDetailList;
	}

	public void setUserJobDetailList(List<UserJobDetail> userJobDetailList) {
		this.userJobDetailList = userJobDetailList;
	}

	public List<UserJobCat> getUserJobCatList() {
		return userJobCatList;
	}

	public void setUserJobCatList(List<UserJobCat> userJobCatList) {
		this.userJobCatList = userJobCatList;
	}

}
