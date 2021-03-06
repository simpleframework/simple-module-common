package net.simpleframework.module.common.bean;

import net.simpleframework.common.Convert;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public class CategoryStat {

	private String categoryId;

	private int count;

	public CategoryStat(final String categoryId, final Object count) {
		setCategoryId(categoryId);
		setCount(Convert.toInt(count));
	}

	public String getCategoryId() {
		return categoryId;
	}

	public CategoryStat setCategoryId(final String categoryId) {
		this.categoryId = categoryId;
		return this;
	}

	public int getCount() {
		return count;
	}

	public CategoryStat setCount(final int count) {
		this.count = count;
		return this;
	}
}
