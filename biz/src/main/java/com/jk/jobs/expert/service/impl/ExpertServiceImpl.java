package com.jk.jobs.expert.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.expert.bo.ExpertJobCat;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.expert.dao.IExpertDao;
import com.jk.jobs.expert.dao.IExpertJobCatDao;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.exception.ServiceException;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ExpertServiceImpl implements IExpertService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ExpertServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserService userService;

	@Resource
	private IExpertDao expertDao;

	@Resource
	private IExpertJobCatDao expertJobCatDao;

	@Override
	public List<Expert> getExpertList() {
		return getExpertList("");
	}

	@Override
	public List<Expert> getExpertList(String jobCId) {
		Expert expert = new Expert();
		expert.setType(IExpertService.APPLIED);

		List<Expert> expertList = getExpertList(expert);

		if (expertList == null || expertList.size() == 0) {
			return null;
		}

		for (Expert expret : expertList) {
			User user = userService.getUser(expret.getUserId());
			if (user == null) {
				continue;
			}

			expret.setUserName(user.getUserName());
		}

		return expertList;
	}

	@Override
	public Expert getExpert(String expertId) {
		if (StringUtils.isBlank(expertId)) {
			return null;
		}

		String key = expertId.trim();

		Expert expert = null;

		try {
			expert = (Expert) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, e);
		}

		if (expert != null) {
			return expert;
		}

		expert = new Expert();

		try {
			expert.setExpertId(Long.valueOf(expertId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		expert = getExpert(expert);
		if (expert == null) {
			return null;
		}

		User user = userService.getUser(expert.getUserId());
		if (user != null) {
			expert.setUserName(user.getUserName());
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, expert);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + key, e);
		}

		return expert;
	}

	@Override
	public Expert getExpert(Long userId) {
		if (userId == null) {
			return null;
		}

		Long key = userId;

		Expert expert = null;

		try {
			expert = (Expert) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, e);
		}

		if (expert != null) {
			return expert;
		}

		expert = new Expert();
		expert.setUserId(userId);

		expert = getExpert(expert);
		if (expert == null) {
			return null;
		}

		ExpertJobCat expertJobCat = new ExpertJobCat();
		expertJobCat.setExpertId(expert.getExpertId());

		expert.setExpertJobCatList(getExpertJobCatList(expertJobCat));

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, expert);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + key, e);
		}

		return expert;
	}

	/**
	 * 
	 * @param expert
	 * @return
	 */
	private BooleanResult validate(Expert expert) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (expert == null) {
			result.setCode("专家信息不能为空");
			return result;
		}

		String content = expert.getContent();
		if (StringUtils.isBlank(content)) {
			result.setCode("专家介绍不能为空");
			return result;
		}

		BigDecimal price = expert.getPrice();
		if (price == null) {
			result.setCode("咨询费用不能为空");
			return result;
		}

		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult saveOrUpdate(Long userId, final Expert expert) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		result = validate(expert);
		if (!result.getResult()) {
			return result;
		}

		expert.setUserId(userId);
		expert.setModifyUser(userId.toString());

		// 获取 擅长模块
		// map0 remove 相同的 剩下的 删除
		final Map<Long, Boolean> map0 = new HashMap<Long, Boolean>();

		Long expertId = expert.getExpertId();
		if (expertId != null) {
			ExpertJobCat expertJobCat = new ExpertJobCat();
			expertJobCat.setExpertId(expertId);

			List<ExpertJobCat> expertJobCatList = getExpertJobCatList(expertJobCat);
			if (expertJobCatList != null && expertJobCatList.size() > 0) {
				for (ExpertJobCat detail : expertJobCatList) {
					map0.put(detail.getJobCId(), Boolean.TRUE);
				}
			}
		}

		// map1 map0 中不存在的 即 新建
		final Map<Long, Boolean> map1 = new HashMap<Long, Boolean>();

		List<ExpertJobCat> expertJobCatList = expert.getExpertJobCatList();

		// 存在擅长模块
		if (expertJobCatList != null && expertJobCatList.size() > 0) {
			for (ExpertJobCat detail : expertJobCatList) {
				Long key = detail.getJobCId();
				if (map0.containsKey(key)) {
					map0.remove(key);
				} else {
					map1.put(key, Boolean.TRUE);
				}
			}
		}

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				Long expertId = expert.getExpertId();

				if (expertId == null) {
					// TODO
					expert.setType(IExpertService.APPLIED);

					try {
						expertDao.createExpert(expert);
						expertId = expert.getExpertId();
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(expert), e);
						ts.setRollbackOnly();

						result.setCode("专家信息创建失败，请稍后再试");
						return result;
					}
				} else {
					try {
						int c = expertDao.updateExpert(expert);
						if (c != 1) {
							ts.setRollbackOnly();

							result.setCode("专家信息修改失败，请稍后再试");
							return result;
						}
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(expert), e);
						ts.setRollbackOnly();

						result.setCode("专家信息修改失败，请稍后再试");
						return result;
					}
				}

				// 删除 擅长模块
				if (map0.size() > 0) {
					String[] codes = new String[map0.size()];
					int i = 0;
					for (Map.Entry<Long, Boolean> m : map0.entrySet()) {
						codes[i++] = m.getKey().toString();
					}

					ExpertJobCat expertJobCat0 = new ExpertJobCat();
					expertJobCat0.setExpertId(expertId);
					expertJobCat0.setModifyUser(expert.getModifyUser());
					expertJobCat0.setCodes(codes);

					try {
						expertJobCatDao.deleteExpertJobCat(expertJobCat0);
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(expertJobCat0), e);
						ts.setRollbackOnly();

						result.setCode("擅长模块信息修改失败，请稍后再试");
						return result;
					}
				}

				// 新建 擅长模块
				if (map1.size() > 0) {
					for (Map.Entry<Long, Boolean> m : map1.entrySet()) {
						ExpertJobCat expertJobCat1 = new ExpertJobCat();
						expertJobCat1.setExpertId(expertId);
						expertJobCat1.setJobCId(m.getKey());
						expertJobCat1.setModifyUser(expert.getModifyUser());

						try {
							expertJobCatDao.createExpertJobCat(expertJobCat1);
						} catch (Exception e) {
							logger.error(LogUtil.parserBean(expertJobCat1), e);
							ts.setRollbackOnly();

							result.setCode("擅长模块信息修改失败，请稍后再试");
							return result;
						}
					}
				}

				result.setCode(expertId.toString());
				result.setResult(true);
				return result;
			}
		});

		if (res.getResult()) {
			// remove cache
			remove(userId, res.getCode());
		}

		return res;
	}

	private List<Expert> getExpertList(Expert expert) {
		try {
			return expertDao.getExpertList(expert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expert), e);
		}

		return null;
	}

	private Expert getExpert(Expert expert) {
		try {
			return expertDao.getExpert(expert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expert), e);
		}

		return null;
	}

	private List<ExpertJobCat> getExpertJobCatList(ExpertJobCat expertJobCat) {
		try {
			return expertJobCatDao.getExpertJobCatList(expertJobCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expertJobCat), e);
		}

		return null;
	}

	private void remove(Long userId, String expertId) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_EXPERT_USER_ID + userId);
		} catch (ServiceException e) {
			logger.error(e);
		}

		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_EXPERT_EXPERT_ID + expertId);
		} catch (ServiceException e) {
			logger.error(e);
		}
	}

}
