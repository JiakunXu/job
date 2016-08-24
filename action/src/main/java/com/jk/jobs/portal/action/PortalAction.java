package com.jk.jobs.portal.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.weixin.IWeixinService;
import com.jk.jobs.api.weixin.bo.Ticket;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2191525146456353749L;

	@Resource
	private IJobService jobService;

	@Resource
	private IWeixinService weixinService;

	private List<Job> jobList;

	private Ticket ticket;

	/**
	 * 移动商场.
	 * 
	 * @return
	 */
	public String homepage() {
		Job j = new Job();
		j.setLimit(3);
		j.setOffset(0);

		j.setType(IJobService.PUBLISH);

		jobList = jobService.getJobList(j);

		String requestURL = env.getProperty("appUrl") + "/homepage.htm";
		HttpServletRequest request = getServletRequest();
		String queryString = request.getQueryString();

		ticket =
			weixinService.getTicket(StringUtils.isEmpty(queryString) ? requestURL : requestURL + "?" + queryString);

		return SUCCESS;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
