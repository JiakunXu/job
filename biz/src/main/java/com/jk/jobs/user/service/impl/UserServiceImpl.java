package com.jk.jobs.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.user.dao.IUserDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserServiceImpl implements IUserService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserDao userDao;

	@Override
	public BooleanResult createUser(User user, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (user == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		user.setModifyUser(modifyUser);

		try {
			userDao.createUser(user);
			result.setCode(user.getUserId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);

			result.setCode("用户信息创建失败，请稍后再试");
		}

		return result;
	}

	@Override
	public User getUser(Long userId) {
		if (userId == null) {
			return null;
		}

		Long key = userId;

		User user = null;

		try {
			user = (User) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_USER_ID + key, e);
		}

		if (user != null) {
			return user;
		}

		user = new User();
		user.setUserId(userId);

		user = getUser(user);

		if (user == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_USER_ID + key, user);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_USER_ID + key, e);
		}

		return user;
	}

	@Override
	public BooleanResult setUserName(Long userId, String userName) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		User user = new User();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		user.setUserId(userId);
		user.setModifyUser(userId.toString());

		if (StringUtils.isBlank(userName)) {
			result.setCode("名字信息不能为空");
			return result;
		}

		user.setUserName(userName.trim());

		try {
			int c = userDao.updateUser(user);
			if (c == 1) {
				result.setResult(true);

				// remove cache
				remove(userId);
			} else {
				result.setCode("修改用户信息失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);

			result.setCode("修改用户信息失败");
		}

		return result;
	}

	private User getUser(User user) {
		try {
			return userDao.getUser(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

	/**
	 * remove cache.
	 * 
	 * @param key
	 */
	private void remove(Long key) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

}
