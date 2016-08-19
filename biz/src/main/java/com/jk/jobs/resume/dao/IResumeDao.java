package com.jk.jobs.resume.dao;

import java.util.List;

import com.jk.jobs.api.resume.bo.Resume;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IResumeDao {

	/**
	 * 
	 * @param resume
	 * @return
	 */
	List<Resume> getResumeList(Resume resume);

}