package com.web.account.uiview;

import java.io.Serializable;

public class MessageUIView implements Serializable {

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;
	// ��Ϣ��ʾ�ı�ʶ
	private String msgFlag;

	private final String userNameNull = "�������û���";

	private final String userPwdNull = "����������";

	private final String verificationCodeNull = "��������֤��";

	private final String verificationCodeError = "��֤���������������";

	private final String loginError = "��½ʧ�ܣ��û���������������";

	private String errorMsg;

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getUserNameNull() {
		return userNameNull;
	}

	public String getUserPwdNull() {
		return userPwdNull;
	}

	public String getVerificationCodeNull() {
		return verificationCodeNull;
	}

	public String getVerificationCodeError() {
		return verificationCodeError;
	}

	public String getLoginError() {
		return loginError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
