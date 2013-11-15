package net.simpleframework.module.common.team.impl;

import net.simpleframework.ado.query.DataQueryUtils;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.ctx.permission.PermissionUser;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.team.ITeamService;
import net.simpleframework.module.common.team.Team;
import net.simpleframework.module.common.team.TeamRole;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractTeamService<T extends Team> extends AbstractDbBeanService<T>
		implements ITeamService<T> {

	protected boolean isMemeber(final Object owner, final PermissionUser user, final String role) {
		return user.isManager()
				|| count("ownerId=? and role=? and userId=?", owner, role, user.getId()) > 0;
	}

	@Override
	public IDataQuery<T> queryByOwner(final Object owner, final TeamRole role) {
		if (owner == null) {
			return DataQueryUtils.nullQuery();
		}
		if (role == null) {
			return query("ownerId=? order by createDate desc", owner);
		} else {
			return query("ownerId=? and role=? order by createDate desc", owner, role.getName());
		}
	}

	@Override
	public IDataQuery<T> queryByOwner(final Object owner) {
		return queryByOwner(owner, null);
	}
}