package com.web.account.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.web.account.uiview.UserUIView;
import com.web.pub.fw.exception.PubBaseException;

/**
 * �ͻ���Ϣsupprot
 * 
 * @since 6.1
 * @version 2012-4-20 ����02:57:12
 * @author ��־��
 */
@Service
public class AccountControllerSupport {

	/**
	 * ��ȡ�˻���Ϣ��������ǰ���˻���Ϣչʾ��
	 * 
	 * @param userView
	 *            �û�view
	 * @return �˻�view
	 * @throws EcpBaseException
	 */
	public UserUIView getAccount(UserUIView userView) throws PubBaseException {
		// ��ȡ���ݷ���ʵ��
		// IQryCustInfoService service =
		// ECPServiceFactory.getCustInfoQryService();
		// // ����һ�� �ͻ�id
		// final String customerId = userView.getCustomerid();
		// Assert.hasText(customerId, "��¼�û�û�й����ͻ�");
		// // ������������id
		// String gourpId = EcpInvocationInfoProxy.getInstance().getPkGroup();
		// // ��ѯ�ͻ�
		// CustomerPOJO customerPOJO = service.queryCustomer(customerId,
		// gourpId);
		// Assert.notNull(customerPOJO, "��¼�û�û�й����ͻ�");
		return userView;
	}

	/**
	 * ��������
	 * 
	 * @param userId
	 *            �û�id
	 * @param oldPassword
	 *            ������
	 * @param newPassword
	 *            ������
	 * @return
	 * @throws EcpBaseException
	 */
	public Map<String, String> updatePassword(String userId,
			String oldPassword, String newPassword) throws PubBaseException {

		// // ��ȡ���ݷ���ʵ��
		// IUserMaintainService service = ECPServiceFactory
		// .getUserMaintainService();

		Map<String, String> message = new HashMap<String, String>();

		// try {
		// service.updatePassword(userId, oldPassword, newPassword);
		// message.put(IUserConstant.PWDUPD_IS_SUCCESS, "1");
		// message.put(IUserConstant.PWDUPD_MESSAGE,
		// IUserConstant.PWDUPD_SUCCESS_MSG);
		// } catch (EcpBaseException e) {
		// message.put(IUserConstant.PWDUPD_IS_SUCCESS, "0");
		// message.put(IUserConstant.PWDUPD_MESSAGE, e.getMessage());
		// }
		return message;
	}

	/**
	 * ��������
	 * 
	 * @param userName
	 * @return
	 * @throws EcpBaseException
	 * @author:sunshine
	 * @date:2012-5-2 ����03:16:33
	 */
	public String forgetPwd(String userName) throws PubBaseException {
		// return ECPServiceFactory.getUserMaintainService().forgetPassword(
		// userName, null);
		return userName;
	}
}
