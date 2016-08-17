package com.jk.jobs.api.user;

import com.jk.jobs.api.role.bo.Role;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserRoleService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Role getRole(Long userId);

}
