package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * û�������쳣
 * 
 * @author lixln
 * 
 */
public class PubNotPasswordException extends PubBaseException {
	private static final long serialVersionUID = 7000442887820208229L;

	public PubNotPasswordException() {
		super("û������");
	}

	public PubNotPasswordException(PubBaseException e) {
		super("û������", e);
	}
}
