package net.simpleframework.module.common;

import net.simpleframework.ado.db.DbEntityTable;
import net.simpleframework.ctx.IModuleContext;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.AttachmentLob;
import net.simpleframework.module.common.content.IAttachmentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface ICommonModuleContext extends IModuleContext {

	/**
	 * 获取附件管理器
	 * 
	 * @return
	 */
	IAttachmentService<? extends Attachment> getAttachmentService();

	static final DbEntityTable SF_ATTACHMENT = new DbEntityTable(Attachment.class, "sf_attachment");
	static final DbEntityTable SF_ATTACHMENT_LOB = new DbEntityTable(AttachmentLob.class,
			"sf_attachment_lob").setUniqueColumns("md").setNoCache(true);
}
