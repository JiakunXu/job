package com.jk.jobs.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.job.dao.IJobDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class JobServiceImpl implements IJobService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(JobServiceImpl.class);

	@Resource
	private IJobDao jobDao;

	@Override
	public List<Job> getJobList(Job job) {
		if (job == null) {
			return null;
		}

		try {
			return jobDao.getJobList(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		return null;
	}

}
