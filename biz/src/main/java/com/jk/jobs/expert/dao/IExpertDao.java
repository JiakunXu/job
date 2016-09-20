package com.jk.jobs.expert.dao;

import java.util.List;

import com.jk.jobs.api.expert.bo.Expert;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IExpertDao {

	/**
	 * 
	 * @param expert
	 * @return
	 */
	List<Expert> getExpertList(Expert expert);

	/**
	 * 
	 * @param expert
	 * @return
	 */
	Expert getExpert(Expert expert);

	/**
	 * 
	 * @param expert
	 * @return
	 */
	int createExpert(Expert expert);

	/**
	 * 
	 * @param expert
	 * @return
	 */
	int updateExpert(Expert expert);
}
