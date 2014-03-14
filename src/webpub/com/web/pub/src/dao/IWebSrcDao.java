package com.web.pub.src.dao;

import java.util.List;

import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.page.paravo.MenusQryParaVO;
import com.web.pub.page.paravo.ResourQryParaVO;
import com.web.rule.persistence.entity.MenuVO;
import com.web.rule.persistence.entity.ResourceVO;

/**
 * 
 * 
 * @author:sunshine
 * @date:2014-1-13 ����2:45:20
 */
public interface IWebSrcDao {

	/**
	 * ��ѯ���и��ڵ�
	 * 
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-13 ����2:49:48
	 */
	public List<MenuVO> qryRootMenuVO(MenusQryParaVO qryParaVO)
			throws PubBusinessException;

	/**
	 * ��ѯ���ж����˵����˵�ֻ֧��������
	 * 
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-13 ����2:49:59
	 */
	public List<MenuVO> qryLeafMenuVO() throws PubBusinessException;

	/**
	 * ����������ѯ�˵�VO
	 * 
	 * @param menuID
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-12 ����9:37:46
	 */
	public MenuVO qryMenuVOByID(String menuID) throws PubBusinessException;

	/**
	 * ����Ԥ�ýű�
	 * 
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-21 ����3:20:01
	 */
	public void initMenuData() throws PubBusinessException;

	/**
	 * ������Դ
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:17
	 */
	public ResourceVO addResource(ResourceVO recourceVO)
			throws PubBusinessException;

	/**
	 * ɾ����Դ
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:14
	 */
	public ResourceVO delResource(ResourceVO recourceVO)
			throws PubBusinessException;

	/**
	 * �޸���Դ
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:11
	 */
	public ResourceVO updateResource(ResourceVO recourceVO)
			throws PubBusinessException;

	/**
	 * ����������ѯ��Դ
	 * 
	 * @param resQryParaVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:08
	 */
	public List<ResourceVO> getResource(ResourQryParaVO resQryParaVO)
			throws PubBusinessException;

	/**
	 * �����˵�
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:05
	 */
	public MenuVO addMenus(MenuVO menuVO) throws PubBusinessException;

	/**
	 * ɾ���˵�
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:34:02
	 */
	public MenuVO delMenus(MenuVO menuVO) throws PubBusinessException;

	/**
	 * �޸Ĳ˵�
	 * 
	 * @param recourceVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:33:59
	 */
	public MenuVO updateMenus(MenuVO menuVO) throws PubBusinessException;

	/**
	 * ����������ѯ�˵�
	 * 
	 * @param menuQryParaVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-11 ����3:33:56
	 */
	public List<MenuVO> getMenus(MenusQryParaVO menuQryParaVO)
			throws PubBusinessException;

	/**
	 * ����������ѯ�˵�
	 * 
	 * @param sourceID
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-12 ����2:49:49
	 */
	public ResourceVO qrySourceVOByID(String sourceID)
			throws PubBusinessException;
}
