package com.jk.jobs.expert.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.expert.dao.IExpertDao;
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
	private IUserService userService;

	@Resource
	private IExpertDao expertDao;

	@Override
	public List<Expert> getExpertList() {
		List<Expert> expertList = getExpertList(new Expert());

		if (expertList == null || expertList.size() == 0) {
			return null;
		}

		for (Expert expert : expertList) {
			User user = userService.getUser(expert.getUserId());
			if (user == null) {
				continue;
			}

			expert.setUserName(user.getUserName());
		}

		return expertList;
	}

	private List<Expert> getExpertList(Expert expert) {
		try {
			return expertDao.getExpertList(expert);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(expert), e);
		}

		return null;
	}

}
