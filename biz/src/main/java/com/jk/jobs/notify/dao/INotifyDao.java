package com.jk.jobs.notify.dao;

import java.util.List;

import com.jk.jobs.api.notify.bo.Notify;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface INotifyDao {

	/**
	 * 
	 * @param notify
	 * @return
	 */
	List<Notify> getNotifyList(Notify notify);

	/**
	 * 
	 * @param notify
	 * @return
	 */
	int createNotify(Notify notify);

}
