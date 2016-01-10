package net.simpleframework.module.common.content.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.simpleframework.ctx.common.bean.AttachmentFile;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.AttachmentLob;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractOSSAttachmentService<T extends Attachment> extends
		AbstractAttachmentService<T> {

	protected abstract OSSClient getClient();

	protected abstract String getBucketname();

	@Override
	public AttachmentLob getLob(final String md) throws IOException {
		final AttachmentLob lob = super.getLob(md);
		final OSSClient client = getClient();
		final String bucketname = getBucketname();
		if (lob != null && client.doesObjectExist(bucketname, md)) {
			final OSSObject obj = client.getObject(bucketname, md);
			lob.setAttachment(obj.getObjectContent());
			return lob;
		}
		return null;
	}

	@Override
	protected void putAttachmentLob(final AttachmentLob lob, final AttachmentFile af)
			throws IOException {
		final File file = af.getAttachment();
		if (file.exists()) {
			final ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(af.getSize());
			getClient().putObject(getBucketname(), af.getMd5(),
					new FileInputStream(af.getAttachment()), meta);
		}
	}

	@Override
	protected void deleteAttachment(final String md) throws IOException {
		super.deleteAttachment(md);
		getClient().deleteObject(getBucketname(), md);
	}

	@Override
	public void updateAttachment(final T attachment, final InputStream iStream) throws IOException {
		// 不支持
	}
}
