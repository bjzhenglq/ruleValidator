package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * �ظ���¼�쳣
 * 
 * @author lixln
 * 
 */
public class PubRepeatLoginException extends PubBaseException {
	private static final long serialVersionUID = 5196036887390401151L;

	public PubRepeatLoginException() {
		super("�ظ���¼");
	}

	public PubRepeatLoginException(PubBaseException e) {
		super("�ظ���¼", e);
	}
}
