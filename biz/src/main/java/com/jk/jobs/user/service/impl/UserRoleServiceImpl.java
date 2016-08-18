package com.jk.jobs.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.api.user.IUserRoleService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.user.dao.IUserRoleDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserRoleServiceImpl.class);

	@Resource
	private IUserRoleDao userRoleDao;

	@Override
	public Role getRole(Long userId) {
		if (userId == null) {
			return null;
		}

		User user = new User();
		user.setUserId(userId);

		try {
			return userRoleDao.getRole(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

	@Override
	public BooleanResult setUserRole(Long userId, String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
