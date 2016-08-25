package com.jk.jobs.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.user.IUserJobService;
import com.jk.jobs.api.user.bo.UserJob;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.user.dao.IUserJobDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserJobServiceImpl implements IUserJobService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserJobServiceImpl.class);

	@Resource
	private IJobService jobService;

	@Resource
	private IUserJobDao userJobDao;

	@Override
	public BooleanResult deliver(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		UserJob userJob = new UserJob();
		userJob.setUserId(userId);
		userJob.setModifyUser(userId.toString());

		try {
			userJob.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			userJobDao.createUserJob(userJob);
			result.setCode(userJob.getId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);

			result.setCode("用户项目信息创建失败，请稍后再试");
		}

		return result;
	}

	@Override
	public List<Job> getJobList(Long userId) {
		if (userId == null) {
			return null;
		}

		UserJob userJob = new UserJob();
		userJob.setUserId(userId);

		List<UserJob> userJobList = getUserJobList(userJob);

		if (userJobList == null || userJobList.size() == 0) {
			return null;
		}

		Map<Long, UserJob> map = new HashMap<Long, UserJob>();
		String[] jobId = new String[userJobList.size()];
		int i = 0;

		for (UserJob uj : userJobList) {
			map.put(uj.getJobId(), uj);
			jobId[i++] = uj.getJobId().toString();
		}

		List<Job> jobList = jobService.getJobList(jobId);

		if (jobList == null || jobList.size() == 0) {
			return null;
		}

		for (Job job : jobList) {
			UserJob uj = map.get(job.getJobId());
			job.setCreateDate(uj.getCreateDate());
		}

		return jobList;
	}

	@Override
	public List<UserJob> getUserList(String jobId) {
		if (StringUtils.isBlank(jobId)) {
			return null;
		}

		UserJob userJob = new UserJob();

		try {
			userJob.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		return getUserJobList(userJob);
	}

	/**
	 * 
	 * @param userJob
	 * @return
	 */
	private List<UserJob> getUserJobList(UserJob userJob) {
		try {
			return userJobDao.getUserJobList(userJob);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);
		}

		return null;
	}

}
