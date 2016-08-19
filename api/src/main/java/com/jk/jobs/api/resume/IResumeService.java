package com.jk.jobs.api.resume;

import java.util.List;

import com.jk.jobs.api.resume.bo.Resume;

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

}
