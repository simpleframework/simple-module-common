package net.simpleframework.module.common;

import net.simpleframework.ctx.service.ado.db.IDbModuleContext;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.IAttachmentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface ICommonModuleContext extends IDbModuleContext {

	/**
	 * 获取附件管理器
	 * 
	 * @return
	 */
	IAttachmentService<? extends Attachment> getAttachmentService();
}
