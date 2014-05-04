package net.simpleframework.module.common.content;

import java.util.Date;

import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.ado.bean.IDescriptionBeanAware;
import net.simpleframework.common.ID;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public class RecommendBean extends AbstractUserAwareBean implements IDescriptionBeanAware {
	/* 拥有者id */
	private ID contentId;

	/* 推荐级别 >=0, 0表示取消推荐 */
	private int level;

	/* 设计的开始时间和结束时间 */
	private Date dstartDate, dendDate;

	/* 推荐状态 */
	private ERecommendStatus status;

	private String description;

	public ID getContentId() {
		return contentId;
	}

	public void setContentId(final ID contentId) {
		this.contentId = contentId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	public Date getDstartDate() {
		return dstartDate;
	}

	public void setDstartDate(final Date dstartDate) {
		this.dstartDate = dstartDate;
	}

	public Date getDendDate() {
		return dendDate;
	}

	public void setDendDate(final Date dendDate) {
		this.dendDate = dendDate;
	}

	public ERecommendStatus getStatus() {
		return status;
	}

	public void setStatus(final ERecommendStatus status) {
		this.status = status;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	private static final long serialVersionUID = 7046191368624045451L;
}
