package com.jk.jobs.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.job.bo.JobDetail;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.job.dao.IJobDao;
import com.jk.jobs.job.dao.IJobDetailDao;

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

	@Resource
	private IJobDetailDao jobDetailDao;

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

	@Override
	public Job getJob(String jobId) {
		if (StringUtils.isBlank(jobId)) {
			return null;
		}

		Job job = new Job();

		try {
			job.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(LogUtil.parserBean(job), e);

			return null;
		}

		try {
			job = jobDao.getJob(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);

			return null;
		}

		if (job == null) {
			return null;
		}

		JobDetail jobDetail = new JobDetail();
		jobDetail.setJobId(job.getJobId());

		List<JobDetail> jobDetailList = null;

		try {
			jobDetailList = jobDetailDao.getJobDetailList(jobDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		if (jobDetailList != null && jobDetailList.size() > 0) {
			job.setJobDetailList(jobDetailList);
		}

		return job;
	}

}
