package com.jk.jobs.weixin.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.weixin.ITicketService;
import com.jk.jobs.api.weixin.ITokenService;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.wideka.weixin.api.auth.IJSAPITicketService;
import com.wideka.weixin.api.auth.bo.Ticket;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class TicketServiceImpl implements ITicketService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TicketServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private ITokenService tokenService;

	@Resource
	private IJSAPITicketService jsapiTicketService;

	@Override
	public BooleanResult getTicket(String appId, String appSecret) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(appId)) {
			result.setCode("appId 不能为空.");
			return result;
		}

		if (StringUtils.isBlank(appSecret)) {
			result.setCode("appSecret 不能为空.");
			return result;
		}

		String t = null;
		String key = appId.trim() + "&" + appSecret.trim();

		try {
			t = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, e);
		}

		if (StringUtils.isNotBlank(t)) {
			result.setCode(t);
			result.setResult(true);
			return result;
		}

		result = tokenService.getToken("client_credential", appId, appSecret);
		if (!result.getResult()) {
			return result;
		}

		Ticket ticket = null;

		try {
			ticket = jsapiTicketService.getTicket(result.getCode());
		} catch (RuntimeException e) {
			logger.error(e);

			result.setCode(e.getMessage());
			result.setResult(false);
			return result;
		}

		t = ticket.getTicket();

		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, t, ticket.getExpiresIn());
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, e);
		}

		result.setCode(t);
		return result;
	}

	@Override
	public BooleanResult getTicket4Corp(String corpId, String corpSecret) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(corpId)) {
			result.setCode("corpId 不能为空.");
			return result;
		}

		if (StringUtils.isBlank(corpSecret)) {
			result.setCode("corpSecret 不能为空.");
			return result;
		}

		String t = null;
		String key = corpId.trim() + "&" + corpSecret.trim();

		try {
			t = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, e);
		}

		if (StringUtils.isNotBlank(t)) {
			result.setCode(t);
			result.setResult(true);
			return result;
		}

		result = tokenService.getToken(corpId, corpSecret);
		if (!result.getResult()) {
			return result;
		}

		Ticket ticket = null;

		try {
			ticket = jsapiTicketService.getTicket4Corp(result.getCode());
		} catch (RuntimeException e) {
			logger.error(e);

			result.setCode(e.getMessage());
			result.setResult(false);
			return result;
		}

		t = ticket.getTicket();

		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, t, ticket.getExpiresIn());
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_TICKET + key, e);
		}

		result.setCode(t);
		return result;
	}

}
