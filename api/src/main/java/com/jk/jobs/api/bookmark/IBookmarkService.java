package com.jk.jobs.api.bookmark;

import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBookmarkService {

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult save(Long userId, String jobId);

}
