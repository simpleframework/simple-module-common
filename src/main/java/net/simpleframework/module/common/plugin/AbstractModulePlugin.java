package net.simpleframework.module.common.plugin;

import net.simpleframework.common.Convert;
import net.simpleframework.common.object.ObjectEx;
import net.simpleframework.common.object.ObjectUtils;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractModulePlugin extends ObjectEx implements IModulePlugin {

	private int mark;

	private String text;

	@Override
	public int getMark() {
		if (mark == 0) {
			mark = Convert.toInt(ObjectUtils.hashStr(getClass().getName()));
		}
		return mark;
	}

	public AbstractModulePlugin setMark(final int mark) {
		this.mark = mark;
		return this;
	}

	@Override
	public String getText() {
		return text;
	}

	public AbstractModulePlugin setText(final String text) {
		this.text = text;
		return this;
	}

	@Override
	public int getOrder() {
		return getMark();
	}

	@Override
	public String toString() {
		return getText();
	}
}
