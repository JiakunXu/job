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
	int getJobCount(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	List<Job> getJobList(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	Job getJob(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	int createJob(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	int updateJob(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	int revokeJob(Job job);

}
