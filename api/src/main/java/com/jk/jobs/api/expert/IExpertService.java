package com.jk.jobs.api.expert;

import java.util.List;

import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IExpertService {

	/**
	 * 申请.
	 */
	String APPLY = "apply";

	/**
	 * 申请通过.
	 */
	String APPLIED = "applied";

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

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Expert getExpert(Long userId);

	/**
	 * 
	 * @param userId
	 * @param expert
	 * @return
	 */
	BooleanResult saveOrUpdate(Long userId, Expert expert);

}
