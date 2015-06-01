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
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
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
	public void updateAttachment(final T attachment, final InputStream iStream) throws IOException {
		FileUtils.copyFile(iStream, new File(_getHomedir() + attachment.getMd5()));
	}

	@Override
	public AttachmentLob getLob(final String md) throws IOException {
		final File file = new File(_getHomedir() + md);
		if (file.exists()) {
			final AttachmentLob lob = createLob();
			lob.setAttachment(new FileInputStream(file));
			return lob;
		}
		return null;
	}

	@Override
	protected void insertAttachment(final AttachmentFile af) throws IOException {
		FileUtils.copyFile(new FileInputStream(af.getAttachment()),
				new File(_getHomedir() + af.getMd5()));
	}

	@Override
	protected void deleteAttachment(final String md) throws IOException {
		new File(_getHomedir() + md).delete();
	}
}
