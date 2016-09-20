package com.jk.jobs.issue.dao;

import java.util.List;

import com.jk.jobs.api.issue.bo.Issue;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IIssueDao {

	/**
	 * 
	 * @param issue
	 * @return
	 */
	int createIssue(Issue issue);

	/**
	 * 
	 * @param issue
	 * @return
	 */
	int getIssueCount(Issue issue);

	/**
	 * 
	 * @param issue
	 * @return
	 */
	List<Issue> getIssueList(Issue issue);

	/**
	 * 
	 * @param issue
	 * @return
	 */
	Issue getIssue(Issue issue);

	/**
	 * 
	 * @param issue
	 * @return
	 */
	int updateIssue(Issue issue);

}
