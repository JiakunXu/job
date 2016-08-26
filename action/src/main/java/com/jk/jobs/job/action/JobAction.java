package com.jk.jobs.job.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.api.job.bo.JobDetail;
import com.jk.jobs.api.user.bo.UserJob;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

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

	@Resource
	private IJobCatService jobCatService;

	private List<Job> jobList;

	private String jobId;

	private Job job;

	private List<JobCat> jobCatList;

	private JobDetail jobDetail;

	private List<UserJob> userJobList;

	/**
	 * UserJob.
	 */
	private String userJobId;

	private UserJob resume;

	/**
	 * 
	 * @return
	 */
	public String list() {
		Job j = new Job();
		j.setLimit(100);
		j.setOffset(0);

		j.setType(IJobService.PUBLISH);

		jobList = jobService.getJobList(j);

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
		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	public String publish() {
		// TODO
		if (job != null && jobDetail != null) {
			List<JobDetail> list = new ArrayList<JobDetail>();
			list.add(jobDetail);
			job.setJobDetailList(list);
		}

		BooleanResult result = jobService.publish(this.getUser().getUserId(), job);

		if (result.getResult()) {
			this.setResourceResult("发布成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 我发布的项目.
	 * 
	 * @return
	 */
	public String my() {
		jobList = jobService.getJobList(this.getUser().getUserId());

		return SUCCESS;
	}

	/**
	 * 项目投的简历.
	 * 
	 * @return
	 */
	public String resume() {
		userJobList = jobService.getUserList(this.getUser().getUserId(), jobId);

		return SUCCESS;
	}

	public String resumeDetail() {
		resume = jobService.detail(this.getUser().getUserId(), jobId, userJobId);

		return SUCCESS;
	}

	/**
	 * 忽略简历.
	 * 
	 * @return
	 */
	public String ignore() {
		BooleanResult result = jobService.ignore(this.getUser().getUserId(), jobId, userJobId);

		if (result.getResult()) {
			this.setResourceResult("忽略成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 完成.
	 * 
	 * @return
	 */
	public String finish() {
		BooleanResult result = jobService.finish(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("结束成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 撤销.
	 * 
	 * @return
	 */
	public String revoke() {
		BooleanResult result = jobService.revoke(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("撤销成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String delete() {
		BooleanResult result = jobService.delete(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("删除成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

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

	public List<JobCat> getJobCatList() {
		return jobCatList;
	}

	public void setJobCatList(List<JobCat> jobCatList) {
		this.jobCatList = jobCatList;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public List<UserJob> getUserJobList() {
		return userJobList;
	}

	public void setUserJobList(List<UserJob> userJobList) {
		this.userJobList = userJobList;
	}

	public String getUserJobId() {
		return userJobId;
	}

	public void setUserJobId(String userJobId) {
		this.userJobId = userJobId;
	}

	public UserJob getResume() {
		return resume;
	}

	public void setResume(UserJob resume) {
		this.resume = resume;
	}

}
