package com.jk.jobs.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.monitor.ILogMonitorService;
import com.jk.jobs.api.monitor.bo.LogMonitor;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.monitor.dao.ILogMonitorDao;

/**
 * 
 * @author xujiakun
 * 
 */
@Service
public class LogMonitorServiceImpl implements ILogMonitorService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(LogMonitorServiceImpl.class);

	@Resource
	private ILogMonitorDao logMonitorDao;

	@Override
	public int getLogMonitorCount(LogMonitor logMonitor) {
		if (logMonitor == null) {
			return 0;
		}

		try {
			return logMonitorDao.getLogMonitorCount(logMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(logMonitor), e);
		}

		return 0;
	}

	@Override
	public List<LogMonitor> getLogMonitorList(LogMonitor logMonitor) {
		if (logMonitor == null) {
			return null;
		}

		try {
			return logMonitorDao.getLogMonitorList(logMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(logMonitor), e);
		}

		return null;
	}

	@Override
	public boolean createLogMonitor(List<LogMonitor> logMonitorList) {
		if (logMonitorList == null) {
			return false;
		}

		try {
			logMonitorDao.createLogMonitor(logMonitorList);
			return true;
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(logMonitorList), e);
		}

		return false;
	}

	@Override
	public List<LogMonitor> getLogMonitorList4SendEmail() {
		try {
			return logMonitorDao.getLogMonitorList4SendEmail();
		} catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

}
