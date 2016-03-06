package net.simpleframework.module.common.content.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.simpleframework.common.FileUtils;
import net.simpleframework.ctx.common.bean.AttachmentFile;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.AttachmentLob;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractFileAttachmentService<T extends Attachment> extends
		AbstractAttachmentService<T> {

	private String homedir = "c:/attachments";

	public String getHomedir() {
		return homedir;
	}

	public void setHomedir(final String homedir) {
		this.homedir = homedir;
	}

	protected String _getHomedir() {
		final File dir = new File(getHomedir());
		if (!dir.exists()) {
			FileUtils.createDirectoryRecursively(dir);
		}
		return dir.getAbsolutePath() + File.separator;
	}

	@Override
	public AttachmentLob getLob(final T attachment) throws IOException {
		final AttachmentLob lob = super.getLob(attachment);
		// 数据库依旧保留lob表，需要refs计数
		File file;
		if (lob != null && (file = new File(_getHomedir() + attachment.getMd5())).exists()) {
			lob.setAttachment(new FileInputStream(file));
		}
		return lob;
	}

	@Override
	protected void putAttachmentLob(final AttachmentLob lob, final AttachmentFile af)
			throws IOException {
		final File file = af.getAttachment();
		if (file.exists()) {
			FileUtils.copyFile(new FileInputStream(file), new File(_getHomedir() + af.getMd5()));
		}
	}

	@Override
	protected void deleteAttachmentLob(final T attachment) throws IOException {
		super.deleteAttachmentLob(attachment);
		new File(_getHomedir() + attachment.getMd5()).delete();
	}

	@Override
	public void updateAttachmentLob(final T attachment, final InputStream iStream)
			throws IOException {
		// 直接更新内容
		FileUtils.copyFile(iStream, new File(_getHomedir() + attachment.getMd5()));
	}
}
