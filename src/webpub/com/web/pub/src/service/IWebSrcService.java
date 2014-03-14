package com.web.pub.src.service;

import java.util.List;

import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.fw.uiview.MenuView;
import com.web.pub.fw.uiview.ResourceView;
import com.web.pub.page.paravo.MenusQryParaVO;
import com.web.pub.page.paravo.ResourQryParaVO;

/**
 * ��ȡ��Դ����
 * 
 * @author:sunshine
 * @date:2014-1-13 ����2:43:29
 */
public interface IWebSrcService {

	/**
	 * ��ѯ���в���Ϣ
	 * 
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-13 ����2:46:42
	 */
	public List<MenuView> qryMenuVO(MenusQryParaVO qryParaVO)
			throws PubBusinessException;

	/**
	 * ����Ԥ�ýű�
	 * 
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-21 ����3:18:55
	 */
	public void initMenuData() throws PubBusinessException;

	/**
	 * ������Դ
	 * 
	 * @param resourceView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:36
	 */
	public ResourceView addResource(ResourceView resourceView)
			throws PubBusinessException;

	/**
	 * ɾ����Դ
	 * 
	 * @param resourceView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:38
	 */
	public ResourceView delResource(ResourceView resourceView)
			throws PubBusinessException;

	/**
	 * �޸���Դ
	 * 
	 * @param resourceView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:42
	 */
	public ResourceView updateResource(ResourceView resourceView)
			throws PubBusinessException;

	/**
	 * ����������ѯ��Դ
	 * 
	 * @param resQryParaVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:45
	 */
	public List<ResourceView> getResource(ResourQryParaVO resQryParaVO)
			throws PubBusinessException;

	/**
	 * �����˵�
	 * 
	 * @param menuView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:48
	 */
	public MenuView addMenus(MenuView menuView) throws PubBusinessException;

	/**
	 * ɾ���˵�
	 * 
	 * @param menuView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:52
	 */
	public MenuView delMenus(MenuView menuView) throws PubBusinessException;

	/**
	 * �޸Ĳ˵�
	 * 
	 * @param menuView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:55
	 */
	public MenuView updateMenus(MenuView menuView) throws PubBusinessException;

	/**
	 * ����������ѯ�˵�
	 * 
	 * @param menuQryParaVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:20:58
	 */
	public List<MenuView> getMenus(MenusQryParaVO menuQryParaVO)
			throws PubBusinessException;

}
