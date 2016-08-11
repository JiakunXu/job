package com.jk.jobs.user.dao;

import com.jk.jobs.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserWeixinDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	User getUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	int createUser(User user);

}
