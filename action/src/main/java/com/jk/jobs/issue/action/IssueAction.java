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

	/**
	 * 操作.
	 */
	private String op;

	private String issueId;

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

	public String revoke() {
		BooleanResult result = issueService.revoke(this.getUser().getUserId(), issueId);

		if (result.getResult()) {
			this.setResourceResult("撤销成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String delete() {
		BooleanResult result = issueService.delete(this.getUser().getUserId(), issueId);

		if (result.getResult()) {
			this.setResourceResult("删除成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	// >>>>>>>>>>以下是专家相关问题<<<<<<<<<<

	public String ignore() {
		BooleanResult result = issueService.ignore(this.getUser().getUserId(), issueId);

		if (result.getResult()) {
			this.setResourceResult("忽略成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
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

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public List<Issue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

}
