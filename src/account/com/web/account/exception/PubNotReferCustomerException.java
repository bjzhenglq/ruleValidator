package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * û�й����ͻ��쳣
 * 
 * @author lixln
 * 
 */
public class PubNotReferCustomerException extends PubBaseException {
	private static final long serialVersionUID = 7362983235536406203L;

	public PubNotReferCustomerException() {
		super("���û�û�й����ͻ�");
	}

	public PubNotReferCustomerException(PubBaseException e) {
		super("���û�û�й����ͻ�", e);
	}
}
