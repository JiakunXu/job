package com.jk.jobs.api.job.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class JobCat implements Serializable {

	private static final long serialVersionUID = 174562407324492977L;

	private Long jobCId;

	private String jobCName;

	public Long getJobCId() {
		return jobCId;
	}

	public void setJobCId(Long jobCId) {
		this.jobCId = jobCId;
	}

	public String getJobCName() {
		return jobCName;
	}

	public void setJobCName(String jobCName) {
		this.jobCName = jobCName;
	}

}
