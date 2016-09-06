package com.jk.jobs.bookmark.dao;

import com.jk.jobs.api.bookmark.bo.Bookmark;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IBookmarkDao {

	/**
	 * 
	 * @param bookmark
	 * @return
	 */
	int createBookmark(Bookmark bookmark);

}
