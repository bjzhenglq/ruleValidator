package com.web.rule.view;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.web.pub.fw.uiview.UIView;
import com.web.rule.persistence.entity.ExecuteRecordVO;
import com.web.rule.persistence.entity.ExecuteUnitVO;
import com.web.rule.persistence.entity.RuleDefinitionVO;
import com.web.rule.persistence.entity.RuleExecuteResultVO;

public class RuleExecuteResultView extends UIView implements Serializable {
	/**
	 * @author:sunshine
	 * @date:2014-1-3 ����4:39:01
	 */
	private static final long serialVersionUID = 6586134593482343418L;
	private String resultId;
	private String executeLevel;
	/**
	 * ִ�е�ԪID
	 */
	@JsonIgnore
	private ExecuteUnitVO executeUnit;

	/**
	 * ����ID
	 */
	@JsonIgnore
	private RuleDefinitionVO ruleDefinition;

	/**
	 * ִ�м�¼ID
	 */
	@JsonIgnore
	private ExecuteRecordVO executeRecord;

	/**
	 * �������
	 */
	private String specParams;
	/**
	 * �Ƿ�ͨ��
	 */
	private String passFlag;

	/**
	 * ���н��
	 */
	private String result;

	/**
	 * ���н����ϸ��Ϣ
	 */
	private String resultDetail;

	
	public String getExecuteLevel() {
		return executeLevel;
	}

	public void setExecuteLevel(String executeLevel) {
		this.executeLevel = executeLevel;
	}

	public String getSpecParams() {
		return specParams;
	}

	public void setSpecParams(String specParams) {
		this.specParams = specParams;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public ExecuteUnitVO getExecuteUnit() {
		return executeUnit;
	}

	public void setExecuteUnit(ExecuteUnitVO executeUnit) {
		this.executeUnit = executeUnit;
	}

	public RuleDefinitionVO getRuleDefinition() {
		return ruleDefinition;
	}

	public void setRuleDefinition(RuleDefinitionVO ruleDefinition) {
		this.ruleDefinition = ruleDefinition;
	}

	public ExecuteRecordVO getExecuteRecord() {
		return executeRecord;
	}

	public void setExecuteRecord(ExecuteRecordVO executeRecord) {
		this.executeRecord = executeRecord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((resultId == null) ? 0 : resultId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleExecuteResultVO other = (RuleExecuteResultVO) obj;
		if (resultId == null) {
			if (other.getResultId() != null)
				return false;
		} else if (!resultId.equals(other.getResultId()))
			return false;
		return true;
	}

}
