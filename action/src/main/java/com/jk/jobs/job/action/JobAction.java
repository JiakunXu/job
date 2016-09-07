package com.jk.jobs.job.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.bookmark.IBookmarkService;
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

	@Resource
	private IBookmarkService bookmarkService;

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
	 * 项目列表 所有人可见.
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
	 * 项目明细 所有人可见.
	 * 
	 * @return
	 */
	public String detail() {
		job = jobService.getJob(jobId);

		if (job != null) {
			Long userId = this.getUser().getUserId();
			// if 不是当前项目发布者
			if (job.getUserId() != userId) {
				// 判断是否已收藏
				int count = bookmarkService.getBookmarkCount(userId, job.getJobId());
				if (count > 0) {
					job.setCount(count);
				}
			}
		}

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

	/**
	 * 发布项目.
	 * 
	 * @return
	 */
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
	 * 修改我的项目.
	 * 
	 * @return
	 */
	public String edit() {
		job = jobService.getJob(this.getUser().getUserId(), jobId);

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	/**
	 * 保存修改我的项目.
	 * 
	 * @return
	 */
	public String update() {
		// TODO
		if (job != null && jobDetail != null) {
			List<JobDetail> list = new ArrayList<JobDetail>();
			list.add(jobDetail);
			job.setJobDetailList(list);
		}

		BooleanResult result = jobService.update(this.getUser().getUserId(), job);

		if (result.getResult()) {
			this.setResourceResult("修改成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
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

	/**
	 * 简历明细.
	 * 
	 * @return
	 */
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
	 * 项目完成.
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
	 * 项目撤销.
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

	/**
	 * 再次发布.
	 * 
	 * @return
	 */
	public String copy() {
		BooleanResult result = jobService.copy(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("发布成功");
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
