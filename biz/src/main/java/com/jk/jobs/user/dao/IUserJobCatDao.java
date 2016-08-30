package com.jk.jobs.user.dao;

import java.util.List;

import com.jk.jobs.api.user.bo.UserJobCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserJobCatDao {

	/**
	 * 
	 * @param userJobCat
	 * @return
	 */
	int createUserJobCat(UserJobCat userJobCat);

	/**
	 * 
	 * @param userJobCat
	 * @return
	 */
	List<UserJobCat> getUserJobCatList(UserJobCat userJobCat);

}
