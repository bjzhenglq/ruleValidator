package com.web.pub.fw.factory;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * 
 * @author:sunshine
 * @date:2014-1-11 ����1:38:55
 */
public class UserContext implements Serializable {
	private static final long serialVersionUID = -7109144523264236997L;

	public UserContext() {
		// ����֮�����ϴ��޸�����Ϊϵͳ��ǰ����
		this.lastDate = Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * �û�id
	 */
	private String userId;

	/**
	 * �û�����
	 */
	private String userCode;
	/**
	 * �û�����
	 */
	private String userName;
	/**
	 * ����
	 */
	private String userPassword;

	/**
	 * �û����� 0-���Ź���Ա���� 1-��ͨ�û����� 2-���׹���Ա
	 * 
	 * @see nc.login.vo.INCUserTypeConstant �û�����
	 */
	private Integer userType;

	/**
	 * �Ự�ϴη�������
	 */
	private long lastDate;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserType() {
		return userType;
	}

	public long getLastDate() {
		return lastDate;
	}

	public void setLastDate(long lastDate) {
		this.lastDate = lastDate;
	}

}
