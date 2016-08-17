package com.jk.jobs.api.role.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Role implements Serializable {

	private static final long serialVersionUID = -6590685118831337082L;

	private Long roleId;

	private String roleName;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
