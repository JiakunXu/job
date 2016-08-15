package com.jk.jobs.region.dao;

import java.util.List;

import com.jk.jobs.api.region.bo.Region;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IRegionDao {

	/**
	 * 
	 * @param region
	 * @return
	 */
	List<Region> getRegionList(Region region);

	/**
	 * 
	 * @param region
	 * @return
	 */
	Region getRegion(Region region);

}
