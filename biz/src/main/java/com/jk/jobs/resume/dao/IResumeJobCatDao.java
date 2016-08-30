package com.jk.jobs.resume.dao;

import java.util.List;

import com.jk.jobs.api.resume.bo.ResumeJobCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IResumeJobCatDao {

	/**
	 * 
	 * @param resumeJobCat
	 * @return
	 */
	List<ResumeJobCat> getResumeJobCatList(ResumeJobCat resumeJobCat);

}
