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
public abstract class AbstractAttachmentService<T extends Attachment>
		extends AbstractDbBeanService<T> implements IAttachmentService<T> {

	@Override
	public IDataQuery<T> queryByContent(final Object contentId, final int attachtype) {
		if (contentId == null) {
			return DataQueryUtils.nullQuery();
		}
		return queryByParams(
				FilterItems.of("contentId", getIdParam(contentId)).addEqual("attachtype", attachtype));
	}

	@Override
	public IDataQuery<T> queryByContent(final Object contentId) {
		if (contentId == null) {
			return DataQueryUtils.nullQuery();
		}
		return queryByParams(FilterItems.of("contentId", getIdParam(contentId)));
	}

	@Override
	public int deleteByContent(final Object contentId) {
		return deleteWith("contentId=?", getIdParam(contentId));
	}

	protected IDbEntityManager<? extends AttachmentLob> getLobEntityManager() {
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

			attachment.setAttr("_AttachmentFile", af);
			getEntityManager().insert(attachment);

			// lob
			AttachmentLob lob;
			if ((lob = getLob(attachment)) == null) {
				insertAttachmentLob(af);
			} else {
				if (lob.getAttachment() == null) {
					putAttachmentLob(lob, af);
				}
				updateRefs(lob, lob.getRefs() + 1);
			}
		}
	}

	@Override
	public AttachmentLob getLob(final T attachment) throws IOException {
		return attachment == null ? null : getLob(attachment.getMd5());
	}

	@Override
	public AttachmentLob getLob(final String md) throws IOException {
		return getLobEntityManager().queryForBean(new ExpressionValue("md=?", md));
	}

	@SuppressWarnings("unchecked")
	protected void insertAttachmentLob(final AttachmentFile af) throws IOException {
		final AttachmentLob lob = createLob();
		lob.setMd(af.getMd5());
		putAttachmentLob(lob, af);
		((IDbEntityManager<AttachmentLob>) getLobEntityManager()).insert(lob);
	}

	protected void putAttachmentLob(final AttachmentLob lob, final AttachmentFile af)
			throws IOException {
		final File file = af.getAttachment();
		if (file.exists()) {
			lob.setAttachment(new FileInputStream(file));
		}
	}

	protected void deleteAttachmentLob(final T attachment) throws IOException {
		getLobEntityManager().delete(new ExpressionValue("md=?", attachment.getMd5()));
	}

	@SuppressWarnings("unchecked")
	protected void updateRefs(final AttachmentLob lob, final int refs) throws IOException {
		lob.setRefs(Math.max(refs, 0));
		((IDbEntityManager<AttachmentLob>) getLobEntityManager()).update(new String[] { "refs" },
				lob);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAttachmentLob(final T attachment, final InputStream iStream)
			throws IOException {
		// 更新附件内容
		final AttachmentLob lob = getLob(attachment);
		if (lob != null) {
			lob.setAttachment(iStream);
			((IDbEntityManager<AttachmentLob>) getLobEntityManager())
					.update(new String[] { "attachment" }, lob);
		}
	}

	@Override
	public long getAttachsize(final Object user) {
		return sum("attachsize", "userid=?", getIdParam(user)).longValue();
	}

	private String tmpdir;

	@Override
	public String getTempdir() {
		if (tmpdir == null) {
			tmpdir = getModuleContext().getContextSettings().getHomeFile("/attach/").getAbsolutePath();
		}
		return tmpdir;
	}

	@Override
	public AttachmentFile createAttachmentFile(final T attachment) throws IOException {
		if (attachment == null) {
			return null;
		}

		final AttachmentFile af = new AttachmentFile(null, attachment.getMd5()) {
			@Override
			public File getAttachment() throws IOException {
				synchronized (this) {
					if (file == null) {
						String filename = getTempdir() + attachment.getMd5();
						final String ext = attachment.getFileExt();
						if (StringUtils.hasText(ext)) {
							filename += "." + ext;
						}
						file = new File(filename);
					}
					if (!file.exists()) {
						final AttachmentLob lob = getLob(attachment);
						InputStream inputStream;
						if (lob == null || (inputStream = lob.getAttachment()) == null) {
							throw new IOException($m("AbstractAttachmentService.0"));
						}
						FileUtils.copyFile(inputStream, file);
					}
				}
				return file;
			};

			private static final long serialVersionUID = 3689368709808495226L;

		}.setId(attachment.getId().toString()).setDurl(attachment.getDurl())
				.setSize(attachment.getAttachsize()).setTopic(attachment.getTopic())
				.setType(attachment.getAttachtype()).setExt(attachment.getFileExt())
				.setCreateDate(attachment.getCreateDate()).setDownloads(attachment.getDownloads())
				.setDescription(attachment.getDescription());
		return af;
	}

	@Override
	public void onInit() throws Exception {
		super.onInit();

		addListener(new DbEntityAdapterEx<T>() {
			@Override
			public void onBeforeDelete(final IDbEntityManager<T> manager,
					final IParamsValue paramsValue) throws Exception {
				super.onBeforeDelete(manager, paramsValue);
				coll(manager, paramsValue); // 删除前缓存
			}

			@Override
			public void onAfterDelete(final IDbEntityManager<T> manager,
					final IParamsValue paramsValue) throws Exception {
				super.onAfterDelete(manager, paramsValue);
				for (final T attachment : coll(manager, paramsValue)) {
					final AttachmentLob lob = getLob(attachment);
					if (lob != null) {
						final int refs = lob.getRefs();
						if (refs <= 0) {
							deleteAttachmentLob(attachment);
						} else {
							updateRefs(lob, refs - 1);
						}
					}
				}
			}
		});
	}
}
