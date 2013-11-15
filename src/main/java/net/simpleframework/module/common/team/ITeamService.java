package net.simpleframework.module.common.team;

import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface ITeamService<T extends Team> extends IDbBeanService<T> {

	/**
	 * 获取注册的团队角色
	 * 
	 * @return
	 */
	TeamRole[] getTeamRoles();

	/**
	 * 根据名称获取团队角色
	 * 
	 * @param name
	 * @return
	 */
	TeamRole getTeamRole(String name);

	/**
	 * 获取拥有者的团队
	 * 
	 * @param owner
	 * @param role
	 * @return
	 */
	IDataQuery<T> queryByOwner(Object owner, TeamRole role);

	IDataQuery<T> queryByOwner(Object owner);
}
