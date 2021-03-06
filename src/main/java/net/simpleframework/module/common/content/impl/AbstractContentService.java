package net.simpleframework.module.common.content.impl;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.TimePeriod;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.content.AbstractCategoryBean;
import net.simpleframework.module.common.content.AbstractContentBean;
import net.simpleframework.module.common.content.AbstractContentBean.EContentStatus;
import net.simpleframework.module.common.content.IContentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractContentService<T extends AbstractContentBean>
		extends AbstractDbBeanService<T> implements IContentService<T> {

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category, final EContentStatus status,
			final TimePeriod timePeriod, final FilterItems filterItems) {
		return queryBeans(category, status, timePeriod, filterItems, getDefaultOrderColumns());
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean oCategory,
			final EContentStatus status) {
		return queryBeans(oCategory, status, null, null);
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category, final TimePeriod timePeriod,
			final ColumnData... orderColumns) {
		return queryBeans(category, EContentStatus.publish, timePeriod, null, orderColumns);
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category) {
		return queryBeans(category, (TimePeriod) null, getDefaultOrderColumns());
	}
}
