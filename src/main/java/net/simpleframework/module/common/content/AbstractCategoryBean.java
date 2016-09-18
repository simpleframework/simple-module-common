package net.simpleframework.module.common.content;

import static net.simpleframework.common.I18n.$m;

import net.simpleframework.ado.bean.AbstractTextDescriptionBean;
import net.simpleframework.ado.bean.IOrderBeanAware;
import net.simpleframework.ado.bean.ITreeBeanAware;
import net.simpleframework.common.ID;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractCategoryBean extends AbstractTextDescriptionBean
		implements ITreeBeanAware, IOrderBeanAware {
	/* 父id */
	private ID parentId;

	/* 排序字段 */
	private int oorder;

	@Override
	public ID getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(final ID parentId) {
		this.parentId = parentId;
	}

	@Override
	public int getOorder() {
		return oorder;
	}

	@Override
	public void setOorder(final int oorder) {
		this.oorder = oorder;
	}

	public static enum ECategoryMark {
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
}
