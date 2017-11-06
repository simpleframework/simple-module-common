package net.simpleframework.module.common.log;

import java.util.HashMap;
import java.util.Map;

import net.simpleframework.ado.bean.IIdBeanAware;
import net.simpleframework.common.Convert;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class LdescVal {

	private static final ThreadLocal<Map<String, String>> DESCs;
	static {
		DESCs = new ThreadLocal<>();
	}

	public static String get(final Object bean) {
		if (bean == null) {
			return null;
		}
		final Map<String, String> m = DESCs.get();
		if (m != null) {
			return m.get(key(bean));
		} else {
			final String s = Convert.toString(bean);
			return s.startsWith(bean.getClass().getName() + "@") ? null : s;
		}
	}

	public static void set(final Object bean, final String description) {
		if (bean == null) {
			return;
		}
		Map<String, String> m = DESCs.get();
		if (description == null) {
			if (m != null) {
				m.remove(key(bean));
				if (m.size() == 0) {
					DESCs.remove();
				}
			}
		} else {
			if (m == null) {
				DESCs.set(m = new HashMap<>());
			}
			m.put(key(bean), description);
		}
	}

	private static String key(final Object bean) {
		final Object k = bean instanceof IIdBeanAware ? ((IIdBeanAware) bean).getId(true) : bean;
		return Convert.toString(k);
	}
}
