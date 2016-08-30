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

	private String birthday;

	private String tel;

	/**
	 * 工作年限.
	 */
	private String workYear;

	private String education;

	private String remark;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<ResumeDetail> resumeDetailList;

	private List<ResumeJobCat> resumeJobCatList;

	/**
	 * UserJob.
	 */
	private Long userJobId;

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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<ResumeJobCat> getResumeJobCatList() {
		return resumeJobCatList;
	}

	public void setResumeJobCatList(List<ResumeJobCat> resumeJobCatList) {
		this.resumeJobCatList = resumeJobCatList;
	}

	public Long getUserJobId() {
		return userJobId;
	}

	public void setUserJobId(Long userJobId) {
		this.userJobId = userJobId;
	}

}
