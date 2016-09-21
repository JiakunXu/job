package com.jk.jobs.api.expert.bo;

import java.math.BigDecimal;
import java.util.List;

import com.jk.jobs.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Expert extends User {

	private static final long serialVersionUID = -2171323735128136031L;

	private Long expertId;

	private Long userId;

	private String content;

	private BigDecimal price;

	private int score;

	private String type;

	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private List<ExpertJobCat> expertJobCatList;

	private Long jobCId;

	public Long getExpertId() {
		return expertId;
	}

	public void setExpertId(Long expertId) {
		this.expertId = expertId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<ExpertJobCat> getExpertJobCatList() {
		return expertJobCatList;
	}

	public void setExpertJobCatList(List<ExpertJobCat> expertJobCatList) {
		this.expertJobCatList = expertJobCatList;
	}

	public Long getJobCId() {
		return jobCId;
	}

	public void setJobCId(Long jobCId) {
		this.jobCId = jobCId;
	}

}
