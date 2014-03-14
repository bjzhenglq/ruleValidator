package com.web.management.service;

import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface IManagementService {
	/*
	 * ��Ⱥͬ������ecpuiproperties�ļ�
	 * @param jsonData �ļ�����json�ַ���
	 * */
	public Map<String,String> uiPropConfigSave(String jsonData);
	
	/*
	 * ��Ⱥͬ�����¸��Ի�ͼƬ�ļ�
	 * @param partFile�ϴ���ͼƬ�ļ�
	 * */
	public String uiManagementPictureSave(CommonsMultipartFile partFile);
}
