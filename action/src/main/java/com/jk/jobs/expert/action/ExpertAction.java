package com.jk.jobs.expert.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.expert.bo.ExpertJobCat;
import com.jk.jobs.api.issue.IIssueService;
import com.jk.jobs.api.issue.bo.Issue;
import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ExpertAction extends BaseAction {

	private static final long serialVersionUID = 5618028048849823745L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(ExpertAction.class);

	@Resource
	private IExpertService expertService;

	@Resource
	private IJobCatService jobCatService;

	@Resource
	private IIssueService issueService;

	private String jobCId;

	private List<Expert> expertList;

	private List<JobCat> jobCatList;

	/**
	 * 专家.
	 */
	private String expertId;

	/**
	 * 参考问题.
	 */
	private String issueId;

	private Expert expert;

	private Issue issue;

	/**
	 * 
	 * @return
	 */
	public String index() {
		expert = expertService.getExpert(this.getUser().getUserId());

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	/**
	 * 申请专家.
	 * 
	 * @return
	 */
	public String saveOrUpdate() {
		// TODO
		if (expert != null) {
			String[] jobCIds = StringUtils.isNotBlank(jobCId) ? jobCId.split(",") : null;

			if (jobCIds != null && jobCIds.length > 0) {
				List<ExpertJobCat> list = new ArrayList<ExpertJobCat>();

				for (int i = 0; i < jobCIds.length; i++) {
					String v0 = jobCIds[i];
					if (StringUtils.isNotBlank(v0)) {
						ExpertJobCat expertJobCat = new ExpertJobCat();

						try {
							expertJobCat.setJobCId(Long.valueOf(v0.trim()));
							list.add(expertJobCat);
						} catch (NumberFormatException e) {
							logger.error(e);
						}
					}
				}

				expert.setExpertJobCatList(list);
			}
		}

		BooleanResult result = expertService.saveOrUpdate(this.getUser().getUserId(), expert);

		if (result.getResult()) {
			this.setResourceResult("操作成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 专家列表.
	 * 
	 * @return
	 */
	public String list() {
		expertList = StringUtils.isBlank(jobCId) ? expertService.getExpertList() : expertService.getExpertList(jobCId);

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	/**
	 * 专家 + 咨询.
	 * 
	 * @return
	 */
	public String detail() {
		expert = expertService.getExpert(expertId);

		if (StringUtils.isNotBlank(issueId)) {
			issue = issueService.getIssue(this.getUser().getUserId(), issueId);
		}

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

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

}
