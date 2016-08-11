package com.jk.jobs.auth.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.auth.IAuthService;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.wideka.weixin.api.auth.IOAuth2Service;
import com.wideka.weixin.api.auth.bo.AccessToken;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class AuthServiceImpl implements IAuthService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(AuthServiceImpl.class);

	@Resource
	private IOAuth2Service oauth2Service;

	@Value("${weixin.app.id}")
	private String appId;

	@Value("${weixin.app.secret}")
	private String appSecret;

	@Override
	public BooleanResult authorize(String redirectUrl, String scope) {
		return authorize(redirectUrl, scope, null);
	}

	@Override
	public BooleanResult authorize(String redirectUrl, String scope, String state) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(redirectUrl)) {
			result.setCode("redirectUrl 不能为空");
			return result;
		}

		if (StringUtils.isBlank(scope)) {
			result.setCode("scope 不能为空");
			return result;
		}

		try {
			result.setCode(oauth2Service.authorize(appId, redirectUrl, scope, state));
			result.setResult(true);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode(e.getMessage());
		}

		return result;
	}

	@Override
	public AccessToken accessToken(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		try {
			return oauth2Service.accessToken(appId, appSecret, code);
		} catch (ServiceException e) {
			logger.error(e);
		}

		return null;
	}

}
