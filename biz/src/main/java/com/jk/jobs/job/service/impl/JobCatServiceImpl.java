package com.jk.jobs.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.job.dao.IJobCatDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class JobCatServiceImpl implements IJobCatService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(JobCatServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IJobCatDao jobCatDao;

	@Override
	public List<JobCat> getJobCatList(JobCat jobCat) {
		if (jobCat == null) {
			return null;
		}

		try {
			return jobCatDao.getJobCatList(jobCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(jobCat), e);
		}

		return null;
	}

	@Override
	public JobCat getJobCat(Long jobCId) {
		if (jobCId == null) {
			return null;
		}

		JobCat jobCat = new JobCat();
		jobCat.setJobCId(jobCId);

		try {
			return jobCatDao.getJobCat(jobCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(jobCat), e);
		}

		return null;
	}

}
