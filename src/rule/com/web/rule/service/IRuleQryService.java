package com.web.rule.service;

import java.util.List;

import com.web.pub.fw.exception.PubBaseException;
import com.web.rule.page.paravo.ReportDetailQryPara;
import com.web.rule.page.paravo.StaticQryPara;
import com.web.rule.persistence.entity.ExecuteUnitVO;
import com.web.rule.view.ExecuteRecordView;
import com.web.rule.view.RuleExecuteResultView;
import com.web.rule.view.RusStaticView;

public interface IRuleQryService {

	/**
	 * ��ѯ����ͳ����Ϣ
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2013-12-30 ����1:45:21
	 */
	public List<RusStaticView> qryRuleStaticInfo(ReportDetailQryPara qryPara)
			throws PubBaseException;

	/**
	 * ��ѯ����У���¼
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-2 ����11:07:05
	 */
	public List<ExecuteRecordView> qryExecuteRecord(StaticQryPara qryPara)
			throws PubBaseException;

	/**
	 * ��ѯ����У����ϸ
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-2 ����11:07:52
	 */
	public List<RuleExecuteResultView> qryExecuteResult(
			ReportDetailQryPara qryPara) throws PubBaseException;

	/**
	 * ɾ������ִ�м�¼
	 * 
	 * @param recordID
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-7 ����3:30:07
	 */
	public void delExecRecord(String recordID) throws PubBaseException;

	/**
	 * ����ִ�н��ID��ѯ��Ϣ
	 * 
	 * @param resultId
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-8 ����4:56:52
	 */
	public RuleExecuteResultView queryResultDetailByID(String resultId)
			throws PubBaseException;

	/**
	 * ��ѯ���в�Ʒ
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-16 ����3:53:13
	 */
	public List<ExecuteUnitVO> qryAllProd() throws PubBaseException;
}
