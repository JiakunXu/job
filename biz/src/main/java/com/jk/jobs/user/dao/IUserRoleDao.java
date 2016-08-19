package com.jk.jobs.user.dao;

import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.api.user.bo.UserRole;

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

	/**
	 * 
	 * @param userRole
	 * @return
	 */
	int createUserRole(UserRole userRole);

}
