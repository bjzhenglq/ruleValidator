package com.web.account.service;

import java.util.List;

import com.web.account.uiview.UserUIView;
import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.page.paravo.UserQryParaVO;

/**
 * ��ȡ��Դ����
 * 
 * @author:sunshine
 * @date:2014-1-13 ����2:43:29
 */
public interface IAccountService {

	/**
	 * �û���¼
	 * 
	 * @param userView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:15:08
	 */
	public UserUIView loginUserView(UserUIView userView)
			throws PubBusinessException;

	/**
	 * ��ѯ�����û���Ϣ
	 * 
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-13 ����2:46:42
	 */
	public List<UserUIView> qryUserView(UserQryParaVO userParaVO)
			throws PubBusinessException;

	/**
	 * �����û�
	 * 
	 * @param userView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:14:25
	 */
	public UserUIView insertUserView(UserUIView userView)
			throws PubBusinessException;

	/**
	 * �޸��û�
	 * 
	 * @param userView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:14:22
	 */
	public UserUIView updateUserView(UserUIView userView)
			throws PubBusinessException;

	/**
	 * ɾ���û�
	 * 
	 * @param userView
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:14:20
	 */
	public UserUIView delUserView(UserUIView userView)
			throws PubBusinessException;

	/**
	 * �޸�����
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-24 ����4:58:19
	 */
	public Boolean updatePassword(String userId, String oldPassword,
			String newPassword) throws PubBusinessException;

}
