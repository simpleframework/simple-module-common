package net.simpleframework.module.common.content.impl;

import net.simpleframework.ado.ColumnData;
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
	public IDataQuery<T> queryByContent(final Object content) {
		if (content == null) {
			return DataQueryUtils.nullQuery();
		}
		return query("contentId=? order by createDate desc", content);
	}

	@Override
	public IDataQuery<T> queryChildren(final T parent, final ColumnData... orderColumns) {
		if (parent == null) {
			return DataQueryUtils.nullQuery();
		}
		return super.queryChildren(parent, ColumnData.DESC("createDate"));
	}

	@Override
	public IDataQuery<T> queryAll() {
		return query("1=1 order by createDate desc");
	}

	@Override
	public void onInit() throws Exception {
		addListener(new DbEntityAdapterEx() {
			@SuppressWarnings("unchecked")
			@Override
			public void onBeforeUpdate(final IDbEntityManager<?> manager, final String[] columns,
					final Object[] beans) {
				super.onBeforeUpdate(manager, columns, beans);
				for (final Object o : beans) {
					assertParentId((T) o);
				}
			}
		});
	}
}
