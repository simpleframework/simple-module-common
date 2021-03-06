package net.simpleframework.module.common.content;

import net.simpleframework.ado.bean.AbstractUserAwareBean;
import net.simpleframework.ado.bean.IOrderBeanAware;
import net.simpleframework.ado.db.common.EntityInterceptor;
import net.simpleframework.common.ID;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
@EntityInterceptor(listenerTypes = { "net.simpleframework.module.log.EntityDeleteLogAdapter" })
public class Attachment extends AbstractUserAwareBean implements IOrderBeanAware {
	/* 外键，文档id */
	private ID contentId;

	/* 模块标识 */
	private int imodule;

	/* 摘要值，md5 */
	private String md5;

	/* 标题 */
	private String topic;

	/* 附件类型 */
	private int attachtype;
	/* 附件大小 */
	private long attachsize;

	/* 如果视频附件，记录播放时间 */
	private int videoTime;

	/* 下载地址，可为空 */
	private String durl;

	/* 文件扩展名 */
	private String fileExt;

	/* 下载次数 */
	private int downloads;

	/* 描述 */
	private String description;

	/* 排序号 */
	private int oorder;

	public ID getContentId() {
		return contentId;
	}

	public void setContentId(final ID contentId) {
		this.contentId = contentId;
	}

	public int getImodule() {
		return imodule;
	}

	public void setImodule(final int imodule) {
		this.imodule = imodule;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(final String md5) {
		this.md5 = md5;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(final String topic) {
		this.topic = topic;
	}

	public int getAttachtype() {
		return attachtype;
	}

	public void setAttachtype(final int attachtype) {
		this.attachtype = attachtype;
	}

	public int getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(final int videoTime) {
		this.videoTime = videoTime;
	}

	public String getDurl() {
		return durl;
	}

	public void setDurl(final String durl) {
		this.durl = durl;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(final String fileExt) {
		this.fileExt = fileExt;
	}

	public long getAttachsize() {
		return attachsize;
	}

	public void setAttachsize(final long attachsize) {
		this.attachsize = attachsize;
	}

	public int getDownloads() {
		return downloads;
	}

	public void setDownloads(final int downloads) {
		this.downloads = downloads;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int getOorder() {
		return oorder;
	}

	@Override
	public void setOorder(final int oorder) {
		this.oorder = oorder;
	}

	@Override
	public String toString() {
		return getTopic();
	}

	private static final long serialVersionUID = 7644674999064418448L;
}
