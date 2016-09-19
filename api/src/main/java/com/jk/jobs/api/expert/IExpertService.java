package com.jk.jobs.api.expert;

import java.util.List;

import com.jk.jobs.api.expert.bo.Expert;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IExpertService {

	/**
	 * 
	 * @return
	 */
	List<Expert> getExpertList();

	/**
	 * 
	 * @param jobCId
	 * @return
	 */
	List<Expert> getExpertList(String jobCId);

	/**
	 * 
	 * @param expertId
	 * @return
	 */
	Expert getExpert(String expertId);

}
