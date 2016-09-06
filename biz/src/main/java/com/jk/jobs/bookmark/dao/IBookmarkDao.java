package com.jk.jobs.bookmark.dao;

import java.util.List;

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
	int getBookmarkCount(Bookmark bookmark);

	/**
	 * 
	 * @param bookmark
	 * @return
	 */
	List<Bookmark> getBookmarkList(Bookmark bookmark);

	/**
	 * 
	 * @param bookmark
	 * @return
	 */
	int createBookmark(Bookmark bookmark);

	/**
	 * 
	 * @param bookmark
	 * @return
	 */
	int deleteBookmark(Bookmark bookmark);

}
