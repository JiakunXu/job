package com.jk.jobs.monitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.monitor.IMethodMonitorService;
import com.jk.jobs.api.monitor.bo.MethodMonitor;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.monitor.dao.IMethodMonitorDao;

/**
 * 
 * @author xujiakun
 * 
 */
@Service
public class MethodMonitorServiceImpl implements IMethodMonitorService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(MethodMonitorServiceImpl.class);

	@Resource
	private IMethodMonitorDao methodMonitorDao;

	@Override
	public int getMethodMonitorCount(MethodMonitor methodMonitor) {
		if (methodMonitor == null) {
			return 0;
		}

		try {
			return methodMonitorDao.getMethodMonitorCount(methodMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);
		}

		return 0;
	}

	@Override
	public List<MethodMonitor> getMethodMonitorList(MethodMonitor methodMonitor) {
		if (methodMonitor == null) {
			return null;
		}

		try {
			return methodMonitorDao.getMethodMonitorList(methodMonitor);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);
		}

		return null;
	}

	@Override
	public BooleanResult createMethodMonitor(MethodMonitor methodMonitor) {
		BooleanResult res = new BooleanResult();
		res.setResult(false);

		if (methodMonitor == null) {
			res.setCode("method 信息不能为空");
			return res;
		}

		try {
			methodMonitorDao.createMethodMonitor(methodMonitor);
			res.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(methodMonitor), e);

			res.setCode(IMethodMonitorService.ERROR_MESSAGE);
		}

		return res;
	}

}
