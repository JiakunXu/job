package com.jk.jobs.api.resume.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ResumeDetail implements Serializable {

	private static final long serialVersionUID = 315048421369644386L;

	private Long detailId;

	private Long resumeId;

	/**
	 * 模块.
	 */
	private Long jobCId;

	/**
	 * 项目周期（月）.
	 */
	private int cycle;

	private String content;

	private int rank;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private String jobCName;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

	public Long getJobCId() {
		return jobCId;
	}

	public void setJobCId(Long jobCId) {
		this.jobCId = jobCId;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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
