package com.jk.jobs.expert.dao;

import java.util.List;

import com.jk.jobs.api.expert.bo.ExpertJobCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IExpertJobCatDao {

	/**
	 * 
	 * @param expertJobCat
	 * @return
	 */
	List<ExpertJobCat> getExpertJobCatList(ExpertJobCat expertJobCat);

	/**
	 * 
	 * @param expertJobCat
	 * @return
	 */
	int createExpertJobCat(ExpertJobCat expertJobCat);

	/**
	 * 
	 * @param expertJobCat
	 * @return
	 */
	int deleteExpertJobCat(ExpertJobCat expertJobCat);

}
