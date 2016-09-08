package com.jk.jobs.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
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

	@Resource
	private IJobService jobService;

	@Resource
	private IResumeService resumeService;

	private String jobId;

	private Job job;

	private Resume resume;

	private List<Job> jobList;

	/**
	 * 预览简历.
	 * 
	 * @return
	 */
	public String resume() {
		// 项目
		job = jobService.getJob(jobId);

		// 简历
		resume = resumeService.getResume(this.getUser().getUserId());

		return SUCCESS;
	}

	/**
	 * 投简历.
	 * 
	 * @return
	 */
	public String deliver() {
		BooleanResult result = userJobService.deliver(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("简历投递成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
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

	/**
	 * 撤销.
	 * 
	 * @return
	 */
	public String revoke() {
		BooleanResult result = userJobService.revoke(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("撤销成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 删除.
	 * 
	 * @return
	 */
	public String delete() {
		BooleanResult result = userJobService.delete(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("删除成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
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

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

}
