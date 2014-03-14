package com.web.management.serivce.impl;

import java.util.Calendar;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.web.management.service.IManagementService;

public class ManagementServiceImpl implements IManagementService {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> uiPropConfigSave(String jsonData) {
		ECPUIPorpertiesUpdateCommand command = new ECPUIPorpertiesUpdateCommand(
				jsonData);
		return (Map<String, String>) CommandRunner.runRequestReply(command);
	}

	@Override
	public String uiManagementPictureSave(CommonsMultipartFile partFile) {
		Calendar cdCalendar = Calendar.getInstance();
		// ������Ƭʱ�����Ϊ�ļ�����һ����
		String strTimeStamp = String.valueOf(cdCalendar.getTimeInMillis());
		// ��ȡ�ļ�
		byte[] resourceBytes = partFile.getBytes();
		// ��ȡ�ļ���չ��
		int index = partFile.getOriginalFilename().lastIndexOf(".");
		String fileShortName = partFile.getOriginalFilename().substring(
				index + 1);
		// ��ȡ�����ļ���
		String filename = partFile.getFileItem().getFieldName() + strTimeStamp
				+ "." + fileShortName;
		ECPUIPictureSaveCommand command = new ECPUIPictureSaveCommand(
				resourceBytes, filename, strTimeStamp);
		return (String) CommandRunner.runRequestReply(command);
	}

}
