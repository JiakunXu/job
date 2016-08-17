package com.jk.jobs.user.dao;

import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserRoleDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	Role getRole(User user);

}
