package com.web.management.serivce.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.pub.fw.tools.PropertyFileTool;

/**
 * ͬ��uiproperties�ļ�����
 * 
 * @author songhlc
 * @date 2013-11-21
 */
public class ECPUIPorpertiesUpdateCommand extends AbstractCommand {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(ECPCacheRemoveCommand.class);
	private final String jsonData;

	public ECPUIPorpertiesUpdateCommand(String jsonData) {
		super();
		this.jsonData = jsonData;
	}

	@Override
	public String getId() {
		return "ECPUIPorpertiesUpdateCommand";
	}

	@Override
	public Object run() {
		Map<String, String> map = new HashMap<String, String>();
		InputStream is = null;
		InputStreamReader isr = null;
		Boolean result = false;
		try {
			// ��ȡӦ���µ������ļ�
			String path = ServerRuntime.getECPAppHome()
					+ "/WEB-INF/classes/ecpui.properties";
			JSONObject jsonObj = JSONObject.fromObject(this.jsonData);
			@SuppressWarnings("unchecked")
			Map<String, String> jsonMap = jsonObj;
			result = PropertyFileTool.setProfileString(path, jsonMap);
		} catch (IOException e) {
			logger.error("��ȡϵͳ���������ļ�ecpsystem.properties����!", e);
		} finally {
			IOUtils.closeQuietly(isr);
			IOUtils.closeQuietly(is);
		}
		if (result) {
			map.put("result", "����ɹ�");
		} else {
			map.put("result", "��ȡϵͳ���������ļ�ecpsystem.properties����!");
		}
		return map;
	}

	@Override
	public String getName() {
		return "ECPUIPorpertiesUpdateCommand";
	}

}
