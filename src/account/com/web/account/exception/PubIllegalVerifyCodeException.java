package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * ��֤������쳣
 * 
 * @author lixln
 * 
 */
public class PubIllegalVerifyCodeException extends PubBaseException {
	private static final long serialVersionUID = -2072951997096104148L;

	public PubIllegalVerifyCodeException() {
		// ��δ���ǹ��ʻ�
		super("��֤�����");
	}

	public PubIllegalVerifyCodeException(PubBaseException e) {
		// ��δ���ǹ��ʻ�
		super("��֤�����", e);
	}
}
