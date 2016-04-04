package net.simpleframework.module.common.content.impl;

import static net.simpleframework.common.I18n.$m;

import java.util.Date;
import java.util.Map;

import net.simpleframework.ado.IParamsValue;
import net.simpleframework.ado.db.IDbEntityManager;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ado.trans.TransactionVoidCallback;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.ctx.task.ExecutorRunnableEx;
import net.simpleframework.ctx.task.ITaskExecutor;
import net.simpleframework.module.common.content.AbstractRecommend;
import net.simpleframework.module.common.content.AbstractRecommend.ERecommendStatus;
import net.simpleframework.module.common.content.ContentException;
import net.simpleframework.module.common.content.IRecommendService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractRecommendService<T extends AbstractRecommend> extends
		AbstractDbBeanService<T> implements IRecommendService<T> {

	protected abstract Object getContent(T t);

	protected abstract void _doStatus(T r, ERecommendStatus status);

	@Override
	public void doAbort(final T recommend) {
		_doStatus(recommend, ERecommendStatus.abort);
	}

	protected void _doRecommendTask(final T r) {
		if (r.getStatus() == ERecommendStatus.ready) {
			final Date startDate = r.getDstartDate();
			final Date n = new Date();
			if (startDate == null || startDate.before(n)) {
				// 如果存在运行的推荐，则放弃
				final T r2 = queryRunningRecommend(getContent(r));
				if (r2 != null) {
					_doStatus(r2, ERecommendStatus.abort);
				}
				_doStatus(r, ERecommendStatus.running);
			}
		}
	}

	public void doRecommend_inTran(final T r) {
		doExecuteTransaction(new TransactionVoidCallback() {
			@Override
			protected void doTransactionVoidCallback() throws Throwable {
				if (r.getStatus() == ERecommendStatus.ready) {
					_doRecommendTask(r);
				} else {
					final Date endDate = r.getDendDate();
					if (endDate != null && endDate.before(new Date())) {
						_doStatus(r, ERecommendStatus.complete);
					}
				}
			}
		});
	}

	@Override
	public void onInit() throws Exception {
		super.onInit();

		final ITaskExecutor taskExecutor = getTaskExecutor();
		taskExecutor.addScheduledTask(new ExecutorRunnableEx(getBeanClass().getSimpleName()
				.toLowerCase() + "_check") {
			@Override
			protected void task(final Map<String, Object> cache) throws Exception {
				final IDataQuery<T> dq = query("status=? or status=?", ERecommendStatus.ready,
						ERecommendStatus.running).setFetchSize(0);
				T r;
				while ((r = dq.next()) != null) {
					doRecommend_inTran(r);
				}
			}
		});

		addListener(new DbEntityAdapterEx<T>() {
			@Override
			public void onBeforeDelete(final IDbEntityManager<T> manager,
					final IParamsValue paramsValue) throws Exception {
				super.onBeforeDelete(manager, paramsValue);
				for (final T r : coll(manager, paramsValue)) {
					if (r.getStatus() == ERecommendStatus.running) {
						throw ContentException.of($m("AbstractRecommendService.0"));
					}
				}
			}

			@Override
			public void onAfterInsert(final IDbEntityManager<T> manager, final T[] beans)
					throws Exception {
				super.onAfterInsert(manager, beans);
				for (final T r : beans) {
					_doRecommendTask(r);
				}
			}
		});
	}
}
