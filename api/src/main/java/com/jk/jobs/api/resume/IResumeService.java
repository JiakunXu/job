package com.jk.jobs.api.resume;

import java.util.List;

import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IResumeService {

	/**
	 * 
	 * @param resume
	 * @return
	 */
	List<Resume> getResumeList(Resume resume);

	/**
	 * 
	 * @param resumeId
	 * @return
	 */
	Resume getResume(String resumeId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Resume getResume(Long userId);

	/**
	 * 
	 * @param userId
	 * @param resume
	 * @return
	 */
	BooleanResult saveOrUpdate(Long userId, Resume resume);

}
