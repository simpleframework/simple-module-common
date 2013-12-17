package net.simpleframework.module.common.plugin;

import static net.simpleframework.common.I18n.$m;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import net.simpleframework.common.object.ObjectEx;
import net.simpleframework.common.object.ObjectUtils;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class ModulePluginRegistry<T extends IModulePlugin> extends ObjectEx {

	private Map<Integer, Class<T>> registryCache;
	private Vector<T> all;
	{
		registryCache = new ConcurrentHashMap<Integer, Class<T>>();
		all = new Vector<T>();
	}

	@SuppressWarnings("unchecked")
	public void registPlugin(final Class<? extends IModulePlugin> pluginClass) {
		final IModulePlugin plugin = ModulePluginFactory.get(pluginClass);
		final int mark = plugin.getMark();
		if (registryCache.containsKey(mark) && !plugin.getClass().isAssignableFrom(pluginClass)) {
			throw ModulePluginException.of($m("ModulePluginRegistry.0", mark));
		}
		registryCache.put(mark, (Class<T>) pluginClass);
	}

	public Collection<T> allPlugin() {
		if (all.size() != registryCache.size()) {
			all.clear();
			for (final Class<T> markClass : registryCache.values()) {
				all.add(ModulePluginFactory.get(markClass));
			}
			ObjectUtils.sort(all);
		}
		return new Vector<T>(all);
	}

	public T getPlugin(final int mark) {
		if (mark == 0) {
			return allPlugin().iterator().next();
		}
		return ModulePluginFactory.get(registryCache.get(mark));
	}
}