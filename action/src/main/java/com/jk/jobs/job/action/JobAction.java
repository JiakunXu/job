package com.jk.jobs.job.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class JobAction extends BaseAction {

	private static final long serialVersionUID = -718228608790904376L;

	@Resource
	private IJobService jobService;

	private List<Job> jobList;

	public String list() {
		jobList = jobService.getJobList(new Job());

		return SUCCESS;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

}
