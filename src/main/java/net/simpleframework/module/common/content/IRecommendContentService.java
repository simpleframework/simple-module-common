package net.simpleframework.module.common.content;

import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.TimePeriod;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IRecommendContentService<T extends AbstractRecommendContentBean> extends
		IContentService<T> {
	/**
	 * 查询推荐内容
	 * 
	 * @param category
	 * @param timePeriod
	 * @return
	 */
	IDataQuery<T> queryRecommendationBeans(AbstractCategoryBean category, TimePeriod timePeriod);

	void doUnRecommendationTask();
}
