package com.web.pub.page.handler;

import java.io.Serializable;

public class PageInfoPara implements Serializable {

	private static final long serialVersionUID = 9071730108601342179L;

	private int curPageNo = 1;// ��ǰҳ��

	private int pageSize = 20;// ÿҳ����

	/**
	 * ÿҳ��¼��
	 */
	private int countPerPage;

	public int getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

}
