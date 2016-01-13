package net.simpleframework.module.common.content.impl;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.IParamsValue;
import net.simpleframework.ado.db.IDbEntityManager;
import net.simpleframework.ado.query.DataQueryUtils;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.content.AbstractComment;
import net.simpleframework.module.common.content.ICommentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractCommentService<T extends AbstractComment> extends
		AbstractDbBeanService<T> implements ICommentService<T> {

	@Override
	public IDataQuery<T> queryComments(final Object contentId, final ColumnData... orderColumns) {
		if (contentId == null) {
			return DataQueryUtils.nullQuery();
		}
		return queryByParams(FilterItems.of("contentId", getIdParam(contentId)), orderColumns);
	}

	@Override
	public IDataQuery<T> queryChildren(final T parent, final ColumnData... orderColumns) {
		if (parent == null) {
			return DataQueryUtils.nullQuery();
		}
		return super.queryChildren(parent, ColumnData.DESC("createDate"));
	}

	protected void updateComments(final T comment, final int i) {
	}

	protected void addUpdateCommentsListener() {
		addListener(new DbEntityAdapterEx<T>() {
			@Override
			public void onBeforeDelete(final IDbEntityManager<T> manager,
					final IParamsValue paramsValue) throws Exception {
				super.onBeforeDelete(manager, paramsValue);
				for (final T comment : coll(manager, paramsValue)) {
					updateComments(comment, -1);
				}
			}

			@Override
			public void onAfterInsert(final IDbEntityManager<T> manager, final T[] beans)
					throws Exception {
				super.onAfterInsert(manager, beans);
				for (final T o : beans) {
					updateComments(o, 0);
				}
			}
		});
	}
}
