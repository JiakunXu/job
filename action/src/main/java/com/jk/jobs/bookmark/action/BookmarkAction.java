package com.jk.jobs.bookmark.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.bookmark.IBookmarkService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class BookmarkAction extends BaseAction {

	private static final long serialVersionUID = 6291108808341605810L;

	@Resource
	private IBookmarkService bookmarkService;

	private String jobId;

	private List<Job> jobList;

	public String stats() {
		this.setResourceResult(String.valueOf(bookmarkService.getBookmarkCount(this.getUser().getUserId(), null)));

		return RESOURCE_RESULT;
	}

	public String save() {
		BooleanResult result = bookmarkService.save(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("收藏成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String cancel() {
		BooleanResult result = bookmarkService.cancel(this.getUser().getUserId(), jobId);

		if (result.getResult()) {
			this.setResourceResult("取消成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String list() {
		jobList = bookmarkService.getJobList(this.getUser().getUserId());

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
