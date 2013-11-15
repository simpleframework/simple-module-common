package net.simpleframework.module.common.plugin;

import net.simpleframework.common.th.RuntimeExceptionEx;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public class ModulePluginException extends RuntimeExceptionEx {

	public ModulePluginException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public static ModulePluginException of(final String msg) {
		return _of(ModulePluginException.class, msg);
	}

	private static final long serialVersionUID = -9040459630157881505L;
}
