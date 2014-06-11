package net.simpleframework.module.common.content.impl;

import static net.simpleframework.common.I18n.$m;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.simpleframework.ado.FilterItems;
import net.simpleframework.ado.IParamsValue;
import net.simpleframework.ado.db.IDbEntityManager;
import net.simpleframework.ado.db.common.ExpressionValue;
import net.simpleframework.ado.query.DataQueryUtils;
import net.simpleframework.ado.query.IDataQuery;
import net.simpleframework.common.BeanUtils;
import net.simpleframework.common.FileUtils;
import net.simpleframework.common.ID;
import net.simpleframework.common.StringUtils;
import net.simpleframework.ctx.common.bean.AttachmentFile;
import net.simpleframework.ctx.service.ado.db.AbstractDbBeanService;
import net.simpleframework.module.common.content.Attachment;
import net.simpleframework.module.common.content.AttachmentLob;
import net.simpleframework.module.common.content.IAttachmentService;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885) https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public abstract class AbstractAttachmentService<T extends Attachment> extends
		AbstractDbBeanService<T> implements IAttachmentService<T> {

	@Override
	public IDataQuery<T> queryByContent(final Object contentId, final int attachtype) {
		if (contentId == null) {
			return DataQueryUtils.nullQuery();
		}
		return queryByParams(FilterItems.of("contentId", contentId)
				.addEqual("attachtype", attachtype));
	}

	@Override
	public IDataQuery<T> queryByContent(final Object contentId) {
		if (contentId == null) {
			return DataQueryUtils.nullQuery();
		}
		return queryByParams(FilterItems.of("contentId", contentId));
	}

	protected IDbEntityManager<AttachmentLob> getLobEntityManager() {
		return getEntityManager(AttachmentLob.class);
	}

	protected AttachmentLob createLob() {
		return new AttachmentLob();
	}

	protected T createAttachmentFile(final AttachmentFile aFile) {
		final T attachment = createBean();
		return attachment;
	}

	@Override
	public void insert(final ID contentId, final ID userId,
			final Collection<AttachmentFile> attachments) throws IOException {
		insert(contentId, userId, attachments, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(final ID contentId, final ID userId,
			final Collection<AttachmentFile> attachments, final Map<String, Object> exts)
			throws IOException {
		if (attachments == null || attachments.size() == 0) {
			return;
		}
		final IDbEntityManager<AttachmentLob> lobManager = getLobEntityManager();
		for (final AttachmentFile af : attachments) {
			final String md5 = af.getMd5();
			final T attachment = createAttachmentFile(af);
			attachment.setId(ID.of(af.getId()));
			attachment.setContentId(contentId);
			attachment.setTopic(FileUtils.stripFilenameExtension(af.getTopic()));
			attachment.setMd5(md5);
			attachment.setAttachsize(af.getAttachment().length());
			attachment.setAttachtype(af.getType());
			attachment.setFileExt(af.getExt());
			attachment.setCreateDate(new Date());
			attachment.setUserId(userId);
			attachment.setDescription(af.getDescription());

			if (exts != null) {
				for (final Map.Entry<String, Object> p : exts.entrySet()) {
					BeanUtils.setProperty(attachment, p.getKey(), p.getValue());
				}
			}

			getEntityManager().insert(attachment);

			// lob
			AttachmentLob lob;
			if ((lob = getLob(attachment)) == null) {
				lob = createLob();
				lob.setMd(md5);
				setAttachment(lob, af);
				lobManager.insert(lob);
			} else {
				lob.setRefs(lob.getRefs() + 1);
				lobManager.update(new String[] { "refs" }, lob);
			}
		}
	}

	@Override
	public void updateLob(final T attachment, final InputStream iStream) throws IOException {
		final AttachmentLob lob = getLob(attachment);
		if (lob != null) {
			lob.setAttachment(iStream);
			getLobEntityManager().update(new String[] { "attachment" }, lob);
		}
	}

	@Override
	public AttachmentLob getLob(final T attachment) throws IOException {
		return attachment == null ? null : getLobEntityManager().queryForBean(
				new ExpressionValue("md=?", attachment.getMd5()));
	}

	protected void setAttachment(final AttachmentLob lob, final AttachmentFile af)
			throws IOException {
		lob.setAttachment(new FileInputStream(af.getAttachment()));
	}

	protected void deleteAttachment(final String md) {
		getLobEntityManager().delete(new ExpressionValue("md=?", md));
	}

	private String tmpdir;

	@Override
	public String getTempdir() {
		if (tmpdir == null) {
			final StringBuilder sb = new StringBuilder();
			final String fs = File.separator;
			sb.append(getModuleContext().getContextSettings().getTmpFiledir().getAbsolutePath());
			sb.append(fs).append("attach").append(fs);
			tmpdir = sb.toString();
		}
		return tmpdir;
	}

	@Override
	public AttachmentFile createAttachmentFile(final T attachment) throws IOException {
		if (attachment == null) {
			return null;
		}
		String filename = getTempdir() + attachment.getMd5();
		final String ext = attachment.getFileExt();
		if (StringUtils.hasText(ext)) {
			filename += "." + ext;
		}
		final File oFile = new File(filename);
		final AttachmentFile af = new AttachmentFile(oFile, attachment.getMd5()) {
			@Override
			public File getAttachment() throws IOException {
				synchronized (this) {
					if (!oFile.exists()) {
						final AttachmentLob lob = getLob(attachment);
						InputStream inputStream;
						if (lob == null || (inputStream = lob.getAttachment()) == null) {
							throw new IOException($m("AbstractAttachmentService.0"));
						}
						FileUtils.copyFile(inputStream, oFile);
					}
				}
				return super.getAttachment();
			};

			private static final long serialVersionUID = 3689368709808495226L;

		}.setId(attachment.getId().toString()).setSize(attachment.getAttachsize())
				.setTopic(attachment.getTopic()).setType(attachment.getAttachtype()).setExt(ext)
				.setCreateDate(attachment.getCreateDate()).setDownloads(attachment.getDownloads())
				.setDescription(attachment.getDescription());
		return af;
	}

	@Override
	public void onInit() throws Exception {
		super.onInit();

		addListener(new DbEntityAdapterEx() {
			@Override
			public void onBeforeDelete(final IDbEntityManager<?> service,
					final IParamsValue paramsValue) {
				super.onBeforeDelete(service, paramsValue);
				coll(paramsValue); // 删除前缓存
			}

			@Override
			public void onAfterDelete(final IDbEntityManager<?> manager, final IParamsValue paramsValue) {
				super.onAfterDelete(manager, paramsValue);
				final IDbEntityManager<AttachmentLob> lobManager = getLobEntityManager();
				for (final Attachment attachment : coll(paramsValue)) {
					final String md5 = attachment.getMd5();
					final AttachmentLob lob = lobManager.queryForBean(new ExpressionValue("md=?", md5));
					if (lob != null) {
						final int refs = lob.getRefs();
						if (refs == 0 && count("md5=?", md5) == 0) {
							deleteAttachment(md5);
						} else {
							if (refs > 0) {
								lob.setRefs(refs - 1);
								lobManager.update(new String[] { "refs" }, lob);
							}
						}
					}
				}
			}
		});
	}
}
