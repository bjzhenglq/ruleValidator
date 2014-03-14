package com.web.pub.page.handler;

import java.io.Serializable;

public class PubQryPara implements IDBQuery, Serializable {

	/**
	 * @author:sunshine
	 * @date:2014-1-7 ����2:08:24
	 */
	private static final long serialVersionUID = -1588509199655547693L;
	// �²�ѯ��ʶID
	private String newSearchId;
	// ԭ��ѯ��ʶID
	private String oldSearchId;

	// ��ҳ����
	private PageInfoPara pageInfo;

	@Override
	public String getNewSearchId() {
		return newSearchId;
	}

	public void setNewSearchId(String newSearchId) {
		this.newSearchId = newSearchId;
	}

	@Override
	public String getOldSearchId() {
		return oldSearchId;
	}

	public void setOldSearchId(String oldSearchId) {
		this.oldSearchId = oldSearchId;
	}

	public PageInfoPara getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoPara pageInfo) {
		this.pageInfo = pageInfo;
	}

	@Override
	public int getCountPerPage() {
		return this.getPageInfo().getPageSize();
	}
}
