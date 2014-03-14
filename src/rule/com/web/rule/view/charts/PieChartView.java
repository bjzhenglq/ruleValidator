package com.web.rule.view.charts;

import java.io.Serializable;
import java.util.HashMap;

/**
 * ��ͼ��ͼģ��
 * 
 * @author:sunshine
 * @date:2014-1-15 ����1:28:34
 */
public class PieChartView extends PubChartView implements Serializable {

	/**
	 * @author:sunshine
	 * @date:2014-1-17 ����2:07:48
	 */
	private static final long serialVersionUID = 3183728419601073999L;

	// ����ʾ��
	private HashMap<String, Double> seriesData;

	public HashMap<String, Double> getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(HashMap<String, Double> seriesData) {
		this.seriesData = seriesData;
	}

}
