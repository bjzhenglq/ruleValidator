package com.web.rule.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.web.pub.fw.exception.PubBaseException;
import com.web.rule.dao.IRuleDao;
import com.web.rule.page.paravo.ReportDetailQryPara;
import com.web.rule.persistence.entity.ExecuteRecordVO;
import com.web.rule.persistence.entity.ExecuteUnitVO;
import com.web.rule.persistence.entity.RuleDefinitionVO;
import com.web.rule.persistence.entity.RuleExecuteResultVO;
import com.web.rule.view.RusStaticView;

@Repository
@SuppressWarnings({ "unchecked", })
public class RuleDaoImpl extends HibernateDaoSupport implements IRuleDao {

	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * ���ռ�¼��״̬ͳ������
	 * 
	 * @author:sunshine
	 * @date:2014-1-2 ����11:06:04
	 * @see com.web.rule.dao.IRuleDao#queryRuleStatic()
	 */
	@Override
	public List<RusStaticView> queryRuleStatic(ReportDetailQryPara qryPara)
			throws PubBaseException {
		List<RusStaticView> retList = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select executerecord_recordid,result ,count(*),exe_begin_time ");
		sb.append("  from rv_rule_result ");
		sb.append(" right join rv_execute_record   ");
		sb.append("    on executerecord_recordid=recordid    ");
		sb.append(" right join rv_execute_unit    ");
		sb.append("    on unitId=rv_rule_result.executeunit_unitid    ");
		sb.append(" where 1=1                    ");
		if (!StringUtils.isEmpty(qryPara.getRecordID())) {
			sb.append(" and executerecord_recordid=? ");
		}
		if (!StringUtils.isEmpty(qryPara.getProduct())) {
			sb.append(" and prod_code=? ");
		}
		sb.append(" group by executerecord_recordid,result,exe_begin_time");
		sb.append(" order by exe_begin_time desc");
		SQLQuery qry = getSession().createSQLQuery(sb.toString());
		int i = 0;
		if (!StringUtils.isEmpty(qryPara.getRecordID())) {
			qry.setParameter(i++, qryPara.getRecordID());
		}
		if (!StringUtils.isEmpty(qryPara.getProduct())) {
			qry.setParameter(i++, qryPara.getProduct());
		}
		List<Object> objList = qry.list();
		if (objList != null) {
			retList = new ArrayList<RusStaticView>();
			for (Object object : objList) {
				Object[] obj = (Object[]) object;
				RusStaticView staticView = new RusStaticView();
				if (obj[0] != null) {
					staticView.setRecordID((String) obj[0]);
					staticView.setResultStatu((String) obj[1]);
					staticView.setStatusNum(obj[2].toString());
					staticView.setRecordDate(((Timestamp) obj[3]).toString());
					retList.add(staticView);
				}
			}
		}
		return retList;
	}

	/**
	 * ��ѯ����У���¼
	 * 
	 * @author:sunshine
	 * @date:2014-1-2 ����11:05:58
	 * @see com.web.rule.dao.IRuleDao#qryExecuteRecord()
	 */
	@Override
	public List<ExecuteRecordVO> qryExecuteRecord() throws PubBaseException {
		List<ExecuteRecordVO> ruleVOList = null;
		try {
			ruleVOList = getSession().createQuery(
					" from ExecuteRecordVO order by exeBeginTime desc").list();
		} catch (HibernateException e) {

		}
		return ruleVOList;
	}

