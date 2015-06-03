package net.simpleframework.module.common.content;

import net.simpleframework.ado.ColumnMeta;
import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.ado.bean.IDescriptionBeanAware;
import net.simpleframework.ado.bean.IOrderBeanAware;
import net.simpleframework.common.web.html.HtmlUtils;
import net.simpleframework.lib.org.jsoup.nodes.Document;
import net.simpleframework.module.common.bean.IViewsBeanAware;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@SuppressWarnings("serial")
public abstract class AbstractContentBean extends AbstractUserAwareBean implements
		IDescriptionBeanAware, IOrderBeanAware, IViewsBeanAware {
	/* 状态 */
	@ColumnMeta(columnText = "#(AbstractContentBean.0)")
	private EContentStatus status;

	/* 标题 */
	@ColumnMeta(columnText = "#(AbstractContentBean.1)")
	private String topic;
	/* 正文内容 */
	private String content;
	/* 内容标识 */
	private int contentMark;

	/* 描述 */
	@ColumnMeta(columnText = "#(Description)")
	private String description;

	/* 排序字段 */
	private int oorder;
	/* 统计信息-查看次数 */
	private long views;

	public EContentStatus getStatus() {
		return status == null ? EContentStatus.edit : status;
	}

	public void setStatus(final EContentStatus status) {
		this.status = status;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
		removeAttr("_doc");
	}

	public int getContentMark() {
		return contentMark;
	}

	public void setContentMark(final int contentMark) {
		this.contentMark = contentMark;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int getOorder() {
		return oorder;
	}

	@Override
	public void setOorder(final int oorder) {
		this.oorder = oorder;
	}

	@Override
	public long getViews() {
		return views;
	}

	@Override
	public void setViews(final long views) {
		this.views = views;
	}

	@Override
	public String toString() {
		return getTopic();
	}

	@ColumnMeta(ignore = true)
	public Document doc() {
		return getAttrCache("_doc", new CacheV<Document>() {
			@Override
			public Document get() {
				return HtmlUtils.createHtmlDocument(getContent());
			}
		});
	}

	public static int CONTENT_MARK_IMG = 1; // 含有图片 01
	public static int CONTENT_MARK_ATTACH = 2; // 含有附件 10
	public static int CONTENT_MARK_CODE = 4; // 含有代码 100
	public static int CONTENT_MARK_VOTE = 8; // 含有代码 1000
}
