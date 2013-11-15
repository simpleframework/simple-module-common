package net.simpleframework.module.common.content;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ctx.common.bean.TimePeriod;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IContentService<T extends AbstractContentBean> extends IDbBeanService<T> {

	/**
	 * 按指定条件查找
	 * 
	 * @param category
	 *           类目
	 * @param status
	 *           状态
	 * @param timePeriod
	 *           时间范围
	 * @param filterItems
	 *           其它条件
	 * @param orderColumns
	 *           排序
	 * @return
	 */
	IDataQuery<T> queryBeans(AbstractCategoryBean category, EContentStatus status,
			TimePeriod timePeriod, FilterItems filterItems, ColumnData... orderColumns);

	IDataQuery<T> queryBeans(AbstractCategoryBean category, EContentStatus status,
			TimePeriod timePeriod, FilterItems filterItems);

	/**
	 * 按类目查找
	 * 
	 * @param category
	 * @return
	 */
	IDataQuery<T> queryBeans(AbstractCategoryBean category);

	IDataQuery<T> queryBeans(AbstractCategoryBean category, TimePeriod timePeriod,
			ColumnData... orderColumns);

	IDataQuery<T> queryBeans(AbstractCategoryBean oCategory, EContentStatus status);

	/**
	 * 查询推荐内容
	 * 
	 * @param category
	 * @param timePeriod
	 * @return
	 */
	IDataQuery<T> queryRecommendationBeans(AbstractCategoryBean category, TimePeriod timePeriod);

	/**
	 * 查询我的内容
	 * 
	 * @param user
	 * @return
	 */
	IDataQuery<T> queryMyBeans(Object user);
}
