package com.jk.jobs.resume.dao;

import java.util.List;

import com.jk.jobs.api.resume.bo.ResumeDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IResumeDetailDao {

	/**
	 * 
	 * @param resume
	 * @return
	 */
	List<ResumeDetail> getResumeDetailList(ResumeDetail resumeDetail);

}
