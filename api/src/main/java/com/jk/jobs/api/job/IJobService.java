package com.jk.jobs.api.job;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobService {

	/**
	 * 
	 * @param job
	 * @return
	 */
	List<Job> getJobList(Job job);

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	Job getJob(String jobId);

	/**
	 * 
	 * @param userId
	 * @param job
	 * @return
	 */
	BooleanResult publishJob(Long userId, Job job);

}
