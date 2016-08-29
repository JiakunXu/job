package com.jk.jobs.resume.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.api.resume.bo.ResumeDetail;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.resume.dao.IResumeDao;
import com.jk.jobs.resume.dao.IResumeDetailDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ResumeServiceImpl implements IResumeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ResumeServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IJobCatService jobCatService;

	@Resource
	private IResumeDao resumeDao;

	@Resource
	private IResumeDetailDao resumeDetailDao;

	@Override
	public List<Resume> getResumeList(Resume resume) {
		if (resume == null) {
			return null;
		}

		try {
			return resumeDao.getResumeList(resume);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resume), e);
		}

		return null;
	}

	@Override
	public Resume getResume(String resumeId) {
		if (StringUtils.isBlank(resumeId)) {
			return null;
		}

		Resume resume = new Resume();

		try {
			resume.setResumeId(Long.valueOf(resumeId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		resume = getResume(resume);

		if (resume == null) {
			return null;
		}

		resume.setResumeDetailList(getResumeDetailList(resume.getResumeId()));

		return resume;
	}

	@Override
	public Resume getResume(Long userId) {
		if (userId == null) {
			return null;
		}

		Resume resume = new Resume();
		resume.setUserId(userId);

		resume = getResume(resume);

		if (resume == null) {
			return null;
		}

		resume.setResumeDetailList(getResumeDetailList(resume.getResumeId()));

		return resume;
	}

	@Override
	public BooleanResult saveOrUpdate(Long userId, final Resume resume) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (resume == null) {
			result.setCode("简历信息不能为空");
			return result;
		}

		resume.setUserId(userId);
		resume.setModifyUser(userId.toString());

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long resumeId = resume.getResumeId();

				if (resumeId == null) {
					try {
						resumeDao.createResume(resume);
						resumeId = resume.getResumeId();
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(resume), e);
						ts.setRollbackOnly();

						result.setCode("简历信息创建失败，请稍后再试");
						return result;
					}
				} else {
					try {
						int c = resumeDao.updateResume(resume);
						if (c != 1) {
							ts.setRollbackOnly();

							result.setCode("简历信息修改失败，请稍后再试");
							return result;
						}
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(resume), e);
						ts.setRollbackOnly();

						result.setCode("简历信息修改失败，请稍后再试");
						return result;
					}
				}

				List<ResumeDetail> resumeDetailList = resume.getResumeDetailList();

				if (resumeDetailList != null && resumeDetailList.size() > 0) {
					int rank = 0;
					for (ResumeDetail resumeDetail : resumeDetailList) {
						// 默认 项目内容 空 即 无效
						if (StringUtils.isBlank(resumeDetail.getContent())) {
							continue;
						}

						//
						resumeDetail.setResumeId(resumeId);
						resumeDetail.setRank(rank++);
						resumeDetail.setModifyUser(resume.getModifyUser());

						Long detailId = resumeDetail.getDetailId();

						if (detailId == null) {
							try {
								resumeDetailDao.createResumeDetail(resumeDetail);
							} catch (Exception e) {
								logger.error(LogUtil.parserBean(resumeDetail), e);
								ts.setRollbackOnly();

								result.setCode("简历明细信息创建失败，请稍后再试");
								return result;
							}
						} else {
							try {
								resumeDetailDao.updateResumeDetail(resumeDetail);
							} catch (Exception e) {
								logger.error(LogUtil.parserBean(resumeDetail), e);
								ts.setRollbackOnly();

								result.setCode("简历明细信息修改失败，请稍后再试");
								return result;
							}
						}
					}
				}

				result.setCode(resumeId.toString());
				result.setResult(true);
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult delete(Long userId, String detailId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		ResumeDetail resumeDetail = new ResumeDetail();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		resumeDetail.setModifyUser(userId.toString());

		if (StringUtils.isBlank(detailId)) {
			result.setCode("简历明细信息不能为空");
			return result;
		}

		try {
			resumeDetail.setDetailId(Long.valueOf(detailId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("简历明细信息不能为空");
			return result;
		}

		Resume resume = getResume(userId);

		if (resume == null) {
			result.setCode("简历信息不能为空");
			return result;
		}

		resumeDetail.setResumeId(resume.getResumeId());

		try {
			int c = resumeDetailDao.deleteResumeDetail(resumeDetail);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("简历明细信息删除失败，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resumeDetail), e);

			result.setCode("简历明细信息删除失败，请稍后再试");
		}

		return result;
	}

	private Resume getResume(Resume resume) {
		try {
			return resumeDao.getResume(resume);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resume), e);
		}

		return null;
	}

	private List<ResumeDetail> getResumeDetailList(Long resumeId) {
		ResumeDetail resumeDetail = new ResumeDetail();
		resumeDetail.setResumeId(resumeId);

		List<ResumeDetail> resumeDetailList = null;

		try {
			resumeDetailList = resumeDetailDao.getResumeDetailList(resumeDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resumeDetail), e);
		}

		if (resumeDetailList == null || resumeDetailList.size() == 0) {
			return null;
		}

		for (ResumeDetail detail : resumeDetailList) {
			JobCat c = jobCatService.getJobCat(detail.getJobCId());
			if (c != null) {
				detail.setJobCName(c.getJobCName());
			}
		}

		return resumeDetailList;
	}

}
