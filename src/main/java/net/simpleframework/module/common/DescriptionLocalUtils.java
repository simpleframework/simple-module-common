package net.simpleframework.module.common;

import java.util.HashMap;
import java.util.Map;

import net.simpleframework.ado.bean.IIdBeanAware;
import net.simpleframework.common.Convert;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class DescriptionLocalUtils {

	private static final ThreadLocal<Map<String, String>> DESCRIPTION_LOCAL;
	static {
		DESCRIPTION_LOCAL = new ThreadLocal<Map<String, String>>();
	}

	private static String key(final Object bean) {
		final Object k = bean instanceof IIdBeanAware ? ((IIdBeanAware) bean).getId() : bean;
		return Convert.toString(k);
	}

	public static String get(final Object bean) {
		if (bean == null) {
			return null;
		}
		final Map<String, String> m = DESCRIPTION_LOCAL.get();
		return m != null ? m.get(key(bean)) : null;
	}

	public static void set(final Object bean, final String description) {
		if (bean == null) {
			return;
		}
		Map<String, String> m = DESCRIPTION_LOCAL.get();
		if (description == null) {
			if (m != null) {
				m.remove(key(bean));
				if (m.size() == 0) {
					DESCRIPTION_LOCAL.remove();
				}
			}
		} else {
			if (m == null) {
				DESCRIPTION_LOCAL.set(m = new HashMap<String, String>());
			}
			m.put(key(bean), description);
		}
	}
}
