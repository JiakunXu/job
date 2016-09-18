package com.jk.jobs.expert.action;

import java.util.List;

import javax.annotation.Resource;

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

	private List<Expert> expertList;

	private List<JobCat> jobCatList;

	private String jobCId;

	/**
	 * 
	 * @return
	 */
	public String list() {
		expertList = expertService.getExpertList();

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
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

	public String getJobCId() {
		return jobCId;
	}

	public void setJobCId(String jobCId) {
		this.jobCId = jobCId;
	}

}
