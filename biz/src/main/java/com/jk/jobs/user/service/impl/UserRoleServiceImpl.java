package com.jk.jobs.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.role.bo.Role;
import com.jk.jobs.api.user.IUserRoleService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.api.user.bo.UserRole;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
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
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserRoleDao userRoleDao;

	@Override
	public Role getRole(Long userId) {
		if (userId == null) {
			return null;
		}

		Long key = userId;

		Role role = null;

		try {
			role = (Role) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_ROLE_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_ROLE_USER_ID + key, e);
		}

		if (role != null) {
			return role;
		}

		User user = new User();
		user.setUserId(userId);

		role = getRole(user);

		if (role == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_ROLE_USER_ID + key, role);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_ROLE_USER_ID + key, e);
		}

		return role;
	}

	@Override
	public BooleanResult setUserRole(Long userId, String roleId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(roleId)) {
			result.setCode("角色信息不能为空");
			return result;
		}

		UserRole userRole = new UserRole();

		userRole.setUserId(userId);

		try {
			userRole.setRoleId(Long.valueOf(roleId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("角色信息不能为空");
			return result;
		}

		userRole.setModifyUser(userId.toString());

		try {
			userRoleDao.createUserRole(userRole);
			result.setCode(userRole.getId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userRole), e);

			result.setCode("用户角色信息创建失败，请稍后再试");
		}

		return result;
	}

	private Role getRole(User user) {
		try {
			return userRoleDao.getRole(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

}
