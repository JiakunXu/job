package com.jk.jobs.api.user;

import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.framework.bo.BooleanResult;

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

	/**
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	BooleanResult setUserRole(Long userId, String roleId);

}
