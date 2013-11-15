package net.simpleframework.module.common.team;

import net.simpleframework.common.object.TextNamedObject;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public class TeamRole extends TextNamedObject<TeamRole> {

	public static TeamRole of(final String name, final String text) {
		return new TeamRole().setName(name).setText(text);
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public TeamRole setDescription(final String description) {
		this.description = description;
		return this;
	}
}
