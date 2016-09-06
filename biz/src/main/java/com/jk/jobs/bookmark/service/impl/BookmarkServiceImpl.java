package com.jk.jobs.bookmark.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.bookmark.IBookmarkService;
import com.jk.jobs.api.bookmark.bo.Bookmark;
import com.jk.jobs.bookmark.dao.IBookmarkDao;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class BookmarkServiceImpl implements IBookmarkService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(BookmarkServiceImpl.class);

	@Resource
	private IBookmarkDao bookmarkDao;

	@Override
	public BooleanResult save(Long userId, String jobId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Bookmark bookmark = new Bookmark();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		bookmark.setUserId(userId);
		bookmark.setModifyUser(userId.toString());

		if (StringUtils.isBlank(jobId)) {
			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			bookmark.setJobId(Long.valueOf(jobId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("项目信息不能为空");
			return result;
		}

		try {
			bookmarkDao.createBookmark(bookmark);
			result.setCode(bookmark.getId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bookmark), e);

			result.setCode("收藏信息创建失败，请稍后再试");
		}

		return result;
	}

}
