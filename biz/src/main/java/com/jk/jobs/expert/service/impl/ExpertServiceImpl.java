package com.jk.jobs.expert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.expert.dao.IExpertDao;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ExpertServiceImpl implements IExpertService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ExpertServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserService userService;

	@Resource
	private IExpertDao expertDao;

	@Override
	public List<Expert> getExpertList() {
		return getExpertList("");
	}

	@Override
	public List<Expert> getExpertList(String jobCId) {
		List<Expert> expertList = getExpertList(new Expert());

		if (expertList == null || expertList.size() == 0) {
			return null;
		}

		for (Expert expert : expertList) {
			User user = userService.getUser(expert.getUserId());
			if (user == null) {
				continue;
			}

			expert.setUserName(user.getUserName());
		}

		return expertList;
	}

	@Override
	public Expert getExpert(String expertId) {
		if (StringUtils.isBlank(expertId)) {
			return null;
		}

		String key = expertId.trim();

		Expert expert = null;

		try {
			expert = (Expert) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, e);
		}

		if (expert != null) {
			return expert;
		}

		expert = new Expert();

		try {
			expert.setExpertId(Long.valueOf(expertId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		expert = getExpert(expert);
		if (expert == null) {
			return null;
		}

		User user = userService.getUser(expert.getUserId());
		if (user != null) {
			expert.setUserName(user.getUserName());
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, expert);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, e);
		}

		return expert;
	}

	@Override
	public Expert getExpert(Long userId) {
		if (userId == null) {
			return null;
		}

		Long key = userId;

		Expert expert = null;

		try {
			expert = (Expert) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, e);
		}

		if (expert != null) {
			return expert;
		}

		expert = new Expert();
		expert.setUserId(userId);

		expert = getExpert(expert);
		if (expert == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, expert);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, e);
		}

		return expert;
	}

	private List<Expert> getExpertList(Expert expert) {
		try {
			return expertDao.getExpertList(expert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expert), e);
		}

		return null;
	}

	private Expert getExpert(Expert expert) {
		try {
			return expertDao.getExpert(expert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expert), e);
		}

		return null;
	}

}
