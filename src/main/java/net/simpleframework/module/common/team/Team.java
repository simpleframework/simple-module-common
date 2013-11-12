package net.simpleframework.module.common.team;

import net.simpleframework.ado.ColumnMeta;
import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.ado.db.common.EntityInterceptor;
import net.simpleframework.common.ID;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
@EntityInterceptor(listenerTypes = { "net.simpleframework.module.log.EntityDeleteLogAdapter" })
public abstract class Team extends AbstractUserAwareBean {

	/* 团队拥有者 */
	private ID ownerId;

	/* 成员角色 */
	private String role;

	/* 描述 */
	@ColumnMeta(columnText = "#(Description)")
	private String description;

	public ID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(final ID ownerId) {
		this.ownerId = ownerId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	// @Override
	// public String toString() {
	// return MVCContext.permission().getUser(getUserId()) + " [" + role + "]";
	// }
}
