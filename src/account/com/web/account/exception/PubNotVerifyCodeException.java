package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * ��¼����֤���쳣
 * 
 * @author lixln
 * 
 */
public class PubNotVerifyCodeException extends PubBaseException {
	private static final long serialVersionUID = 933326572614778517L;

	public PubNotVerifyCodeException() {
		// ��δ���ǹ��ʻ�
		super("û����֤��");
	}

	public PubNotVerifyCodeException(PubBaseException e) {
		// ��δ���ǹ��ʻ�
		super("û����֤��", e);
	}
}
