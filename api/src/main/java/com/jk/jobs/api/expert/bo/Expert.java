package com.jk.jobs.api.expert.bo;

import java.math.BigDecimal;

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

	private BigDecimal price;

	private String remark;

	private int score;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
