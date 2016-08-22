package com.jk.jobs.user.dao;

import java.util.List;

import com.jk.jobs.api.user.bo.UserJob;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserJobDao {

	/**
	 * 
	 * @param userJob
	 * @return
	 */
	int createUserJob(UserJob userJob);

	/**
	 * 
	 * @param userJob
	 * @return
	 */
	List<UserJob> getUserJobList(UserJob userJob);

}
