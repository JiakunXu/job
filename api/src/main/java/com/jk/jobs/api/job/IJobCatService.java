package com.jk.jobs.api.job;

import java.util.List;

import com.jk.jobs.api.job.bo.JobCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobCatService {

	/**
	 * 
	 * @param jobCat
	 * @return
	 */
	List<JobCat> getJobCatList(JobCat jobCat);

	/**
	 * 
	 * @param jobCId
	 * @return
	 */
	JobCat getJobCat(Long jobCId);

}
