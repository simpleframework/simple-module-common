package net.simpleframework.module.common.plugin;

import net.simpleframework.common.object.ObjectEx;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class ModulePluginFactory extends ObjectEx {

	public static <T extends IModulePlugin> T get(final Class<T> markClass) {
		return singleton(markClass);
	}
}