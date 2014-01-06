package net.simpleframework.module.common.content;

import java.io.InputStream;
import java.io.Serializable;

import net.simpleframework.ado.db.DbEntityTable;
import net.simpleframework.ado.db.common.EntityInterceptor;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@EntityInterceptor(listenerTypes = { "net.simpleframework.module.log.EntityDeleteLogAdapter" })
public class AttachmentLob implements Serializable {

	/* 摘要值，唯一键值 */
	private String md;

	/* 引用次数 */
	private int refs;

	/* 附件 */
	private InputStream attachment;

	public String getMd() {
		return md;
	}

	public void setMd(final String md) {
		this.md = md;
	}

	public int getRefs() {
		return refs;
	}

	public void setRefs(final int refs) {
		this.refs = refs;
	}

	public InputStream getAttachment() {
		return attachment;
	}

	public void setAttachment(final InputStream attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return getMd();
	}

	public static final DbEntityTable TBL = new DbEntityTable(AttachmentLob.class,
			"sf_attachment_lob").setNoCache(true);

	private static final long serialVersionUID = 1757957179977035488L;
}
