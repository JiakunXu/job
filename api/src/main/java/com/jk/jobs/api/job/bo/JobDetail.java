package com.jk.jobs.api.job.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class JobDetail implements Serializable {

	private static final long serialVersionUID = 6108585810842306206L;

	private Long detailId;

	private Long jobId;

	private String content;

	private int rank;

	private String modifyUser;

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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
