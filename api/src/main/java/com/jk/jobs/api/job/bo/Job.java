package com.jk.jobs.api.job.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Job implements Serializable {

	private static final long serialVersionUID = -2010210383041214633L;

	private Long jobId;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

}
