package net.simpleframework.module.common.content;

import static net.simpleframework.common.I18n.$m;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public enum ECategoryMark {
	/**
	 * 正常标识
	 */
	normal {
		@Override
		public String toString() {
			return $m("ECategoryMark.normal");
		}
	},

	/**
	 * 内置类目标识
	 */
	builtIn {
		@Override
		public String toString() {
			return $m("ECategoryMark.builtIn");
		}
	}
}
