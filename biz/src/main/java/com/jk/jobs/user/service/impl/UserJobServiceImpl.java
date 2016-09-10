package com.jk.jobs.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.api.resume.bo.ResumeDetail;
import com.jk.jobs.api.resume.bo.ResumeJobCat;
import com.jk.jobs.api.user.IUserJobService;
import com.jk.jobs.api.user.bo.UserJob;
import com.jk.jobs.api.user.bo.UserJobCat;
import com.jk.jobs.api.user.bo.UserJobDetail;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.user.dao.IUserJobCatDao;
import com.jk.jobs.user.dao.IUserJobDao;
import com.jk.jobs.user.dao.IUserJobDetailDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserJobServiceImpl implements IUserJobService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserJobServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IResumeService resumeService;

	@Resource
	private IJobService jobService;

	@Resource
	private IJobCatService jobCatService;

	@Resource
	private IUserJobDao userJobDao;

	@Resource
	private IUserJobDetailDao userJobDetailDao;

	@Resource
	private IUserJobCatDao userJobCatDao;

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

		final UserJob userJob = new UserJob();
		final List<UserJobDetail> userJobDetailList = new ArrayList<UserJobDetail>();
		final List<UserJobCat> userJobCatList = new ArrayList<UserJobCat>();

		userJob.setUserId(userId);
		userJob.setModifyUser(userId.toString());

		try {
			userJob.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		// 1. 判断是否已投递简历

		// 1.1 只查询投递状态的简历
		int count = getJobCount(userId, IUserJobService.DELIVER);
		if (count > 0) {
			result.setCode("简历已投递");
			return result;
		}

		// 1.2 只查询被拒绝状态的简历
		count = getJobCount(userId, IUserJobService.IGNORE);
		if (count > 0) {
			result.setCode("简历已投递");
			return result;
		}

		// 2. 判断用户是否已维护简历
		Resume resume = resumeService.getResume(userId);

		if (resume == null) {
			result.setCode("简历信息不能为空");
			return result;
		}

		userJob.setName(resume.getName());
		userJob.setSex(resume.getSex());
		userJob.setBirthday(resume.getBirthday());
		userJob.setTel(resume.getTel());
		userJob.setWorkYear(resume.getWorkYear());
		userJob.setEducation(resume.getEducation());
		userJob.setRemark(resume.getRemark());
		// 简历投递
		userJob.setType(IUserJobService.DELIVER);

		List<ResumeDetail> resumeDetailList = resume.getResumeDetailList();
		if (resumeDetailList != null && resumeDetailList.size() > 0) {
			for (ResumeDetail detail : resumeDetailList) {
				UserJobDetail userJobDetail = new UserJobDetail();
				userJobDetail.setJobCId(detail.getJobCId());
				userJobDetail.setCycle(detail.getCycle());
				userJobDetail.setContent(detail.getContent());
				userJobDetail.setRank(detail.getRank());
				userJobDetail.setModifyUser(userJob.getModifyUser());

				userJobDetailList.add(userJobDetail);
			}
		}

		List<ResumeJobCat> resumeJobCatList = resume.getResumeJobCatList();
		if (resumeJobCatList != null && resumeJobCatList.size() > 0) {
			for (ResumeJobCat detail : resumeJobCatList) {
				UserJobCat userJobCat = new UserJobCat();
				userJobCat.setJobCId(detail.getJobCId());
				userJobCat.setModifyUser(userJob.getModifyUser());

				userJobCatList.add(userJobCat);
			}
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long userJobId = null;

				try {
					userJobDao.createUserJob(userJob);
					userJobId = userJob.getUserJobId();
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(userJob), e);
					ts.setRollbackOnly();

					result.setCode("简历信息创建失败，请稍后再试");
					return result;
				}

				if (userJobDetailList != null && userJobDetailList.size() > 0) {
					for (UserJobDetail userJobDetail : userJobDetailList) {
						userJobDetail.setUserJobId(userJobId);

						try {
							userJobDetailDao.createUserJobDetail(userJobDetail);
						} catch (Exception e) {
							logger.error(LogUtil.parserBean(userJobDetail), e);
							ts.setRollbackOnly();

							result.setCode("简历明细信息创建失败，请稍后再试");
							return result;
						}
					}
				}

				if (userJobCatList != null && userJobCatList.size() > 0) {
					for (UserJobCat userJobCat : userJobCatList) {
						userJobCat.setUserJobId(userJobId);

						try {
							userJobCatDao.createUserJobCat(userJobCat);
						} catch (Exception e) {
							logger.error(LogUtil.parserBean(userJobCat), e);
							ts.setRollbackOnly();

							result.setCode("擅长模块信息创建失败，请稍后再试");
							return result;
						}
					}
				}

				result.setCode(userJobId.toString());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public int getJobCount(Long userId, String type) {
		if (userId == null) {
			return 0;
		}

		UserJob userJob = new UserJob();
		userJob.setUserId(userId);
		userJob.setType(type);

		return getUserJobCount(userJob);
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
			job.setType(uj.getType());
		}

		return jobList;
	}

	@Override
	public BooleanResult revoke(Long userId, String jobId) {
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

		userJob.setType(IUserJobService.REVOKE);

		try {
			userJobDao.updateUserJob(userJob);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);

			result.setCode("简历撤销失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult delete(Long userId, String jobId) {
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

		userJob.setType(IUserJobService.DELETE);

		try {
			userJobDao.updateUserJob(userJob);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);

			result.setCode("简历撤销失败，请稍后再试");
		}

		return result;
	}

	// >>>>>>>>>>以下是项目相关简历<<<<<<<<<<

	@Override
	public int getUserCount(Long jobId) {
		if (jobId == null) {
			return 0;
		}

		UserJob userJob = new UserJob();
		userJob.setJobId(jobId);
		// 只查询 投递 状态
		userJob.setType(IUserJobService.DELIVER);

		return getUserJobCount(userJob);
	}

	@Override
	public List<UserJob> getUserList(String jobId) {
		if (StringUtils.isBlank(jobId)) {
			return null;
		}

		UserJob userJob = new UserJob();
		// 只查询 投递 状态
		userJob.setType(IUserJobService.DELIVER);

		try {
			userJob.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		List<UserJob> userJobList = getUserJobList(userJob);

		if (userJobList == null || userJobList.size() == 0) {
			return null;
		}

		// 擅长模块
		for (UserJob uj : userJobList) {
			uj.setUserJobCatList(getUserJobCatList(uj.getUserJobId()));
		}

		return userJobList;
	}

	@Override
	public UserJob detail(Long jobId, String userJobId) {
		if (jobId == null) {
			return null;
		}

		if (StringUtils.isBlank(userJobId)) {
			return null;
		}

		String key = jobId + "&" + userJobId.trim();

		UserJob userJob = null;

		try {
			userJob = (UserJob) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_USER_JOB_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_USER_JOB_ID + key, e);
		}

		if (userJob != null) {
			return userJob;
		}

		userJob = new UserJob();
		userJob.setJobId(jobId);

		try {
			userJob.setUserJobId(Long.valueOf(userJobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		userJob = getUserJob(userJob);

		if (userJob == null) {
			return null;
		}

		userJob.setUserJobDetailList(getUserJobDetailList(userJob.getUserJobId()));
		userJob.setUserJobCatList(getUserJobCatList(userJob.getUserJobId()));

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_USER_JOB_ID + key, userJob);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_USER_JOB_ID + key, e);
		}

		return userJob;
	}

	@Override
	public BooleanResult ignore(Long jobId, String userJobId, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		UserJob userJob = new UserJob();

		if (jobId == null) {
			result.setCode("项目信息不能为空");
			return result;
		}

		userJob.setJobId(jobId);

		if (StringUtils.isBlank(userJobId)) {
			result.setCode("简历信息不能为空");
			return result;
		}

		try {
			userJob.setUserJobId(Long.valueOf(userJobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("简历信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		userJob.setType(IUserJobService.IGNORE);
		userJob.setModifyUser(modifyUser);

		try {
			userJobDao.updateUserJob(userJob);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);

			result.setCode("简历忽略失败，请稍后再试");
		}

		return result;
	}

	/**
	 * 
	 * @param userJob
	 * @return
	 */
	private int getUserJobCount(UserJob userJob) {
		try {
			return userJobDao.getUserJobCount(userJob);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);
		}

		return 0;
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

	/**
	 * 
	 * @param userJob
	 * @return
	 */
	private UserJob getUserJob(UserJob userJob) {
		try {
			return userJobDao.getUserJob(userJob);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJob), e);
		}

		return null;
	}

	/**
	 * 
	 * @param userJobDetail
	 * @return
	 */
	private List<UserJobDetail> getUserJobDetailList(Long userJobId) {
		UserJobDetail userJobDetail = new UserJobDetail();
		userJobDetail.setUserJobId(userJobId);

		List<UserJobDetail> userJobDetailList = null;

		try {
			userJobDetailList = userJobDetailDao.getUserJobDetailList(userJobDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJobDetail), e);
		}

		if (userJobDetailList == null || userJobDetailList.size() == 0) {
			return null;
		}

		for (UserJobDetail detail : userJobDetailList) {
			JobCat c = jobCatService.getJobCat(detail.getJobCId());
			if (c != null) {
				detail.setJobCName(c.getJobCName());
			}
		}

		return userJobDetailList;
	}

	private List<UserJobCat> getUserJobCatList(Long userJobId) {
		UserJobCat userJobCat = new UserJobCat();
		userJobCat.setUserJobId(userJobId);

		List<UserJobCat> userJobCatList = null;

		try {
			userJobCatList = userJobCatDao.getUserJobCatList(userJobCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userJobCat), e);
		}

		if (userJobCatList == null || userJobCatList.size() == 0) {
			return null;
		}

		for (UserJobCat detail : userJobCatList) {
			JobCat c = jobCatService.getJobCat(detail.getJobCId());
			if (c != null) {
				detail.setJobCName(c.getJobCName());
			}
		}

		return userJobCatList;
	}

}
