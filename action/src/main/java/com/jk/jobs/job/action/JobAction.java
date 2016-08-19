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

	private String jobId;

	private Job job;

	/**
	 * 
	 * @return
	 */
	public String list() {
		jobList = jobService.getJobList(new Job());

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		job = jobService.getJob(jobId);

		return SUCCESS;
	}

	/**
	 * 需求模版.
	 * 
	 * @return
	 */
	public String template() {

		return SUCCESS;
	}

	public String publish() {

		return RESOURCE_RESULT;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}
