package net.simpleframework.module.common.content;

import java.util.Date;

import net.simpleframework.ado.ColumnMeta;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractRecommendContentBean extends AbstractContentBean {
	/* 推荐级别 >=0, 0表示取消推荐 */
	@ColumnMeta(columnText = "#(AbstractContentBean.2)")
	private int recommendation;

	/* 推荐日期 */
	private Date recommendationDate;

	/* 推荐时长, 单位s. 当超出推荐推荐时长后, recommendation=0 */
	private int recommendationDuration;

	public int getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(final int recommendation) {
		this.recommendation = recommendation;
	}

	public Date getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(final Date recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	public int getRecommendationDuration() {
		return recommendationDuration;
	}

	public void setRecommendationDuration(final int recommendationDuration) {
		this.recommendationDuration = recommendationDuration;
	}
}
