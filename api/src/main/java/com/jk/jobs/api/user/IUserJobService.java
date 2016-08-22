package com.jk.jobs.api.user;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */

public interface IUserJobService {

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult resume(Long userId, String jobId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Job> getJobList(Long userId);

}
