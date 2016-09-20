package com.jk.jobs.api.issue;

import java.util.List;

import com.jk.jobs.api.issue.bo.Issue;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IIssueService {

	/**
	 * 投递.
	 */
	String SUBMIT = "submit";

	/**
	 * 撤销.
	 */
	String REVOKE = "revoke";

	/**
	 * 删除.
	 */
	String DELETE = "delete";

	/**
	 * 忽略.
	 */
	String IGNORE = "ignore";

	/**
	 * 
	 * @param userId
	 * @param issue
	 * @return
	 */
	BooleanResult submit(Long userId, Issue issue);

	/**
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	int getIssueCount(Long userId, String type);

	/**
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	List<Issue> getIssueList(Long userId, String type);

	/**
	 * 
	 * @param userId
	 * @param issueId
	 * @return
	 */
	Issue getIssue(Long userId, String issueId);

	/**
	 * 
	 * @param userId
	 * @param issueId
	 * @return
	 */
	BooleanResult revoke(Long userId, String issueId);

	/**
	 * 
	 * @param userId
	 * @param issueId
	 * @return
	 */
	BooleanResult delete(Long userId, String issueId);

	// >>>>>>>>>>以下是专家相关问题<<<<<<<<<<

	/**
	 * 
	 * @param userId
	 *            专家.
	 * @param issueId
	 * @return
	 */
	BooleanResult ignore(Long userId, String issueId);

}
