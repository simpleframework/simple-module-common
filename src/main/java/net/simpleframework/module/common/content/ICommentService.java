package net.simpleframework.module.common.content;

import net.simpleframework.ado.ColumnData;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.ID;
import net.simpleframework.ctx.service.ado.IUserBeanServiceAware;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface ICommentService<T extends AbstractComment>
		extends IDbBeanService<T>, IUserBeanServiceAware<T> {

	/**
	 * 获取文档的评论列表，parentId=null
	 * 
	 * @param content
	 * @return
	 */
	IDataQuery<T> queryComments(Object contentId, ID userId, ColumnData... orderColumns);

	IDataQuery<T> queryComments(Object contentId, ColumnData... orderColumns);
}