	/**
	 * ��ѯ����У����ϸ
	 * 
	 * @author:sunshine
	 * @date:2014-1-2 ����11:06:01
	 * @see com.web.rule.dao.IRuleDao#qryExecuteResult()
	 */
	@Override
	public List<RuleExecuteResultVO> qryExecuteResult(
			ReportDetailQryPara qryPara) throws PubBaseException {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.resultid,                        ");
		sb.append("       a.executeunit_unitid,              ");
		sb.append("       a.ruledefinition_ruleid,           ");
		sb.append("       a.executerecord_recordid,          ");
		sb.append("       a.spec_params,                     ");
		sb.append("       a.passflag,                        ");
		sb.append("       a.result,                          ");
		sb.append("       b.descdetails,                     ");
		sb.append("       c.comp_code,                       ");
		sb.append("       c.module_code,                     ");
		sb.append("       c.prod_code,                       ");
		sb.append("       b.relatedissueid                   ");
		sb.append("  from rv_rule_result a                   ");
		sb.append("  join rv_rules b                         ");
		sb.append("    on a.ruledefinition_ruleid = b.ruleid ");
		sb.append("  join rv_execute_unit c                   ");
		sb.append("    on a.executeunit_unitid = c.unitid    ");
		sb.append(" where 1=1                                ");

		// sb = new StringBuffer(
		// "select new RuleExecuteResultVO (rs resultId,rs executeUnit,rs ruleDefinition,rs executeRecord,rs specParams ,rs passFlag,rs result) from RuleExecuteResultVO rs where 1=1");

		// ����ִ�м�¼����
		if (!StringUtils.isEmpty(qryPara.getRecordID())) {
			sb.append(" and a.executerecord_recordid=?");
			// sb.append(" and rs.executeRecord.recordId=?");
		}
		// ����ִ��״̬
		if (!StringUtils.isEmpty(qryPara.getRuleStatus())) {
			sb.append(" and  a.result=?");
			// sb.append(" and  rs.result=?");
		}
		// ����������Ʒ
		if (!StringUtils.isEmpty(qryPara.getProduct())) {
			sb.append(" and  c.prod_code=?");
			// sb.append(" and  rs.executeUnit.prodCode=?");
		}
		Query qry = getSession().createSQLQuery(sb.toString());
		// Query qry = getSession().createQuery(sb.toString());
		int i = 0;
		// ����ִ�м�¼����
		if (!StringUtils.isEmpty(qryPara.getRecordID())) {
			qry.setParameter(i++, qryPara.getRecordID());
		}
		// ����ִ��״̬
		if (!StringUtils.isEmpty(qryPara.getRuleStatus())) {
			qry.setParameter(i++, qryPara.getRuleStatus());
		}
		// ����������Ʒ
		if (!StringUtils.isEmpty(qryPara.getProduct())) {
			qry.setParameter(i++, qryPara.getProduct());
		}
		List<RuleExecuteResultVO> ruleVOList = null;
		try {
			ruleVOList = qry.list();
			List<Object[]> list = qry.list();
			if (list != null && list.size() > 0) {
				ruleVOList = new ArrayList<RuleExecuteResultVO>();
				for (Object[] objs : list) {
					RuleExecuteResultVO vo = new RuleExecuteResultVO();
					vo.setResultId(objs[0].toString());
					if (objs[4] != null) {
						vo.setSpecParams(objs[4].toString());
					}
					if (objs[5] != null) {
						vo.setPassFlag(objs[5].toString());
					}
					if (objs[6] != null) {
						vo.setResult(objs[6].toString());
					}
					ExecuteUnitVO unitVO = new ExecuteUnitVO();
					unitVO.setUnitId(objs[1].toString());
					if (objs[8] != null) {
						unitVO.setCompCode(objs[8].toString());
					}
					if (objs[9] != null) {
						unitVO.setModuleCode(objs[9].toString());
					}
					if (objs[10] != null) {
						unitVO.setProdCode(objs[10].toString());
					}
					vo.setExecuteUnit(unitVO);
					RuleDefinitionVO ruleDefinition = new RuleDefinitionVO();
					ruleDefinition.setRuleId(objs[1].toString());
					if (objs[7] != null) {
						ruleDefinition.setDescDetails(objs[7].toString());
					}
					if (objs[11] != null) {
						ruleDefinition.setRelatedIssueId(objs[11].toString());
					}
					vo.setRuleDefinition(ruleDefinition);
					ExecuteRecordVO executeRecord = new ExecuteRecordVO();
					executeRecord.setRecordId(objs[3].toString());
					vo.setExecuteRecord(executeRecord);
					ruleVOList.add(vo);
				}
			}
		} catch (HibernateException e) {

		}
		return ruleVOList;
	}

	/**
	 * ɾ������ִ�м�¼
	 * 
	 * @author:sunshine
	 * @date:2014-1-7 ����3:31:35
	 * @see com.web.rule.dao.IRuleDao#delExecRecord(java.lang.String)
	 */
	@Override
	public void delExecRecord(String recordID) throws PubBaseException {
		Query qry = getSession().createQuery(
				"delete RuleExecuteResultVO where executeRecord.recordId=?");
		qry.setParameter(0, recordID);
		qry.executeUpdate();
		qry = getSession().createQuery(
				"delete ExecuteRecordVO where recordId=?");
		qry.setParameter(0, recordID);
		qry.executeUpdate();
	}

	/**
	 * ����ִ�н��ID��ѯ��Ϣ
	 * 
	 * @author:sunshine
	 * @date:2014-1-8 ����4:58:41
	 * @see com.web.rule.dao.IRuleDao#queryResultDetailByID(java.lang.String)
	 */
	@Override
	public RuleExecuteResultVO queryResultDetailByID(String resultId)
			throws PubBaseException {
		StringBuffer sql = new StringBuffer();
		sql.append(" from RuleExecuteResultVO where resultId=?");
		List<RuleExecuteResultVO> list = getSession()
				.createQuery(sql.toString()).setParameter(0, resultId).list();
		return list.get(0);
	}

	/**
	 * ��ѯ���в�Ʒ
	 * 
	 * @author:sunshine
	 * @date:2014-1-16 ����3:56:14
	 * @see com.web.rule.dao.IRuleDao#qryAllProd()
	 */
	@Override
	public List<ExecuteUnitVO> qryAllProd() throws PubBaseException {
		List<ExecuteUnitVO> ruleVOList = null;
		try {
			List<Object> objList = getSession()
					.createQuery(
							"select distinct prodCode from ExecuteUnitVO order by prodCode desc")
					.list();
			if (objList != null) {
				ruleVOList = new ArrayList<ExecuteUnitVO>();
				for (Object object : objList) {
					ExecuteUnitVO staticView = new ExecuteUnitVO();
					staticView.setProdCode((String) object);
					ruleVOList.add(staticView);
				}
			}
		} catch (HibernateException e) {

		}
		return ruleVOList;
	}
}
