package com.web.pub.fw.exception;

/**
 * �쳣��ʾ��Ϣö����
 * 
 * @author:sunshine
 * @date:2014-2-11 ����1:40:19
 */
public enum PubExceptionMessage {

	PRODUCT_NOT_FOUND {
		@Override
		public String getDescription() {
			return "�Բ���û���ҵ���Ӧ����Ʒ��";
		}
	}, // ö��ֵ���ŷָ�

	SAMPLE {
		@Override
		public String getDescription() {
			return "����";
		}
	};// ö��ֵ����ʱ�÷ֺ�

	// �������󷽷�
	public abstract String getDescription();
}
