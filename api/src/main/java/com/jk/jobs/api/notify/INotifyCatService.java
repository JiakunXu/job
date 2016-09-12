package com.jk.jobs.api.notify;

import com.jk.jobs.api.notify.bo.NotifyCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface INotifyCatService {

	/**
	 * 
	 * @param notifyCId
	 * @return
	 */
	NotifyCat getNotifyCat(Long notifyCId);

}
