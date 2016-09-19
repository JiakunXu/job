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

}
