package com.jk.jobs.resume.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ResumeAction extends BaseAction {

	private static final long serialVersionUID = -1350198812498987637L;

	@Resource
	private IResumeService resumeService;

	private List<Resume> resumeList;

	private String resumeId;

	private Resume resume;

	/**
	 * 
	 * @return
	 */
	public String list() {
		resumeList = resumeService.getResumeList(new Resume());

		return SUCCESS;
	}

	public String detail() {
		resume = resumeService.getResume(resumeId);

		return SUCCESS;
	}

	public List<Resume> getResumeList() {
		return resumeList;
	}

	public void setResumeList(List<Resume> resumeList) {
		this.resumeList = resumeList;
	}

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}
}
