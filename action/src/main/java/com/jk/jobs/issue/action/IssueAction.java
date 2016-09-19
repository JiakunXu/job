package com.jk.jobs.issue.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.issue.IIssueService;
import com.jk.jobs.api.issue.bo.Issue;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class IssueAction extends BaseAction {

	private static final long serialVersionUID = 217570833887127754L;

	@Resource
	private IIssueService issueService;

	private Issue issue;

	/**
	 * 咨询 被咨询.
	 */
	private String type;

	private List<Issue> issueList;

	/**
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Long userId = this.getUser().getUserId();

		sb.append(issueService.getIssueCount(userId, "U")).append("&");
		sb.append(issueService.getIssueCount(userId, "E"));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String submit() {
		BooleanResult result = issueService.submit(this.getUser().getUserId(), issue);

		if (result.getResult()) {
			this.setResourceResult("提交成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String list() {
		issueList = issueService.getIssueList(this.getUser().getUserId(), type);

		return SUCCESS;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

}
