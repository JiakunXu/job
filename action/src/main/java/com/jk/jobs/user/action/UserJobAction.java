package com.jk.jobs.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.user.IUserJobService;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class UserJobAction extends BaseAction {

	private static final long serialVersionUID = 2578807238664967114L;

	@Resource
	private IUserJobService userJobService;

	private String jobId;

	private List<Job> jobList;

	/**
	 * 
	 * @return
	 */
	public String resume() {
		BooleanResult result = userJobService.resume(this.getUser().getUserId(), jobId);

		return SUCCESS;
	}

	/**
	 * 已投简历.
	 * 
	 * @return
	 */
	public String list() {
		jobList = userJobService.getJobList(this.getUser().getUserId());

		return SUCCESS;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

}
