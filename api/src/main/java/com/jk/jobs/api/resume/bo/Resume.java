package com.jk.jobs.api.resume.bo;

import com.jk.jobs.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Resume extends SearchInfo {

	private static final long serialVersionUID = -4393812900452847128L;

	private Long resumeId;

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

}
