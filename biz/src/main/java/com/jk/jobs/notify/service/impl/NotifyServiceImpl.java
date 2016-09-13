package com.jk.jobs.notify.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.notify.INotifyCatService;
import com.jk.jobs.api.notify.INotifyService;
import com.jk.jobs.api.notify.bo.Notify;
import com.jk.jobs.api.notify.bo.NotifyCat;
import com.jk.jobs.framework.bo.BooleanResult;
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

	@Override
	public BooleanResult notify(Long userId, String content, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(content)) {
			result.setCode("消息信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		Notify notify = new Notify();
		notify.setUserId(userId);
		notify.setNotifyCId(2L);
		notify.setContent(content.trim());
		notify.setModifyUser(modifyUser);

		try {
			notifyDao.createNotify(notify);
			result.setCode(notify.getNotifyId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(notify), e);

			result.setCode("消息信息创建失败，请稍后再试");
		}

		return result;
	}

}
