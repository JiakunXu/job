package com.jk.jobs.issue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.expert.IExpertService;
import com.jk.jobs.api.expert.bo.Expert;
import com.jk.jobs.api.issue.IIssueService;
import com.jk.jobs.api.issue.bo.Issue;
import com.jk.jobs.api.user.IUserService;
import com.jk.jobs.api.user.bo.User;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.issue.dao.IIssueDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class IssueServiceImpl implements IIssueService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(IssueServiceImpl.class);

	@Resource
	private IExpertService expertService;

	@Resource
	private IUserService userService;

	@Resource
	private IIssueDao issueDao;

	/**
	 * 
	 * @param issue
	 * @return
	 */
	private BooleanResult validate(Issue issue) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (issue == null) {
			result.setCode("咨询信息不能为空");
			return result;
		}

		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult submit(Long userId, Issue issue) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		result = validate(issue);
		if (!result.getResult()) {
			return result;
		}

		result.setResult(false);

		issue.setUserId(userId);
		issue.setModifyUser(userId.toString());

		try {
			issueDao.createIssue(issue);
			result.setCode(issue.getIssueId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);

			result.setCode("咨询信息创建失败，请稍后再试");
		}

		return result;
	}

	@Override
	public List<Issue> getIssueList(Long userId, String type) {
		if (userId == null || StringUtils.isBlank(type)) {
			return null;
		}

		Issue issue = new Issue();

		if ("U".equals(type)) {
			issue.setUserId(userId);
		} else if ("E".equals(type)) {
			Expert expert = expertService.getExpert(userId);
			if (expert == null) {
				return null;
			}
			issue.setExpertId(expert.getExpertId());
		} else {
			return null;
		}

		List<Issue> issueList = getIssueList(issue);

		if (issueList == null || issueList.size() == 0) {
			return null;
		}

		for (Issue isseu : issueList) {
			User user = userService.getUser(isseu.getUserId());
			if (user != null) {
				isseu.setUserName(user.getUserName());
			}

			Expert expert = expertService.getExpert(isseu.getExpertId().toString());
			if (expert != null) {
				isseu.setExpertName(expert.getUserName());
			}
		}

		return issueList;
	}

	/**
	 * 
	 * @param issue
	 * @return
	 */
	private List<Issue> getIssueList(Issue issue) {
		try {
			return issueDao.getIssueList(issue);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);
		}

		return null;
	}

}
