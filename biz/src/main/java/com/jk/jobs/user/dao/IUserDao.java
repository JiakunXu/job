package com.jk.jobs.user.dao;

import com.jk.jobs.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	int createUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	User getUser(User user);

}
