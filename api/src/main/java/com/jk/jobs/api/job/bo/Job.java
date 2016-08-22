package com.jk.jobs.api.job.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Job implements Serializable {

	private static final long serialVersionUID = -2010210383041214633L;

	private Long jobId;

	/**
	 * 发布者.
	 */
	private Long userId;

	private Long jobCId;

	private String title;

	/**
	 * 薪水.
	 */
	private BigDecimal salary;

	/**
	 * 工作地点.
	 */
	private String workAddress;

	/**
	 * 工作性质.
	 */
	private String jobNature;

	/**
	 * 工作年限.
	 */
	private String workYear;

	private String education;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<JobDetail> jobDetailList;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getJobCId() {
		return jobCId;
	}

	public void setJobCId(Long jobCId) {
		this.jobCId = jobCId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getJobNature() {
		return jobNature;
	}

	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
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

	public List<JobDetail> getJobDetailList() {
		return jobDetailList;
	}

	public void setJobDetailList(List<JobDetail> jobDetailList) {
		this.jobDetailList = jobDetailList;
	}

}
