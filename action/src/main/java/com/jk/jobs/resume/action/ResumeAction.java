package com.jk.jobs.resume.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.api.resume.bo.ResumeDetail;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

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

	private ResumeDetail resumeDetail;

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

	public String my() {
		resume = resumeService.getResume(this.getUser().getUserId());

		return SUCCESS;
	}

	/**
	 * 修改.
	 * 
	 * @return
	 */
	public String edit() {
		resume = resumeService.getResume(this.getUser().getUserId());

		return SUCCESS;
	}

	public String saveOrUpdate() {
		// TODO
		if (resume != null && resumeDetail != null) {
			List<ResumeDetail> list = new ArrayList<ResumeDetail>();
			list.add(resumeDetail);
			resume.setResumeDetailList(list);
		}

		BooleanResult result = resumeService.saveOrUpdate(this.getUser().getUserId(), resume);

		if (result.getResult()) {
			this.setResourceResult("发布成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
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

	public ResumeDetail getResumeDetail() {
		return resumeDetail;
	}

	public void setResumeDetail(ResumeDetail resumeDetail) {
		this.resumeDetail = resumeDetail;
	}

}
