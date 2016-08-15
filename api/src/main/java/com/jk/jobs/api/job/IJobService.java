package com.jk.jobs.api.job;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;

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

}
