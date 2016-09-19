package com.jk.jobs.expert.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ExpertAction extends BaseAction {

	private static final long serialVersionUID = 5618028048849823745L;

	@Resource
	private IExpertService expertService;

	@Resource
	private IJobCatService jobCatService;

	private String jobCId;

	private List<Expert> expertList;

	private List<JobCat> jobCatList;

	/**
	 * 专家.
	 */
	private String expertId;

	private Expert expert;

	/**
	 * 
	 * @return
	 */
	public String list() {
		expertList = StringUtils.isBlank(jobCId) ? expertService.getExpertList() : expertService.getExpertList(jobCId);

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		expert = expertService.getExpert(expertId);

		return SUCCESS;
	}

	public String getJobCId() {
		return jobCId;
	}

	public void setJobCId(String jobCId) {
		this.jobCId = jobCId;
	}

	public List<Expert> getExpertList() {
		return expertList;
	}

	public void setExpertList(List<Expert> expertList) {
		this.expertList = expertList;
	}

	public List<JobCat> getJobCatList() {
		return jobCatList;
	}

	public void setJobCatList(List<JobCat> jobCatList) {
		this.jobCatList = jobCatList;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

}
