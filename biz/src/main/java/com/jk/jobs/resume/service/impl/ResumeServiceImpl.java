package com.jk.jobs.resume.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jk.jobs.api.resume.bo.ResumeJobCat;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.resume.dao.IResumeDao;
import com.jk.jobs.resume.dao.IResumeDetailDao;
import com.jk.jobs.resume.dao.IResumeJobCatDao;

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

	@Resource
	private IResumeJobCatDao resumeJobCatDao;

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
		resume.setResumeJobCatList(getResumeJobCatList(resume.getResumeId()));

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
		resume.setResumeJobCatList(getResumeJobCatList(resume.getResumeId()));

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

		// 获取 擅长模块
		// map0 remove 相同的 剩下的 删除
		final Map<Long, Boolean> map0 = new HashMap<Long, Boolean>();

		Long resumeId = resume.getResumeId();
		if (resumeId != null) {
			ResumeJobCat resumeJobCat = new ResumeJobCat();
			resumeJobCat.setResumeId(resumeId);

			List<ResumeJobCat> resumeJobCatList = getResumeJobCatList(resumeJobCat);
			if (resumeJobCatList != null && resumeJobCatList.size() > 0) {
				for (ResumeJobCat detail : resumeJobCatList) {
					map0.put(detail.getJobCId(), Boolean.TRUE);
				}
			}
		}

		// map1 map0 中不存在的 即 新建
		final Map<Long, Boolean> map1 = new HashMap<Long, Boolean>();

		List<ResumeJobCat> resumeJobCatList = resume.getResumeJobCatList();

		// 存在擅长模块
		if (resumeJobCatList != null && resumeJobCatList.size() > 0) {
			for (ResumeJobCat detail : resumeJobCatList) {
				Long key = detail.getJobCId();
				if (map0.containsKey(key)) {
					map0.remove(key);
				} else {
					map1.put(key, Boolean.TRUE);
				}
			}
		}

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
						//
						resumeDetail.setResumeId(resumeId);
						resumeDetail.setRank(rank++);
						resumeDetail.setModifyUser(resume.getModifyUser());

						Long detailId = resumeDetail.getDetailId();

						if (detailId == null) {
							// 默认 项目内容 空 即 无效
							if (StringUtils.isBlank(resumeDetail.getContent())) {
								continue;
							}

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

				// 删除 擅长模块
				if (map0.size() > 0) {
					String[] codes = new String[map0.size()];
					int i = 0;
					for (Map.Entry<Long, Boolean> m : map0.entrySet()) {
						codes[i++] = m.getKey().toString();
					}

					ResumeJobCat resumeJobCat0 = new ResumeJobCat();
					resumeJobCat0.setResumeId(resumeId);
					resumeJobCat0.setModifyUser(resume.getModifyUser());
					resumeJobCat0.setCodes(codes);

					try {
						resumeJobCatDao.deleteResumeJobCat(resumeJobCat0);
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(resumeJobCat0), e);
						ts.setRollbackOnly();

						result.setCode("擅长模块信息修改失败，请稍后再试");
						return result;
					}
				}

				// 新建 擅长模块
				if (map1.size() > 0) {
					for (Map.Entry<Long, Boolean> m : map1.entrySet()) {
						ResumeJobCat resumeJobCat1 = new ResumeJobCat();
						resumeJobCat1.setResumeId(resumeId);
						resumeJobCat1.setJobCId(m.getKey());
						resumeJobCat1.setModifyUser(resume.getModifyUser());

						try {
							resumeJobCatDao.createResumeJobCat(resumeJobCat1);
						} catch (Exception e) {
							logger.error(LogUtil.parserBean(resumeJobCat1), e);
							ts.setRollbackOnly();

							result.setCode("擅长模块信息修改失败，请稍后再试");
							return result;
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

	private List<ResumeJobCat> getResumeJobCatList(Long resumeId) {
		ResumeJobCat resumeJobCat = new ResumeJobCat();
		resumeJobCat.setResumeId(resumeId);

		List<ResumeJobCat> resumeJobCatList = getResumeJobCatList(resumeJobCat);

		if (resumeJobCatList == null || resumeJobCatList.size() == 0) {
			return null;
		}

		for (ResumeJobCat detail : resumeJobCatList) {
			JobCat c = jobCatService.getJobCat(detail.getJobCId());
			if (c != null) {
				detail.setJobCName(c.getJobCName());
			}
		}

		return resumeJobCatList;
	}

	private List<ResumeJobCat> getResumeJobCatList(ResumeJobCat resumeJobCat) {
		try {
			return resumeJobCatDao.getResumeJobCatList(resumeJobCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resumeJobCat), e);
		}

		return null;
	}

}
