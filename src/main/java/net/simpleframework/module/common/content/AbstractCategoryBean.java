package net.simpleframework.module.common.content;

import java.util.Date;

import net.simpleframework.ado.bean.AbstractTextDescriptionBean;
import net.simpleframework.ado.bean.INameBeanAware;
import net.simpleframework.ado.bean.IOrderBeanAware;
import net.simpleframework.ado.bean.ITreeBeanAware;
import net.simpleframework.common.ID;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractCategoryBean extends AbstractTextDescriptionBean implements
		INameBeanAware, ITreeBeanAware, IOrderBeanAware {
	/* 父id */
	private ID parentId;

	/* 名称，唯一 */
	private String name;

	/* 标识 */
	private ECategoryMark mark;

	/* 创建日期 */
	private Date createDate;

	/* 创建人 */
	private ID userId;

	/* 排序字段 */
	private long oorder;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public ECategoryMark getMark() {
		return mark == null ? ECategoryMark.normal : mark;
	}

	public void setMark(final ECategoryMark mark) {
		this.mark = mark;
	}

	public Date getCreateDate() {
		if (createDate == null) {
			createDate = new Date();
		}
		return createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public ID getUserId() {
		return userId;
	}

	public void setUserId(final ID userId) {
		this.userId = userId;
	}

	@Override
	public ID getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(final ID parentId) {
		this.parentId = parentId;
	}

	@Override
	public long getOorder() {
		return oorder;
	}

	@Override
	public void setOorder(final long oorder) {
		this.oorder = oorder;
	}
}
