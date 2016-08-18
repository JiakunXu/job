package com.jk.jobs.role.dao;

import java.util.List;

import com.jk.jobs.api.role.bo.Role;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IRoleDao {

	/**
	 * 
	 * @param role
	 * @return
	 */
	List<Role> getRoleList(Role role);

}
