package net.simpleframework.module.common.content;

import java.io.IOException;
import java.util.Map;

import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.ID;
import net.simpleframework.ctx.common.bean.AttachmentFile;
import net.simpleframework.ctx.service.ado.db.IDbBeanService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IAttachmentService<T extends Attachment> extends IDbBeanService<T> {

	/**
	 * 获取文档的附件列表
	 * 
	 * @param ContentBean
	 * @return
	 */
	IDataQuery<T> queryByContent(Object content);

	/**
	 * 获取lob对象
	 * 
	 * @param attachment
	 * @return
	 */
	AttachmentLob getLob(T attachment);

	void insert(ID contentId, ID userId, Map<String, AttachmentFile> attachments) throws IOException;

	/**
	 * 插入附件
	 * 
	 * @param contentId
	 * @param userId
	 * @param attachments
	 * @param exts
	 *           扩展属性
	 */
	void insert(ID contentId, ID userId, Map<String, AttachmentFile> attachments,
			Map<String, Object> exts) throws IOException;

	/**
	 * 创建附件对象
	 * 
	 * @param attachment
	 * @return
	 * @throws IOException
	 */
	AttachmentFile createAttachmentFile(T attachment) throws IOException;

	String getTempdir();
}
