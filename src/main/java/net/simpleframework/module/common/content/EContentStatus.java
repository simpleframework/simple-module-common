package net.simpleframework.module.common.content;

import static net.simpleframework.common.I18n.$m;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
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

	lock {
		@Override
		public String toString() {
			return $m("EContentStatus.lock");
		}
	},

	abort {
		@Override
		public String toString() {
			return $m("EContentStatus.abort");
		}
	},

	delete {
		@Override
		public String toString() {
			return $m("EContentStatus.delete");
		}
	},

	closed {
		@Override
		public String toString() {
			return $m("EContentStatus.closed");
		}
	},

	temp {
		@Override
		public String toString() {
			return $m("EContentStatus.temp");
		}
	}
}
