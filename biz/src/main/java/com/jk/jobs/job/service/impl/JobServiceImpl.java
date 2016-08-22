package com.jk.jobs.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.job.bo.JobDetail;
import com.jk.jobs.framework.bo.BooleanResult;
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
	public List<Job> getJobList(Long userId) {
		if (userId == null) {
			return null;
		}

		Job job = new Job();
		job.setUserId(userId);

		try {
			return jobDao.getJobList(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		return null;
	}

	@Override
	public List<Job> getJobList(String[] jobId) {
		if (jobId == null || jobId.length == 0) {
			return null;
		}

		Job job = new Job();
		job.setCodes(jobId);

		return getJobList(job);
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

	@Override
	public BooleanResult publish(Long userId, Job job) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (job == null) {
			result.setCode("项目信息不能为空");
			return result;
		}

		job.setUserId(userId);
		job.setModifyUser(userId.toString());

		try {
			jobDao.createJob(job);
			result.setCode(job.getJobId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);

			result.setCode("项目信息创建失败，请稍后再试");
		}

		return result;
	}

}
