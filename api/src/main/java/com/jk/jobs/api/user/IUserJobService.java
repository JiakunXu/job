package com.jk.jobs.api.user;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.user.bo.UserJob;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */

public interface IUserJobService {

	/**
	 * 投递.
	 */
	String DELIVER = "deliver";

	/**
	 * 忽略.
	 */
	String IGNORE = "ignore";

	/**
	 * 撤销.
	 */
	String REVOKE = "revoke";

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult deliver(Long userId, String jobId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Job> getJobList(Long userId);

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	List<UserJob> getUserList(String jobId);

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult revoke(Long userId, String jobId);

	// >>>>>>>>>>以下是项目相关简历<<<<<<<<<<

	/**
	 * 
	 * @param jobId
	 * @param userJobId
	 * @return
	 */
	UserJob detail(Long jobId, String userJobId);

	/**
	 * 
	 * @param jobId
	 * @param userJobId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult ignore(Long jobId, String userJobId, String modifyUser);

}
