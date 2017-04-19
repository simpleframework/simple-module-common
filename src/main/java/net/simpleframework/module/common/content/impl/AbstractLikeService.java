package net.simpleframework.module.common.content.impl;

import java.util.Date;

import net.simpleframework.ado.IParamsValue;
import net.simpleframework.ado.bean.AbstractIdBean;
import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.ado.db.IDbEntityManager;
import net.simpleframework.common.ID;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.content.ILikeService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractLikeService<T extends AbstractIdBean, M extends AbstractUserAwareBean>
		extends AbstractDbBeanService<M> implements ILikeService<T, M> {

	protected abstract void updateStats(final M m, final Number i);

	@Override
	public M getLike(final T comment, final Object user) {
		return comment == null ? null
				: getBean("commentid=? and userid=?", comment.getId(), getIdParam(user));
	}

	@SuppressWarnings("unchecked")
	@Override
	public M addLike(final T comment, final ID userId) {
		final M like = createBean();
		like.setUserId(userId);
		like.setCreateDate(new Date());
		setLike(like, comment);
		insert(like);
		return like;
	}

	protected abstract void setLike(final M like, final T comment);

	@Override
	public void onInit() throws Exception {
		super.onInit();

		addListener(new DbEntityAdapterEx<M>() {
			@Override
			public void onBeforeDelete(final IDbEntityManager<M> manager,
					final IParamsValue paramsValue) throws Exception {
				super.onBeforeDelete(manager, paramsValue);
				for (final M m : coll(manager, paramsValue)) {
					updateStats(m, -1);
				}
			}

			@Override
			public void onAfterInsert(final IDbEntityManager<M> manager, final M[] beans)
					throws Exception {
				super.onAfterInsert(manager, beans);
				for (final M m : beans) {
					updateStats(m, 0);
				}
			}
		});
	}
}