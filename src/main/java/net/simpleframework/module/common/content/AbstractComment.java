package net.simpleframework.module.common.content;

import net.simpleframework.ado.ColumnMeta;
import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.common.ID;
import net.simpleframework.common.web.html.HtmlUtils;
import net.simpleframework.lib.org.jsoup.nodes.Document;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractComment extends AbstractUserAwareBean {

	/* 拥有者id */
	private ID contentId;

	/* 内容 */
	private String comment;

	public ID getContentId() {
		return contentId;
	}

	public void setContentId(final ID contentId) {
		this.contentId = contentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
		removeAttr("_doc");
	}

	@ColumnMeta(ignore = true)
	public Document doc() {
		Document doc = (Document) getAttr("_doc");
		if (doc == null) {
			setAttr("_doc", doc = HtmlUtils.createHtmlDocument(getComment()));
		}
		return doc;
	}

	@Override
	public String toString() {
		return getComment();
	}
}
