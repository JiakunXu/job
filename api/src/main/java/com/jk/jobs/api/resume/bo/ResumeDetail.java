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

	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
