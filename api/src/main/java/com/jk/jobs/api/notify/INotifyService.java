package com.jk.jobs.api.notify;

import java.util.List;

import com.jk.jobs.api.notify.bo.Notify;
import com.jk.jobs.framework.bo.BooleanResult;

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

	/**
	 * 
	 * @param userId
	 * @param content
	 * @param modifyUser
	 * @return
	 */
	BooleanResult notify(Long userId, String content, String modifyUser);

}
