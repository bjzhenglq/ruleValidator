package com.web.pub.fw.uiview;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * ������ͼ��װ��
 * 
 * @author lixln
 * 
 */
@SuppressWarnings("serial")
public class BaseView implements Serializable {
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
