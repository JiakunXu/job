package com.jk.jobs.api.notify;

import java.util.List;

import com.jk.jobs.api.notify.bo.Notify;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface INotifyService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<Notify> getNotifyList(Long userId);

}
