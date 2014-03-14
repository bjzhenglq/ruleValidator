package com.web.rule.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.pub.fw.exception.PubBaseException;
import com.web.pub.fw.service.PubBaseService;
import com.web.rule.dao.IRuleDao;
import com.web.rule.page.paravo.ReportDetailQryPara;
import com.web.rule.page.paravo.StaticQryPara;
import com.web.rule.persistence.entity.ExecuteRecordVO;
import com.web.rule.persistence.entity.ExecuteUnitVO;
import com.web.rule.persistence.entity.RuleExecuteResultVO;
import com.web.rule.service.IRuleQryService;
import com.web.rule.view.ExecuteRecordView;
import com.web.rule.view.RuleExecuteResultView;
import com.web.rule.view.RusStaticView;

@Service
public class RuleQryServiceImpl extends PubBaseService implements
		IRuleQryService {

	@Resource
	private IRuleDao ruleDao;

	/**
	 * ��ѯ����ͳ����Ϣ
	 * 
	 * @author:sunshine
	 * @date:2013-12-30 ����1:46:01
	 * @see com.web.rule.service.IRuleQryService#qryRuleStaticInfo()
	 */
	@Override
	public List<RusStaticView> qryRuleStaticInfo(ReportDetailQryPara qryPara)
			throws PubBaseException {
		List<RusStaticView> statViewList = ruleDao.queryRuleStatic(qryPara);
		fillByStatu(statViewList);
		return statViewList;
	}

	/**
	 * ����ȫ��״̬��ȫ����
	 * 
	 * @param statViewList
	 * @author:sunshine
	 * @date:2014-1-15 ����2:43:48
	 */
	private void fillByStatu(List<RusStaticView> statViewList) {
		if (statViewList != null) {
			HashMap<String, HashMap<String, RusStaticView>> recordMap = new HashMap<String, HashMap<String, RusStaticView>>();
			List<String> statuList = new ArrayList<String>();
			// ��ѯ���������п���״̬
			for (RusStaticView rusStaticView : statViewList) {
				String status = rusStaticView.getResultStatu();
				String recordID = rusStaticView.getRecordID();
				if (!statuList.contains(status)) {
					statuList.add(rusStaticView.getResultStatu());
				}
				HashMap<String, RusStaticView> statuMap = recordMap
						.get(recordID);
				if (statuMap == null) {
					statuMap = new HashMap<String, RusStaticView>();
				}
				statuMap.put(status, rusStaticView);
				recordMap.put(recordID, statuMap);
			}
			Iterator<Entry<String, HashMap<String, RusStaticView>>> recordIT = recordMap
					.entrySet().iterator();
			while (recordIT.hasNext()) {
				Entry<String, HashMap<String, RusStaticView>> entry = recordIT
						.next();
				HashMap<String, RusStaticView> statuMap = entry.getValue();
				for (String statu : statuList) {
					if (statuMap.get(statu) == null) {
						RusStaticView staicView = new RusStaticView();
						staicView.setRecordID(entry.getKey());
						staicView.setStatusNum("0");
						staicView.setResultStatu(statu);
						statViewList.add(staicView);
					}
				}
			}
		}
	}

	/**
	 * ��ѯ����У���¼
	 * 
	 * @author:sunshine
	 * @date:2014-1-2 ����11:08:13
	 * @see com.web.rule.service.IRuleQryService#qryExecuteRecord()
	 */
	@Override
	public List<ExecuteRecordView> qryExecuteRecord(StaticQryPara qryPara)
			throws PubBaseException {
		List<ExecuteRecordVO> recourdVOList = ruleDao.qryExecuteRecord();
		List<ExecuteRecordView> viewList = convert2View(recourdVOList);
		return viewList;
	}

	/**
	 * ��ѯ����У����ϸ
	 * 
	 * @author:sunshine
	 * @date:2014-1-2 ����11:08:16
	 * @see com.web.rule.service.IRuleQryService#qryExecuteResult()
	 */
	@Override
	public List<RuleExecuteResultView> qryExecuteResult(
			ReportDetailQryPara qryPara) throws PubBaseException {
		List<RuleExecuteResultVO> recourdVOList = ruleDao
				.qryExecuteResult(qryPara);
		List<RuleExecuteResultView> viewList = convert2ReulutView(recourdVOList);
		return viewList;
	}

	/**
	 * ɾ������ִ�м�¼
	 * 
	 * @author:sunshine
	 * @date:2014-1-7 ����3:30:39
	 * @see com.web.rule.service.IRuleQryService#delExecRecord(java.lang.String)
	 */
	@Override
	public void delExecRecord(String recordID) throws PubBaseException {
		ruleDao.delExecRecord(recordID);
	}

	/**
	 * ����ִ�н��ID��ѯ��Ϣ
	 * 
	 * @param resultId
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-8 ����4:56:52
	 */
	@Override
	public RuleExecuteResultView queryResultDetailByID(String resultId)
			throws PubBaseException {
		RuleExecuteResultVO resultVO = ruleDao.queryResultDetailByID(resultId);
		// ReportDetailQryPara qryPara = new ReportDetailQryPara();
		// qryPara.setRecordID(resultId);
		// qryExecuteResult(qryPara);
		return convert2View(resultVO);
	}

	/**
	 * ��ѯ���в�Ʒ
	 * 
	 * @author:sunshine
	 * @date:2014-1-16 ����3:55:38
	 * @see com.web.rule.service.IRuleQryService#qryAllProd()
	 */
	@Override
	public List<ExecuteUnitVO> qryAllProd() throws PubBaseException {
		return ruleDao.qryAllProd();
	}

}
