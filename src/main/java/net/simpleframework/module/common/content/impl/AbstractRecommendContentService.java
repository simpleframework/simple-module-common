package net.simpleframework.module.common.content.impl;

import static net.simpleframework.common.I18n.$m;

import java.util.Calendar;
import java.util.Date;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.EFilterRelation;
import net.simpleframework.ado.FilterItem;
import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.Convert;
import net.simpleframework.common.TimePeriod;
import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.ctx.permission.LoginUser;
import net.simpleframework.module.common.DescriptionLogUtils;
import net.simpleframework.module.common.content.AbstractCategoryBean;
import net.simpleframework.module.common.content.AbstractRecommendContentBean;
import net.simpleframework.module.common.content.EContentStatus;
import net.simpleframework.module.common.content.IRecommendContentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractRecommendContentService<T extends AbstractRecommendContentBean>
		extends AbstractContentService<T> implements IRecommendContentService<T> {
	private final ColumnData[] RECOMMENDATION_ORDER_COLUMNS = ArrayUtils.add(
			new ColumnData[] { ColumnData.DESC("recommendation") }, ColumnData.class,
			getDefaultOrderColumns());

	@Override
	public IDataQuery<T> queryRecommendationBeans(final AbstractCategoryBean category,
			final TimePeriod timePeriod) {
		return queryBeans(category, EContentStatus.publish, timePeriod,
				FilterItems.of(new FilterItem("recommendation", EFilterRelation.gt, 0)),
				RECOMMENDATION_ORDER_COLUMNS);
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
					DescriptionLogUtils.set(t,
							$m("AbstractContentService.0", Convert.toDateString(rDate), dur));
					update(new String[] { "recommendation" }, t);
				}
			}
		}
	}
}
