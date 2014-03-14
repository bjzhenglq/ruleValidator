package com.web.management.serivce.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ͬ��ecp���Ի�ͼƬ�ļ�����
 * 
 * @author songhlc
 * @date 2013-11-21
 */
public class ECPUIPictureSaveCommand extends AbstractCommand {
	private static final Log logger = LogFactory
			.getLog(ECPUIPictureSaveCommand.class);
	private static final long serialVersionUID = 8521949207090016592L;
	/*
	 * �ϴ����ļ�
	 */
	private final byte[] resourceBytes;
	/*
	 * ͼƬ�ļ�ʱ�����Ϊ�ļ�����һ����
	 */
	private final String strTimeStamp;
	/*
	 * ͼƬ�ļ�ʱ�����Ϊ�ļ�����һ����
	 */
	private final String filename;

	/*
	 * Ĭ�Ϲ��캯��
	 */
	public ECPUIPictureSaveCommand(byte[] resourceBytes, String filename,
			String strTimeStamp) {
		super();
		this.resourceBytes = resourceBytes;
		this.strTimeStamp = strTimeStamp;
		this.filename = filename;
	}

	@Override
	public String getId() {
		return "ECPUIPictureSaveCommand";
	}

	@Override
	public Object run() {
		String fileListString = "";
		// ��ȡͼƬ����·��
		String url = ServerRuntime.getECPAppHome()
				+ "/component/ecp/common/management/customsetting/src/theme/default/img";
		File uploadfile = new File(url);
		if (!uploadfile.exists()) {
			uploadfile.mkdirs();
		}
		fileListString += this.filename + "?ts=" + this.strTimeStamp + " ";
		// �ļ�����·��
		String filepath = uploadfile + "\\" + this.filename;
		FileOutputStream outfile = null;
		try {
			outfile = new FileOutputStream(filepath);
			IOUtils.copy(new ByteArrayInputStream(resourceBytes), outfile);
		} catch (FileNotFoundException e) {
			logger.error("û���ҵ��ļ�:" + filepath, e);
		} catch (IOException e) {
			logger.error("�ļ�:" + filepath + " д�����", e);
		} finally {
			IOUtils.closeQuietly(outfile);
		}
		return fileListString;
	}

	@Override
	public String getName() {
		return "ECPUIPictureSaveCommand";
	}

}
