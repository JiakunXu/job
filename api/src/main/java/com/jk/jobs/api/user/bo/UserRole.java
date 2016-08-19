package com.jk.jobs.api.user.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8020967187724716472L;

	private Long id;

	private Long userId;

	private Long roleId;

	private String modifyUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
