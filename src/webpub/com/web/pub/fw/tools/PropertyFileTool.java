package com.web.pub.fw.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * properties�ļ���д���������� ��properties����ܱ����ļ��е�ע�ͼ������ַ�
 * 
 * @author:songjl
 * @date:2013-09-12
 */
public class PropertyFileTool {
	/**
	 * ���ݴ����Map<String,String>�޸�properties�ļ��ж�Ӧ��ֵ
	 * 
	 * @param String
	 *            file
	 * @param Map
	 *            <String, String> map
	 * @return Boolean true success false file didn't exist
	 * */
	public static boolean setProfileString(String file, Map<String, String> map)
			throws IOException {
		File f = new File(file);
		boolean res = false;
		if (f.exists()) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			String outstr = "";
			try {
				isr = new InputStreamReader(new FileInputStream(f), "utf-8");
				br = new BufferedReader(isr);
				String line = "";

				while ((line = br.readLine()) != null) {
					if (line == "") // ���Ϊ��
					{
						outstr += "\n";
						continue;
					}
					if (line.startsWith("#") || line.startsWith("!")) // ���Ϊע��
					{
						outstr += line + "\n";
						continue;
					}
					Boolean flag = false;
					for (String key : map.keySet()) {
						if (line.trim().startsWith(key)) {
							String[] keyandvalue = line.split("=");
							if (map.get(key) instanceof String) {
								outstr += keyandvalue[0].toString() + "="
										+ map.get(key).toString() + "\n";
							} else {
								outstr += keyandvalue[0].toString() + "="
										+ transObj2Str(map.get(key)) + "\n";
							}
							res = true;
							flag = true;
							break;
						}
					}
					if (flag) {
						continue;
					}
					outstr += line + "\n";
				}
			} finally {
				br.close();
				isr.close();
			}
			if (res) {
				OutputStreamWriter write = null;
				BufferedWriter bw = null;
				try {
					write = new OutputStreamWriter(new FileOutputStream(f),
							"utf-8");
					bw = new BufferedWriter(write);
					bw.write(outstr);
				} finally {
					bw.close();
					write.close();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * �Ѷ���ת����json��ʽ���ַ���
	 * 
	 * @param obj
	 * @return
	 * @author:������
	 * @date:2013-3-24 ����08:40:05
	 */
	private static String transObj2Str(Object obj) {
		String s = null;
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, obj);
			s = sw.toString();
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return s;
	}
}
