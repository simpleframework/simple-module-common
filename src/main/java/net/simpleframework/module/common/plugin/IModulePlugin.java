package net.simpleframework.module.common.plugin;

import net.simpleframework.common.object.IObjectOrderAware;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IModulePlugin extends IObjectOrderAware {

	/**
	 * 唯一标记
	 * 
	 * @return
	 */
	int getMark();

	/**
	 * 显示名称
	 * 
	 * @return
	 */
	String getText();
}
