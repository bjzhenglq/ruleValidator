package com.web.pub.page.handler;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface IPageAwareResult {

	public int getTotalPages();

	public int getTotalRecords();

	/**
	 * 
	 * @return
	 */
	public abstract List<?> firstPage();

	/**
	 * ���һҳ
	 * 
	 * @return
	 */

	public abstract List lastPage();

	/**
	 * ��һҳ
	 * 
	 * @return
	 */
	public abstract List nextPage();

	/**
	 * ��һҳ
	 * 
	 * @return
	 */
	public abstract List previousPage();

	public abstract List toPage(int page);

}