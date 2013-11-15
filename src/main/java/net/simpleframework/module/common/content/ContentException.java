package net.simpleframework.module.common.content;

import net.simpleframework.common.th.RuntimeExceptionEx;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public class ContentException extends RuntimeExceptionEx {

	public ContentException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public static ContentException of(final String msg) {
		return _of(ContentException.class, msg);
	}

	public static RuntimeException of(final Throwable throwable) {
		return _of(ContentException.class, null, throwable);
	}

	private static final long serialVersionUID = -3871370008470558392L;
}
