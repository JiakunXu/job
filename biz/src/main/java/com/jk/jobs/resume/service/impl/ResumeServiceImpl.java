package com.jk.jobs.resume.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.api.resume.bo.ResumeDetail;
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

		ResumeDetail resumeDetail = new ResumeDetail();
		resumeDetail.setResumeId(resume.getResumeId());

		resume.setResumeDetailList(getResumeDetailList(resumeDetail));

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

		ResumeDetail resumeDetail = new ResumeDetail();
		resumeDetail.setResumeId(resume.getResumeId());

		resume.setResumeDetailList(getResumeDetailList(resumeDetail));

		return resume;
	}

	private Resume getResume(Resume resume) {
		try {
			return resumeDao.getResume(resume);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resume), e);
		}

		return null;
	}

	private List<ResumeDetail> getResumeDetailList(ResumeDetail resumeDetail) {
		try {
			return resumeDetailDao.getResumeDetailList(resumeDetail);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(resumeDetail), e);
		}

		return null;
	}

}
