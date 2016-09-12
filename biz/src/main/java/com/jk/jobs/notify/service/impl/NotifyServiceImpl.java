package com.jk.jobs.notify.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.notify.INotifyCatService;
import com.jk.jobs.api.notify.INotifyService;
import com.jk.jobs.api.notify.bo.Notify;
import com.jk.jobs.api.notify.bo.NotifyCat;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.notify.dao.INotifyDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class NotifyServiceImpl implements INotifyService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(NotifyServiceImpl.class);

	@Resource
	private INotifyCatService notifyCatService;

	@Resource
	private INotifyDao notifyDao;

	@Override
	public List<Notify> getNotifyList(Long userId) {
		if (userId == null) {
			return null;
		}

		Notify notify = new Notify();
		notify.setUserId(userId);

		List<Notify> notifyList = null;

		try {
			notifyList = notifyDao.getNotifyList(notify);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(notify), e);
		}

		if (notifyList == null || notifyList.size() == 0) {
			return null;
		}

		for (Notify no : notifyList) {
			NotifyCat notifyCat = notifyCatService.getNotifyCat(no.getNotifyCId());
			no.setNotifyCName(notifyCat != null ? notifyCat.getNotifyCName() : "通知");
		}

		return notifyList;
	}

}
