package com.jk.jobs.api.region;

import java.util.List;

import com.jk.jobs.api.region.bo.Region;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IRegionService {

	/**
	 * 
	 * @param type
	 * @return
	 */
	List<Region> getRegionList(String type);

	/**
	 * 
	 * @param regionId
	 * @return
	 */
	Region getRegion(String regionId);

}
