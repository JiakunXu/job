package com.jk.jobs.api.weixin;

import com.jk.jobs.api.weixin.bo.Ticket;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IWeixinService {

	/**
	 * 
	 * @param url
	 * @return
	 */
	Ticket getTicket(String url);

}
