package com.web.pub.fw.tools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ��ܹ�����
 * 
 * @author zhenghb
 * @date 2012-3-26 ����01:28:44
 */
@RequestMapping("file")
public class EsFwUtil {

	/**
	 * ��־
	 */
	private static final Log logger = LogFactory.getLog(EsFwUtil.class);
	private static final String FIEL_SEPARATOR = File.separator;

	// ���������ʱ������Դ
	private static char[] codeSequence = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 't', 'u', 'v', 'w',
			'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * ajax����ֵ
	 * 
	 * @param response
	 * @param content
	 */
	public static void render(HttpServletResponse response, String content) {
		try {
			response.setContentType("text/html;charset=UTF-8 ");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * �ж��ַ����Ƿ�Ϊnull���߿��ַ������������ôҲ���ؿ��ַ���
	 * 
	 * @param string
	 * @return
	 */
	public static String ifNull(Object string) {
		String value = "";
		if (string != null) {
			value = string.toString();
		}
		return value;
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * �ж�List�Ƿ�Ϊ��
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isListEmpty(List<?> value) {
		return value == null || value.isEmpty() || value.size() == 0;
	}

	/**
	 * ��ȡ��ǰ��ʱ����Ϣ�����������ں�ʱ�䣩
	 */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * ���ַ���ת��DATE
	 * 
	 * @param datestr
	 *            �����ַ���
	 * @param pattern
	 *            ��ʽ
	 * @return
	 */
	public static Date parseDate(String datestr, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(datestr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ����ָ�����ȵ����������������֤��ʱʹ��
	 * 
	 * @param length
	 * @return
	 * @author:sunshine
	 * @date:2012-4-12 ����10:02:17
	 */
	public static String genNumPassword(int length) {
		// ����һ���������������
		Random random = new Random();
		// randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤��
		StringBuffer randomCode = new StringBuffer();
		for (int i = 0; i < length; i++) {
			// �õ������������֤�����֡�
			String strRand = String.valueOf(codeSequence[random.nextInt(32)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}

	/**
	 * ��ȡsql�ļ�·��
	 * 
	 * @return
	 * @author:sunshine
	 * @date:2014-1-20 ����4:08:17
	 */
	public static String getInitSqlFilePath(String fileName) {
		StringBuffer initSqlFilePath = new StringBuffer();
		String rootPath = System.getProperty("webapp.root");
		initSqlFilePath.append(rootPath);
		initSqlFilePath.append(FIEL_SEPARATOR).append("WEB-INF");
		initSqlFilePath.append(FIEL_SEPARATOR).append("config");
		initSqlFilePath.append(FIEL_SEPARATOR).append(fileName);
		return initSqlFilePath.toString();
	}
}
