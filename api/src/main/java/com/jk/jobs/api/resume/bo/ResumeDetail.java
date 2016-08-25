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
	 * 项目周期（月）.
	 */
	private int cycle;

	private String content;

	private int rank;

	private String modifyUser;

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

}
