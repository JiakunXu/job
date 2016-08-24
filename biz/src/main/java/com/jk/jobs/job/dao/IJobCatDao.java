package com.jk.jobs.job.dao;

import java.util.List;

import com.jk.jobs.api.job.bo.JobCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobCatDao {

	/**
	 * 
	 * @param jobCat
	 * @return
	 */
	List<JobCat> getJobCatList(JobCat jobCat);

	/**
	 * 
	 * @param jobCat
	 * @return
	 */
	JobCat getJobCat(JobCat jobCat);

}
