package com.jk.jobs.job.dao;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobDao {

	/**
	 * 
	 * @param job
	 * @return
	 */
	List<Job> getJobList(Job job);

}
