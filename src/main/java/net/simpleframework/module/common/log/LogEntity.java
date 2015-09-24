package net.simpleframework.module.common.log;

import net.simpleframework.common.Convert;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class LogEntity {

	private static final ThreadLocal<Boolean> FLAGs;
	static {
		FLAGs = new ThreadLocal<Boolean>();
	}

	public static boolean isDisable() {
		return Convert.toBool(FLAGs.get());
	}

	public static void disable() {
		FLAGs.set(Boolean.TRUE);
	}

	public static void enable() {
		FLAGs.remove();
	}
}
