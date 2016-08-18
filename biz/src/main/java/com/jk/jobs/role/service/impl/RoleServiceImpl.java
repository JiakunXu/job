package com.jk.jobs.role.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.role.IRoleService;
import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.role.dao.IRoleDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class RoleServiceImpl implements IRoleService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(RoleServiceImpl.class);

	@Resource
	private IRoleDao roleDao;

	@Override
	public List<Role> getRoleList() {
		try {
			return roleDao.getRoleList(new Role());
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

}
