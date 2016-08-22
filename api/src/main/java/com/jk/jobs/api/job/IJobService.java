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
	 * 我发布的项目.
	 * 
	 * @param userId
	 * @return
	 */
	List<Job> getJobList(Long userId);

	/**
	 * 我投递的项目.
	 * 
	 * @param jobId
	 * @return
	 */
	List<Job> getJobList(String[] jobId);

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
	BooleanResult publish(Long userId, Job job);

}
