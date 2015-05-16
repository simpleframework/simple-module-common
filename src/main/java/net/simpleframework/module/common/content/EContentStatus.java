package net.simpleframework.module.common.content;

import static net.simpleframework.common.I18n.$m;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public enum EContentStatus {
	edit {
		@Override
		public String toString() {
			return $m("EContentStatus.edit");
		}
	},
	audit {
		@Override
		public String toString() {
			return $m("EContentStatus.audit");
		}
	},
	publish {
		@Override
		public String toString() {
			return $m("EContentStatus.publish");
		}
	},
	closed {
		@Override
		public String toString() {
			return $m("EContentStatus.closed");
		}
	},
	lock {
		@Override
		public String toString() {
			return $m("EContentStatus.lock");
		}
	},
	delete {
		@Override
		public String toString() {
			return $m("EContentStatus.delete");
		}
	},
	temp {
		@Override
		public String toString() {
			return $m("EContentStatus.temp");
		}
	}
}
