package com.jk.jobs.user.dao;

import com.jk.jobs.api.user.bo.UserJobDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserJobDetailDao {

	/**
	 * 
	 * @param userJobDetail
	 * @return
	 */
	int createUserJobDetail(UserJobDetail userJobDetail);

}
