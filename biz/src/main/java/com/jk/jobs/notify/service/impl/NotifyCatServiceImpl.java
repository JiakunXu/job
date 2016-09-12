package com.jk.jobs.notify.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.notify.INotifyCatService;
import com.jk.jobs.api.notify.bo.NotifyCat;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.notify.dao.INotifyCatDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class NotifyCatServiceImpl implements INotifyCatService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(NotifyCatServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private INotifyCatDao notifyCatDao;

	@Override
	public NotifyCat getNotifyCat(Long notifyCId) {
		if (notifyCId == null) {
			return null;
		}

		Long key = notifyCId;

		NotifyCat notifyCat = null;

		try {
			notifyCat = (NotifyCat) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_NOTIFY_CAT_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_NOTIFY_CAT_ID + key, e);
		}

		if (notifyCat != null) {
			return notifyCat;
		}

		notifyCat = new NotifyCat();
		notifyCat.setNotifyCId(notifyCId);

		try {
			notifyCat = notifyCatDao.getNotifyCat(notifyCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(notifyCat), e);

			return null;
		}

		if (notifyCat == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_NOTIFY_CAT_ID + key, notifyCat);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_NOTIFY_CAT_ID + key, e);
		}

		return notifyCat;
	}

}
