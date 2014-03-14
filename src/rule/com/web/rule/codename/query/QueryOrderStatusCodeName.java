package com.web.rule.codename.query;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.web.pub.codename.iquery.IQueryCodeName;
import com.web.pub.comp.combobox.ComboBoxItem;
import com.web.pub.comp.combobox.ComboBoxModel;
import com.web.pub.comp.combobox.IComboBoxModel;
import com.web.pub.fw.exception.PubBaseException;

/**
 * ���ö���״̬
 * 
 * @author,sunshine
 * @date,2013-2-5 ����1,44,33
 * 
 */
public class QueryOrderStatusCodeName implements IQueryCodeName {

	@Override
	public IComboBoxModel getCodeNameModel(String param)
			throws PubBaseException {

		HashMap<String, ComboBoxItem> orderStatusMap = new HashMap<String, ComboBoxItem>();
		IComboBoxModel model = new ComboBoxModel();
		ComboBoxItem item = new ComboBoxItem("ִ��ʧ��", "ִ��ʧ��");
		model.addItem(item);
		orderStatusMap.put(item.getCode(), item);
		item = new ComboBoxItem("ִ�гɹ�", "ִ�гɹ�");
		model.addItem(item);
		orderStatusMap.put(item.getCode(), item);
		item = new ComboBoxItem("����", "����");
		model.addItem(item);
		orderStatusMap.put(item.getCode(), item);
		item = new ComboBoxItem("ִ���쳣", "ִ���쳣");
		model.addItem(item);
		orderStatusMap.put(item.getCode(), item);
		// �����ȡĳЩ�ض�״̬�Ķ���״̬��Ϣ
		if (!StringUtils.isEmpty(param)) {
			String[] orders = param.split(",");
			model = new ComboBoxModel();
			for (int i = 0; i < orders.length; i++) {
				ComboBoxItem orderItem = orderStatusMap.get(orders[i]);
				if (orderItem != null) {
					model.addItem(orderItem);
				}
			}
			return model;
		}
		return model;
	}
}
