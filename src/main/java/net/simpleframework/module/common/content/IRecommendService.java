package net.simpleframework.module.common.content;

import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IRecommendService<T extends AbstractRecommend> extends IDbBeanService<T> {

	/**
	 * @param content
	 * 
	 * @return
	 */
	IDataQuery<T> queryRecommends(Object content);

	/**
	 * 获取正在运行的推荐
	 * 
	 * @param news
	 * @return
	 */
	T queryRunningRecommend(Object content);

	void doAbort(T recommend);
}
