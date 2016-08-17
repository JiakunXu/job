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

}
