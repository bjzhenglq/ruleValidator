package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * ��¼�쳣
 * 
 * @author lixln
 * 
 */
public class PubLoginException extends PubBaseException {
	private static final long serialVersionUID = 7250400025270312113L;

	public PubLoginException() {
		super("��¼����");
	}

	public PubLoginException(PubBaseException e) {
		super("��¼����", e);
	}
}
