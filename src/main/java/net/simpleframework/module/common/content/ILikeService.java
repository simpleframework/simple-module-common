package net.simpleframework.module.common.content;

import net.simpleframework.ado.bean.AbstractIdBean;
import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.common.ID;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface ILikeService<T extends AbstractIdBean, M extends AbstractUserAwareBean>
		extends IDbBeanService<M> {

	/**
	 * 获取"赞"对象
	 * 
	 * @param obj
	 * @param userId
	 * @return
	 */
	M getLike(T obj, Object userId);

	M addLike(T obj, ID userId);
}
