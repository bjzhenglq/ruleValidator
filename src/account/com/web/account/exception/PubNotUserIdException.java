package com.web.account.exception;

import com.web.pub.fw.exception.PubBaseException;

/**
 * û���û����쳣
 * 
 * @author lixln
 * 
 */
public class PubNotUserIdException extends PubBaseException {
	private static final long serialVersionUID = 1532840905506326789L;

	public PubNotUserIdException() {
		super("û���û���");
	}

	public PubNotUserIdException(PubBaseException e) {
		super("û���û���", e);
	}
}
