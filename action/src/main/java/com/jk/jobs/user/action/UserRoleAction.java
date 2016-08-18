package com.jk.jobs.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.role.IRoleService;
import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.api.user.IUserRoleService;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;

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
	private IUserRoleService userRoleService;

	@Resource
	private IRoleService roleService;

	private List<Role> roleList;

	private String roleId;

	/**
	 * 
	 * @return
	 */
	public String index() {
		roleList = roleService.getRoleList();

		return SUCCESS;
	}

	public String set() {
		BooleanResult result = userRoleService.setUserRole(this.getUser().getUserId(), roleId);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
