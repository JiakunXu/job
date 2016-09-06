package com.jk.jobs.api.bookmark;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBookmarkService {

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	int getBookmarkCount(Long userId, Long jobId);

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult save(Long userId, String jobId);

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult cancel(Long userId, String jobId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Job> getJobList(Long userId);

}
