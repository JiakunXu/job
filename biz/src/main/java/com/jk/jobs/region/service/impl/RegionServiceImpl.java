package com.jk.jobs.region.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jk.jobs.api.region.IRegionService;
import com.jk.jobs.api.region.bo.Region;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;
import com.jk.jobs.framework.util.LogUtil;
import com.jk.jobs.region.dao.IRegionDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class RegionServiceImpl implements IRegionService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(RegionServiceImpl.class);

	@Resource
	private IRegionDao regionDao;

	@Override
	public List<Region> getRegionList(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}

		Region region = new Region();
		region.setType(type.trim());

		try {
			return regionDao.getRegionList(region);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(region), e);
		}

		return null;
	}

	@Override
	public Region getRegion(String regionId) {
		if (StringUtils.isBlank(regionId)) {
			return null;
		}

		Region region = new Region();

		try {
			region.setRegionId(Long.valueOf(regionId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		try {
			return regionDao.getRegion(region);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(region), e);
		}

		return null;
	}
}
