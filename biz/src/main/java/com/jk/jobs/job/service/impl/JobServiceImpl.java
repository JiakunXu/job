package com.jk.jobs.job.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jk.jobs.api.bookmark.IBookmarkService;
import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.api.job.bo.JobDetail;
import com.jk.jobs.api.user.IUserJobService;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.api.user.bo.UserJob;
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
	private TransactionTemplate transactionTemplate;

	@Resource
	private IUserService userService;

	@Resource
	private IUserJobService userJobService;

	@Resource
	private IJobCatService jobCatService;

	@Resource
	private IBookmarkService bookmarkService;

	@Resource
	private IJobDao jobDao;

	@Resource
	private IJobDetailDao jobDetailDao;

	@Override
	public int getJobCount(Job job) {
		if (job == null) {
			return 0;
		}

		try {
			return jobDao.getJobCount(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		return 0;
	}

	@Override
	public List<Job> getJobList(Job job) {
		if (job == null) {
			return null;
		}

		List<Job> jobList = null;

		try {
			jobList = jobDao.getJobList(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		if (jobList == null || jobList.size() == 0) {
			return null;
		}

		for (Job j : jobList) {
			// 项目发布人
			User u = userService.getUser(j.getUserId());
			if (u != null) {
				j.setUserName(u.getUserName());
			}

			// 项目类别
			JobCat c = jobCatService.getJobCat(j.getJobCId());
			if (c != null) {
				j.setJobCName(c.getJobCName());
			}

			// 项目被收藏
			j.setStar(bookmarkService.getBookmarkCount(j.getJobId()));
		}

		return jobList;
	}

	@Override
	public List<Job> getJobList(Long userId) {
		if (userId == null) {
			return null;
		}

		Job job = new Job();
		job.setUserId(userId);

		int limit = getJobCount(job);

		if (limit == 0) {
			return null;
		}

		job.setLimit(limit);
		job.setOffset(0);

		List<Job> jobList = getJobList(job);

		if (jobList == null || jobList.size() == 0) {
			return null;
		}

		for (Job j : jobList) {
			// 已投简历
			j.setCount(userJobService.getUserCount(j.getJobId()));
		}

		return jobList;
	}

	@Override
	public List<Job> getJobList(String[] jobId) {
		if (jobId == null || jobId.length == 0) {
			return null;
		}

		Job job = new Job();
		job.setCodes(jobId);

		int limit = getJobCount(job);

		if (limit == 0) {
			return null;
		}

		job.setLimit(limit);
		job.setOffset(0);

		return getJobList(job);
	}

	@Override
	public Job getJob(String jobId) {
		return getJob(null, jobId);
	}

	@Override
	public Job getJob(Long userId, String jobId) {
		if (StringUtils.isBlank(jobId)) {
			return null;
		}

		Job job = new Job();
		job.setUserId(userId);

		try {
			job.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(LogUtil.parserBean(job), e);

			return null;
		}

		job = getJob(job);

		if (job == null) {
			return null;
		}

		// 项目发布人
		User u = userService.getUser(job.getUserId());
		if (u != null) {
			job.setUserName(u.getUserName());
		}

		// 项目类别
		JobCat c = jobCatService.getJobCat(job.getJobCId());
		if (c != null) {
			job.setJobCName(c.getJobCName());
		}

		// 项目介绍
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
	public BooleanResult publish(Long userId, final Job job) {
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
		// TODO
		job.setType(IJobService.PUBLISH);
		job.setModifyUser(userId.toString());

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long jobId = null;

				try {
					jobDao.createJob(job);
					jobId = job.getJobId();
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(job), e);
					ts.setRollbackOnly();

					result.setCode("项目信息创建失败，请稍后再试");
					return result;
				}

				List<JobDetail> jobDetailList = job.getJobDetailList();

				if (jobDetailList != null && jobDetailList.size() > 0) {
					int rank = 0;
					for (JobDetail jobDetail : jobDetailList) {
						jobDetail.setJobId(jobId);
						jobDetail.setRank(rank++);
						jobDetail.setModifyUser(job.getModifyUser());

						try {
							jobDetailDao.createJobDetail(jobDetail);
						} catch (Exception e) {
							logger.error(LogUtil.parserBean(jobDetail), e);
							ts.setRollbackOnly();

							result.setCode("项目明细信息创建失败，请稍后再试");
							return result;
						}
					}
				}

				result.setCode(jobId.toString());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult update(Long userId, final Job job) {
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

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long jobId = job.getJobId();

				try {
					int c = jobDao.updateJob(job);
					if (c != 1) {
						ts.setRollbackOnly();

						result.setCode("项目信息修改失败，请稍后再试");
						return result;
					}
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(job), e);
					ts.setRollbackOnly();

					result.setCode("项目信息修改失败，请稍后再试");
					return result;
				}

				List<JobDetail> jobDetailList = job.getJobDetailList();

				if (jobDetailList != null && jobDetailList.size() > 0) {
					int rank = 0;
					for (JobDetail jobDetail : jobDetailList) {
						jobDetail.setJobId(jobId);
						jobDetail.setRank(rank++);
						jobDetail.setModifyUser(job.getModifyUser());

						Long detailId = jobDetail.getDetailId();

						if (detailId == null) {
							try {
								jobDetailDao.createJobDetail(jobDetail);
							} catch (Exception e) {
								logger.error(LogUtil.parserBean(jobDetail), e);
								ts.setRollbackOnly();

								result.setCode("项目明细信息创建失败，请稍后再试");
								return result;
							}
						} else {
							try {
								jobDetailDao.updateJobDetail(jobDetail);
							} catch (Exception e) {
								logger.error(LogUtil.parserBean(jobDetail), e);
								ts.setRollbackOnly();

								result.setCode("项目明细信息修改失败，请稍后再试");
								return result;
							}
						}
					}
				}

				result.setCode(jobId.toString());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult finish(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Job job = new Job();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		job.setUserId(userId);
		job.setModifyUser(userId.toString());

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			job.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		job.setType(IJobService.FINISH);

		try {
			int c = jobDao.finishJob(job);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("项目已撤销或结束，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);

			result.setCode("项目结束失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult revoke(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Job job = new Job();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		job.setUserId(userId);
		job.setModifyUser(userId.toString());

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			job.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		job.setType(IJobService.REVOKE);

		try {
			int c = jobDao.revokeJob(job);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("项目已结束或撤销，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);

			result.setCode("项目撤销失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult delete(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Job job = new Job();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		job.setUserId(userId);
		job.setModifyUser(userId.toString());

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			job.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		job.setType(IJobService.DELETE);

		try {
			int c = jobDao.deleteJob(job);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("项目尚未撤销或已删除，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);

			result.setCode("项目删除失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult copy(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		final Job job = new Job();
		final JobDetail jobDetail = new JobDetail();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		job.setUserId(userId);
		// TODO
		job.setType(IJobService.PUBLISH);
		job.setModifyUser(userId.toString());
		jobDetail.setModifyUser(job.getModifyUser());

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			job.setJobId(Long.valueOf(jobId));
			jobDetail.setCopyJobId(job.getJobId());
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long jobId = null;

				try {
					jobDao.copyJob(job);
					jobId = job.getJobId();
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(job), e);
					ts.setRollbackOnly();

					result.setCode("项目信息创建失败，请稍后再试");
					return result;
				}

				try {
					jobDetail.setJobId(jobId);
					jobDetailDao.copyJobDetail(jobDetail);
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(jobDetail), e);
					ts.setRollbackOnly();

					result.setCode("项目明细信息创建失败，请稍后再试");
					return result;
				}

				result.setCode(jobId.toString());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public List<UserJob> getUserList(Long userId, String jobId) {
		if (userId == null || StringUtils.isBlank(jobId)) {
			return null;
		}

		// 验证 userId 是 项目发布者
		Job job = getJob(userId, jobId);

		if (job == null) {
			return null;
		}

		// 根据 jobId 获得投简历
		return userJobService.getUserList(jobId);
	}

	@Override
	public UserJob detail(Long userId, String jobId, String userJobId) {
		if (userId == null) {
			return null;
		}

		if (StringUtils.isBlank(jobId)) {
			return null;
		}

		if (StringUtils.isBlank(userJobId)) {
			return null;
		}

		// 验证 userId 是 项目发布者
		Job job = getJob(userId, jobId);

		if (job == null) {
			return null;
		}

		return userJobService.detail(job.getJobId(), userJobId);
	}

	@Override
	public BooleanResult ignore(Long userId, String jobId, String userJobId) {
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

		if (StringUtils.isBlank(userJobId)) {
			result.setCode("简历信息不能为空");
			return result;
		}

		// 验证 userId 是 项目发布者
		Job job = getJob(userId, jobId);

		if (job == null) {
			result.setCode("没有权限操作简历");
			return result;
		}

		return userJobService.ignore(job.getJobId(), userJobId, userId.toString());
	}

	/**
	 * 
	 * @param job
	 * @return
	 */
	private Job getJob(Job job) {
		try {
			return jobDao.getJob(job);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(job), e);
		}

		return null;
	}

}
