package com.jk.jobs.api.user;

import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserService {

	/**
	 * 
	 * @param user
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createUser(User user, String modifyUser);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	User getUser(Long userId);

}
