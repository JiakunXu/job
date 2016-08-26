package com.jk.jobs.user.dao;

import java.util.List;

import com.jk.jobs.api.user.bo.UserJobDetail;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserJobDetailDao {

	/**
	 * 
	 * @param userJobDetail
	 * @return
	 */
	int createUserJobDetail(UserJobDetail userJobDetail);

	/**
	 * 
	 * @param userJobDetail
	 * @return
	 */
	List<UserJobDetail> getUserJobDetailList(UserJobDetail userJobDetail);

}
