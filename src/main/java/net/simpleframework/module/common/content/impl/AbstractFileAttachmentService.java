package net.simpleframework.module.common.content.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.simpleframework.common.FileUtils;
import net.simpleframework.ctx.common.bean.AttachmentFile;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.AttachmentLob;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractFileAttachmentService<T extends Attachment> extends
		AbstractAttachmentService<T> {

	private String homedir = "d:/attachments";

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
		if (lob != null) {
			lob.setAttachment(new FileInputStream(new File(_getHomedir() + lob.getMd())));
		}
		return lob;
	}

	@Override
	protected void setAttachment(final AttachmentLob lob, final AttachmentFile af)
			throws IOException {
		FileUtils.copyFile(new FileInputStream(af.getAttachment()),
				new File(_getHomedir() + af.getMd5()));
	}

	@Override
	protected void deleteAttachment(final String md) {
		super.deleteAttachment(md);
		new File(_getHomedir() + md).delete();
	}
}
