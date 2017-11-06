package net.simpleframework.module.common.log;

import java.util.HashSet;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class LogEntity {
	public static boolean isDisable(final Object bean) {
		return hCache.contains(bean);
	}

	public static void disable(final Object bean) {
		hCache.add(bean);
	}

	public static void enable(final Object bean) {
		hCache.remove(bean);
	}

	private static final HashSet<Object> hCache = new HashSet<>();
}
