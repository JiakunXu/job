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

		String tel = issue.getTel();
		if (StringUtils.isBlank(tel)) {
			result.setCode("联系方式不能为空");
			return result;
		}

		String content = issue.getContent();
		if (StringUtils.isBlank(content)) {
			result.setCode("问题详情不能为空");
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

		issue.setType(IIssueService.SUBMIT);

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
	public int getIssueCount(Long userId, String type) {
		if (userId == null || StringUtils.isBlank(type)) {
			return 0;
		}

		Issue issue = new Issue();

		if ("U".equals(type)) {
			issue.setUserId(userId);
		} else if ("E".equals(type)) {
			Expert expert = expertService.getExpert(userId);
			if (expert == null) {
				return 0;
			}
			issue.setExpertId(expert.getExpertId());
			issue.setType(IIssueService.SUBMIT);
		} else {
			return 0;
		}

		return getIssueCount(issue);
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
			issue.setType(IIssueService.SUBMIT);
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

	@Override
	public Issue getIssue(Long userId, String issueId) {
		if (userId == null || StringUtils.isBlank(issueId)) {
			return null;
		}

		try {
			return getIssue(userId, Long.valueOf(issueId));
		} catch (NumberFormatException e) {
			logger.error(e);
		}

		return null;
	}

	/**
	 * 
	 * @param userId
	 * @param issueId
	 * @return
	 */
	private Issue getIssue(Long userId, Long issueId) {
		Issue issue = new Issue();
		issue.setUserId(userId);
		issue.setIssueId(issueId);

		return getIssue(issue);
	}

	@Override
	public BooleanResult revoke(Long userId, String issueId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(issueId)) {
			result.setCode("问题信息不能为空");
			return result;
		}

		Issue issue = new Issue();
		issue.setUserId(userId);

		try {
			issue.setIssueId(Long.valueOf(issueId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("问题信息不能为空");
			return result;
		}

		issue.setType(IIssueService.REVOKE);
		issue.setModifyUser(userId.toString());

		Issue isseu = getIssue(userId, issue.getIssueId());

		if (isseu == null) {
			result.setCode("问题信息不能为空");
			return result;
		}

		if (!IIssueService.SUBMIT.equals(isseu.getType()) && !IIssueService.IGNORE.equals(isseu.getType())) {
			result.setCode("当前咨询问题已撤销或删除");
			return result;
		}

		try {
			int c = issueDao.updateIssue(issue);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("问题撤销失败，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);

			result.setCode("问题撤销失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult delete(Long userId, String issueId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(issueId)) {
			result.setCode("问题信息不能为空");
			return result;
		}

		Issue issue = new Issue();
		issue.setUserId(userId);

		try {
			issue.setIssueId(Long.valueOf(issueId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("问题信息不能为空");
			return result;
		}

		issue.setType(IIssueService.DELETE);
		issue.setModifyUser(userId.toString());

		Issue isseu = getIssue(userId, issue.getIssueId());

		if (isseu == null) {
			result.setCode("问题信息不能为空");
			return result;
		}

		if (!IIssueService.REVOKE.equals(isseu.getType())) {
			result.setCode("当前咨询问题已删除");
			return result;
		}

		try {
			int c = issueDao.updateIssue(issue);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("问题删除失败，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);

			result.setCode("问题删除失败，请稍后再试");
		}

		return result;
	}

	// >>>>>>>>>>以下是专家相关问题<<<<<<<<<<

	@Override
	public BooleanResult ignore(Long userId, String issueId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("专家信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(issueId)) {
			result.setCode("问题信息不能为空");
			return result;
		}

		Expert expert = expertService.getExpert(userId);
		if (expert == null) {
			result.setCode("专家信息不能为空");
			return result;
		}

		Issue issue = new Issue();
		issue.setExpertId(expert.getExpertId());

		try {
			issue.setIssueId(Long.valueOf(issueId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("问题信息不能为空");
			return result;
		}

		issue.setType(IIssueService.IGNORE);
		issue.setModifyUser(userId.toString());

		Issue isseu = getIssue(userId, issue.getIssueId());

		if (isseu == null) {
			result.setCode("问题信息不能为空");
			return result;
		}

		if (!IIssueService.SUBMIT.equals(isseu.getType())) {
			result.setCode("当前咨询问题已撤销或删除");
			return result;
		}

		try {
			int c = issueDao.updateIssue(issue);
			if (c == 1) {
				result.setResult(true);
			} else {
				result.setCode("问题忽略失败，请稍后再试");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);

			result.setCode("问题忽略失败，请稍后再试");
		}

		return result;
	}

	/**
	 * 
	 * @param issue
	 * @return
	 */
	private int getIssueCount(Issue issue) {
		try {
			return issueDao.getIssueCount(issue);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);
		}

		return 0;
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

	/**
	 * 
	 * @param issue
	 * @return
	 */
	private Issue getIssue(Issue issue) {
		try {
			return issueDao.getIssue(issue);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(issue), e);
		}

		return null;
	}

}
