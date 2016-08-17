package com.jk.jobs.job.dao;

import java.util.List;

import com.jk.jobs.api.job.bo.JobDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobDetailDao {

	/**
	 * 
	 * @param jobDetail
	 * @return
	 */
	List<JobDetail> getJobDetailList(JobDetail jobDetail);

}
