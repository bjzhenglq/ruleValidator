package com.web.account.dao;

import java.util.List;

import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.page.paravo.UserQryParaVO;
import com.web.rule.persistence.entity.UserVO;

/**
 * 
 * 
 * @author:sunshine
 * @date:2014-1-13 ����2:45:20
 */
public interface IAccountDao {

	/**
	 * �û���¼
	 * 
	 * @param userVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:24:30
	 */
	public UserVO loginUser(UserVO userVO) throws PubBusinessException;

	/**
	 * ��ѯ�����û���Ϣ
	 * 
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:24:34
	 */
	public List<UserVO> qryUser(UserQryParaVO userParaVO)
			throws PubBusinessException;

	/**
	 * �����û�
	 * 
	 * @param userVO
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:24:38
	 */
	public UserVO insertUser(UserVO userVO) throws PubBusinessException;

	/**
	 * �޸��û�
	 * 
	 * @param userVO
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:24:40
	 */
	public void updateUser(UserVO userVO) throws PubBusinessException;

	/**
	 * ɾ���û�
	 * 
	 * @param pkUser
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-23 ����4:24:44
	 */
	public void delUser(String pkUser) throws PubBusinessException;

	/**
	 * �޸�����
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-1-24 ����5:04:38
	 */
	public Boolean updatePassword(String userId, String oldPassword,
			String newPassword) throws PubBusinessException;

}
