package com.web.rule.dao;

import java.util.List;

import com.web.pub.fw.exception.PubBaseException;
import com.web.rule.page.paravo.ReportDetailQryPara;
import com.web.rule.persistence.entity.ExecuteRecordVO;
import com.web.rule.persistence.entity.ExecuteUnitVO;
import com.web.rule.persistence.entity.RuleExecuteResultVO;
import com.web.rule.view.RusStaticView;

public interface IRuleDao {

	public List<RusStaticView> queryRuleStatic(ReportDetailQryPara qryPara)
			throws PubBaseException;

	/**
	 * ��ѯ����У���¼
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-2 ����11:07:05
	 */
	public List<ExecuteRecordVO> qryExecuteRecord() throws PubBaseException;

	/**
	 * ��ѯ����У����ϸ
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-2 ����11:06:57
	 */
	public List<RuleExecuteResultVO> qryExecuteResult(
			ReportDetailQryPara qryPara) throws PubBaseException;

	/**
	 * ɾ������ִ�м�¼
	 * 
	 * @param recordID
	 * @author:sunshine
	 * @date:2014-1-7 ����3:31:19
	 */
	public void delExecRecord(String recordID) throws PubBaseException;

	/**
	 * ����ִ�н��ID��ѯ��Ϣ
	 * 
	 * @param resultId
	 * @return
	 * @author:sunshine
	 * @date:2014-1-8 ����4:58:26
	 */
	public RuleExecuteResultVO queryResultDetailByID(String resultId)
			throws PubBaseException;

	/**
	 * ��ѯ���в�Ʒ
	 * 
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-16 ����3:55:53
	 */
	public List<ExecuteUnitVO> qryAllProd() throws PubBaseException;
}
