package com.jk.jobs.api.resume.bo;

import java.util.List;

import com.jk.jobs.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Resume extends SearchInfo {

	private static final long serialVersionUID = -4393812900452847128L;

	private Long resumeId;

	private Long userId;

	private String name;

	private String sex;

	private String tel;

	/**
	 * 工作年限.
	 */
	private String workYear;

	private String education;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<ResumeDetail> resumeDetailList;

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<ResumeDetail> getResumeDetailList() {
		return resumeDetailList;
	}

	public void setResumeDetailList(List<ResumeDetail> resumeDetailList) {
		this.resumeDetailList = resumeDetailList;
	}

}
