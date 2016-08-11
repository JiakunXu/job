package com.jk.jobs.api.weixin;

import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ITokenService {

	/**
	 * 
	 * @param grantType
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	BooleanResult getToken(String grantType, String appId, String appSecret);

	/**
	 * 
	 * @param corpId
	 * @param corpSecret
	 * @return
	 */
	BooleanResult getToken(String corpId, String corpSecret);

}
