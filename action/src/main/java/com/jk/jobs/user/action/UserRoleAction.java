package com.jk.jobs.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.role.IRoleService;
import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class UserRoleAction extends BaseAction {

	private static final long serialVersionUID = -2093998178883324339L;

	@Resource
	private IRoleService roleService;

	private List<Role> roleList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		roleList = roleService.getRoleList();

		return SUCCESS;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
