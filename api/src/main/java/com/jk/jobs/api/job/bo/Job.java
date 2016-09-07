package com.jk.jobs.api.job.bo;

import java.math.BigDecimal;
import java.util.List;

import com.jk.jobs.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Job extends SearchInfo {

	private static final long serialVersionUID = -2010210383041214633L;

	private Long jobId;

	/**
	 * 发布者.
	 */
	private Long userId;

	private Long jobCId;

	private String title;

	/**
	 * 项目周期（月）.
	 */
	private int cycle;

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

	private String remark;

	/**
	 * 项目审核撤销状态.
	 */
	private String type;

	/**
	 * 发布时间.
	 */
	private String createDate;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<JobDetail> jobDetailList;

	private String userName;

	private String jobCName;

	private int star;

	private int count;

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

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<JobDetail> getJobDetailList() {
		return jobDetailList;
	}

	public void setJobDetailList(List<JobDetail> jobDetailList) {
		this.jobDetailList = jobDetailList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobCName() {
		return jobCName;
	}

	public void setJobCName(String jobCName) {
		this.jobCName = jobCName;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
