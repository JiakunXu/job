package com.jk.jobs.bookmark.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.bookmark.IBookmarkService;
import com.jk.jobs.api.bookmark.bo.Bookmark;
import com.jk.jobs.api.cache.IMemcachedCacheService;
import com.jk.jobs.api.job.IJobService;
import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.bookmark.dao.IBookmarkDao;
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
public class BookmarkServiceImpl implements IBookmarkService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(BookmarkServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IJobService jobService;

	@Resource
	private IBookmarkDao bookmarkDao;

	@Override
	public int getBookmarkCount(Long jobId) {
		if (jobId == null) {
			return 0;
		}

		Long key = jobId;

		Integer count = null;

		try {
			count = (Integer) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_BOOKMARK_JOB_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_BOOKMARK_JOB_ID + key, e);
		}

		if (count != null) {
			return count;
		}

		count = getBookmarkCount(null, jobId);

		if (count == null) {
			return 0;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_BOOKMARK_JOB_ID + key, count);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_BOOKMARK_JOB_ID + key, e);
		}

		return count;
	}

	@Override
	public int getBookmarkCount(Long userId, Long jobId) {
		if (jobId == null) {
			return 0;
		}

		Bookmark bookmark = new Bookmark();
		bookmark.setUserId(userId);
		bookmark.setJobId(jobId);

		try {
			return bookmarkDao.getBookmarkCount(bookmark);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bookmark), e);
		}

		return 0;
	}

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

			// remove cache
			remove(jobId.trim());
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bookmark), e);

			result.setCode("收藏信息创建失败，请稍后再试");
		}

		return result;
	}

	@Override
	public BooleanResult cancel(Long userId, String jobId) {
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
			bookmarkDao.deleteBookmark(bookmark);
			result.setResult(true);

			// remove cache
			remove(jobId.trim());
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bookmark), e);

			result.setCode("收藏信息取消失败，请稍后再试");
		}

		return result;
	}

	@Override
	public List<Job> getJobList(Long userId) {
		if (userId == null) {
			return null;
		}

		Bookmark bookmark = new Bookmark();
		bookmark.setUserId(userId);

		List<Bookmark> bookmarkList = getBookmarkList(bookmark);

		if (bookmarkList == null || bookmarkList.size() == 0) {
			return null;
		}

		Map<Long, Bookmark> map = new HashMap<Long, Bookmark>();
		String[] jobId = new String[bookmarkList.size()];
		int i = 0;

		for (Bookmark bm : bookmarkList) {
			map.put(bm.getJobId(), bm);
			jobId[i++] = bm.getJobId().toString();
		}

		return jobService.getJobList(jobId);
	}

	private List<Bookmark> getBookmarkList(Bookmark bookmark) {
		try {
			return bookmarkDao.getBookmarkList(bookmark);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(bookmark), e);
		}

		return null;
	}

	private void remove(String key) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_BOOKMARK_JOB_ID + key);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
