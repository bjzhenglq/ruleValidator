package com.web.pub.fw.service;

import java.awt.Menu;
import java.util.List;

import com.web.pub.fw.exception.PubBaseException;

public interface IMenuDomain {

	/**
	 * ���ڵ�ID
	 */
	public static final String ROOT_ID = "root";

	/**
	 * ʱ�����ʽ
	 */
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ����
	 * 
	 * @param data
	 * @return
	 * @throws WebBaseException
	 */
	public Menu insert(Menu data) throws PubBaseException;

	/**
	 * ����
	 * 
	 * @param data
	 * @return
	 * @throws WebBaseException
	 */
	public Menu update(Menu data) throws PubBaseException;

	/**
	 * ɾ����������
	 * 
	 * @param pks
	 *            ����
	 * @throws WebBaseException
	 */
	public void delete(String[] pks) throws PubBaseException;

	/**
	 * �굥
	 * 
	 * @param id
	 * @return
	 * @throws WebBaseException
	 */
	public Menu detail(String pk) throws PubBaseException;

	/**
	 * ��ѯ
	 * 
	 * @param filter
	 * @return
	 * @throws WebBaseException
	 */
	// public List<Menu> query(MenuQueryFilter filter) throws PubBaseException;

	/**
	 * ��ȡ���ӽڵ�
	 * 
	 * @param parent
	 *            ���ڵ�
	 * 
	 * @param �Ƿ�����ѯ
	 * @return
	 * @throws WebBaseException
	 */
	public List<Menu> getChildren(String parentId, boolean isCascade)
			throws PubBaseException;

}
