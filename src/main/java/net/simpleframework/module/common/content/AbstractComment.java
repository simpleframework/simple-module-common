package net.simpleframework.module.common.content;

import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.common.ID;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.web.html.HtmlUtils;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractComment extends AbstractUserAwareBean {

	/* 拥有者id */
	private ID contentId;

	/* 内容 */
	private String content;

	public ID getContentId() {
		return contentId;
	}

	public void setContentId(final ID contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return StringUtils.substring(HtmlUtils.htmlToText(getContent()), 100);
	}
}
