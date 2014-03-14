package com.web.pub.fw.tools;

import com.web.pub.fw.factory.UserContext;

/**
 * ECPϵͳ��������
 * 
 * @author:sunshine
 * @date:2012-5-31 ����07:10:20
 */
public class InvocationInfoProxy {

	private static InvocationInfoProxy instance = new InvocationInfoProxy();

	private final ThreadLocal<UserContext> threadHolder = new ThreadLocal<UserContext>();

	/**
	 * ��ȡһ��ʵ��
	 */
	public static InvocationInfoProxy getInstance() {
		return instance;
	}

	public void setInvocationContext(UserContext context) {
		threadHolder.set(context);
	}

	public void clear() {
		threadHolder.remove();
	}

	private UserContext getUserContext() {
		if (threadHolder != null) {
			return threadHolder.get();
		}
		return null;
	}

	/**
	 * ��ȡ�û�����
	 * 
	 * @return
	 * @author:sunshine
	 * @date:2012-5-31 ����07:18:37
	 */
	public String getPkUser() {
		if (getUserContext() != null) {
			return getUserContext().getUserId();
		}
		return null;
	}

	/**
	 * ��ȡ�û�����
	 * 
	 * @return
	 * @date 2012-6-18 ����10:17:28
	 */
	public String getUserName() {
		if (getUserContext() != null) {
			return getUserContext().getUserName();
		}
		return null;
	}

	/**
	 * ��ȡ�û�code
	 * 
	 * @return
	 * @date 2012-6-18 ����10:17:28
	 */
	public String getUserCode() {
		if (getUserContext() != null) {
			return getUserContext().getUserCode();
		}
		return null;
	}
}
