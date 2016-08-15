package com.jk.jobs.region.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.region.IRegionService;
import com.jk.jobs.api.region.bo.Region;
import com.jk.jobs.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class RegionAction extends BaseAction {

	private static final long serialVersionUID = -2185782895491930489L;

	@Resource
	private IRegionService regionService;

	private List<Region> regionList;

	private String regionId;

	/**
	 * 
	 * @return
	 */
	public String index() {
		regionList = regionService.getRegionList("P");

		return SUCCESS;
	}

	public String select() {
		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_REGION", regionService.getRegion(regionId));

		return SUCCESS;
	}

	public List<Region> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

}
