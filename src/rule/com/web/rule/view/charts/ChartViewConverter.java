package com.web.rule.view.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.web.rule.view.RusStaticView;

/**
 * ͼ����ͼת��������
 * 
 * @author:sunshine
 * @date:2014-1-15 ����1:36:56
 */
public class ChartViewConverter {

	/**
	 * ��ͳ�ƽ��ת��Ϊ��״ͼ
	 * 
	 * @param resStatViewList
	 * @return
	 * @author:sunshine
	 * @date:2014-1-15 ����1:36:53
	 */
	public static BarChartView conert2BarView(
			List<RusStaticView> resStatViewList) {
		BarChartView barChartView = new BarChartView();
		if (resStatViewList != null) {
			// ����ͼ�����ɵ�������������
			HashMap<String, List<Double>> seriesData = new HashMap<String, List<Double>>();
			// X����������
			HashMap<String, String> axisDataMap = new HashMap<String, String>();
			// ͳ�ƽ�����չ���ִ����������
			HashMap<String, List<RusStaticView>> staticMap = new HashMap<String, List<RusStaticView>>();

			for (RusStaticView rusStaticView : resStatViewList) {
				// ͳ�ƽ�����չ���ִ����������
				List<RusStaticView> staticViewList = staticMap
						.get(rusStaticView.getRecordID());
				if (staticViewList == null) {
					staticViewList = new ArrayList<RusStaticView>();
				}
				staticViewList.add(rusStaticView);
				staticMap.put(rusStaticView.getRecordID(), staticViewList);
				if (!StringUtils.isEmpty(rusStaticView.getRecordDate())) {
					axisDataMap.put(rusStaticView.getRecordID(),
							rusStaticView.getRecordDate());
				}
			}

			Iterator<Entry<String, List<RusStaticView>>> staticIT = staticMap
					.entrySet().iterator();
			List<String> recordIDList = new ArrayList<String>();
			while (staticIT.hasNext()) {
				List<RusStaticView> staticList = staticIT.next().getValue();
				for (RusStaticView rusStaticView : staticList) {
					List<Double> doubleList = seriesData.get(rusStaticView
							.getResultStatu());
					if (doubleList == null) {
						doubleList = new ArrayList<Double>();
					}
					doubleList
							.add(Double.valueOf(rusStaticView.getStatusNum()));
					seriesData.put(rusStaticView.getResultStatu(), doubleList);

					// ����X������
					String recordID = rusStaticView.getRecordID();
					if (!recordIDList.contains(recordID)) {
						recordIDList.add(recordID);
					}
				}
			}

			// ͼ������
			barChartView.setTitleText("����������ͼ");
			// ͼ�����Ʊ�ע
			barChartView.setTitleSubText("�������Թ�����ϵͳ");

			// X����������
			String[] axisData = new String[recordIDList.size()];
			int i = 0;
			for (String recordID : recordIDList) {
				axisData[i++] = axisDataMap.get(recordID);
			}
			barChartView.setxAxisData(axisData);
			Iterator<Entry<String, List<Double>>> it = seriesData.entrySet()
					.iterator();

			// Y��������
			HashMap<String, Double[]> seriesDatas = new HashMap<String, Double[]>();
			while (it.hasNext()) {
				Entry<String, List<Double>> entry = it.next();
				List<Double> doubleList = entry.getValue();
				seriesDatas.put(entry.getKey(),
						doubleList.toArray(new Double[doubleList.size()]));
			}
			barChartView.setSeriesData(seriesDatas);
		}
		return barChartView;
	}

	/**
	 * ��ͳ�ƽ��ת��Ϊ��ͼģ��
	 * 
	 * @param ruleStaticList
	 * @return
	 * @author:sunshine
	 * @date:2014-1-17 ����2:28:49
	 */
	public static PieChartView conert2PieView(List<RusStaticView> ruleStaticList) {
		PieChartView pieChart = new PieChartView();
		if (ruleStaticList != null) {
			HashMap<String, Double> statuMap = new HashMap<String, Double>();
			Double totalRules = Double.valueOf(0);
			for (RusStaticView rusStaticView : ruleStaticList) {
				String statu = rusStaticView.getResultStatu();
				Double number = statuMap.get(statu);
				if (number == null) {
					number = Double.valueOf(0);
				}
				String statuNum = rusStaticView.getStatusNum();
				if (!StringUtils.isEmpty(statuNum)) {
					number += Double.valueOf(statuNum);
					totalRules += number;
				}
				statuMap.put(statu, number);
			}
			pieChart.setSeriesData(statuMap);
			pieChart.setTitleText("����ִ��״̬�ֲ�ͼ");
			pieChart.setTitleSubText("ִ�й���������" + totalRules + "��<br/>ִ��ʱ�䣺"
					+ ruleStaticList.get(0).getRecordDate());
		}
		return pieChart;
	}
}
