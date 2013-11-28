package net.simpleframework.module.common.content.impl;

import static net.simpleframework.common.I18n.$m;

import java.util.Calendar;
import java.util.Date;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.EFilterRelation;
import net.simpleframework.ado.EOrder;
import net.simpleframework.ado.FilterItem;
import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.Convert;
import net.simpleframework.common.TimePeriod;
import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.ctx.permission.LoginUser;
import net.simpleframework.ctx.permission.PermissionUser;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.DescriptionLocalUtils;
import net.simpleframework.module.common.content.AbstractCategoryBean;
import net.simpleframework.module.common.content.AbstractContentBean;
import net.simpleframework.module.common.content.EContentStatus;
import net.simpleframework.module.common.content.IContentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractContentService<T extends AbstractContentBean> extends
		AbstractDbBeanService<T> implements IContentService<T> {

	protected abstract ColumnData[] getDefaultOrderColumns();

	@Override
	public IDataQuery<T> queryByParams(final FilterItems params) {
		return queryByParams(params, getDefaultOrderColumns());
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category,
			final EContentStatus status, final TimePeriod timePeriod, final FilterItems filterItems) {
		return queryBeans(category, status, timePeriod, filterItems, getDefaultOrderColumns());
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean oCategory, final EContentStatus status) {
		return queryBeans(oCategory, status, null, null);
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category,
			final TimePeriod timePeriod, final ColumnData... orderColumns) {
		return queryBeans(category, EContentStatus.publish, timePeriod, null, orderColumns);
	}

	@Override
	public IDataQuery<T> queryBeans(final AbstractCategoryBean category) {
		return queryBeans(category, (TimePeriod) null, getDefaultOrderColumns());
	}

	private final ColumnData[] RECOMMENDATION_ORDER_COLUMNS = ArrayUtils.add(
			new ColumnData[] { new ColumnData("recommendation").setOrder(EOrder.desc) },
			ColumnData.class, getDefaultOrderColumns());

	@Override
	public IDataQuery<T> queryRecommendationBeans(final AbstractCategoryBean category,
			final TimePeriod timePeriod) {
		return queryBeans(category, EContentStatus.publish, timePeriod,
				FilterItems.of(new FilterItem("recommendation", EFilterRelation.gt, 0)),
				RECOMMENDATION_ORDER_COLUMNS);
	}

	@Override
	public IDataQuery<T> queryMyBeans(final Object user) {
		return queryBeans(
				null,
				null,
				null,
				FilterItems.of().addEqualItem("userId",
						(user instanceof PermissionUser) ? ((PermissionUser) user).getId() : user));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doUnRecommendationTask() {
		final IDataQuery<T> dq = queryRecommendationBeans(null, null);
		T t;
		LoginUser.setAdmin();
		while ((t = dq.next()) != null) {
			final Date rDate = t.getRecommendationDate();
			final int dur = t.getRecommendationDuration();
			if (rDate != null && dur > 0) {
				final Calendar cal = Calendar.getInstance();
				cal.setTime(rDate);
				cal.add(Calendar.SECOND, dur);
				if (cal.getTime().before(new Date())) {
					t.setRecommendation(0);
					DescriptionLocalUtils.set(t,
							$m("AbstractContentService.0", Convert.toDateString(rDate), dur));
					update(new String[] { "recommendation" }, t);
				}
			}
		}
	}
}
