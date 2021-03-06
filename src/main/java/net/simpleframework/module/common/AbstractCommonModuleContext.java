package net.simpleframework.module.common;

import net.simpleframework.common.th.NotImplementedException;
import net.simpleframework.ctx.AbstractADOModuleContext;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.IAttachmentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractCommonModuleContext extends AbstractADOModuleContext
		implements ICommonModuleContext {

	@Override
	public IAttachmentService<? extends Attachment> getAttachmentService() {
		throw NotImplementedException.of(getClass(), "getAttachmentService");
	}
}
