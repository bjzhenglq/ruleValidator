package com.web.pub.fw.tools;

import java.math.BigDecimal;

/**
 * ���ȴ�����
 * 
 * @author lixln
 * 
 */
public class ScaleHelper {

	/**
	 * ���ȴ�����ȡ�ַ�������
	 * 
	 * @param data
	 * @param scale
	 * @return
	 */
	public static String getString(BigDecimal data, int scale) {
		if (data != null && scale != -1) {
			// �о���
			return data.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
		} else if (data != null && scale == -1) {
			// û����
			return data.toString();
		} else {
			// �����쳣
			throw new RuntimeException(String.format("�����쳣����ֵ����Ϊ%s������Ϊ%s",
					new Object[] { data, scale }));
		}
	}
}
