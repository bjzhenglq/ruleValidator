package com.web.rule.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rv_rules")
public class RuleDefinitionVO implements Serializable, IEntityIdentifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5376758388369420447L;

	@Id
	@Column(name = "ruleid")
	private String ruleId;
	/**
	 * ��ʶ��- һ������ı�ʶ��ȫ·�����ƣ���֤ȫ��Ψһ
	 */
	@Column(name = "identify", length = 100)
	private String identify;

	/**
	 * ����Ӧ�÷�Χ
	 */
	@Column(name = "use_scope", length = 40)
	private String ruleScope;

	/**
	 * ��������
	 */
	@Column(name = "rule_type", length = 80)
	private String ruleType;

	/**
	 * ����������
	 */
	@Column(name = "ruleCharacter", length = 80)
	private String ruleSubType;

	/**
	 * ��ϸ����
	 */
	@Column(name = "descDetails", length = 1024)
	private String descDetails;

	/**
	 * �����Եȼ�
	 */
	@Column(name = "rule_level", length = 40)
	private String level;
	/**
	 * ������
	 */
	@Column(name = "developer", length = 40)
	private String developer;

	/**
	 * ����Ҫ���������
	 */
	@Column(name = "special_params", length = 200)
	private String specialParams;
	/**
	 * ��ע
	 */
	@Column(name = "memo", length = 1024)
	private String memo;
	/**
	 * �������취
	 */
	@Column(name = "solution", length = 1024)
	private String solution;

	/**
	 * ���������ռ�ϵͳ������ID
	 */
	@Column(name = "relatedIssueId", length = 10)
	private String relatedIssueId;

	/**
	 * �˹������е���ͽ׶�
	 */
	@Column(name = "executePeriod", length = 20)
	private String executePeriod;

	/**
	 * �˹���ִ�еĲ㼶
	 */
	@Column(name = "executeLayer", length = 30)
	private String executeLayer;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getRuleScope() {
		return ruleScope;
	}

	public void setRuleScope(String ruleScope) {
		this.ruleScope = ruleScope;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getRuleSubType() {
		return ruleSubType;
	}

	public void setRuleSubType(String ruleSubType) {
		this.ruleSubType = ruleSubType;
	}

	public String getDescDetails() {
		return descDetails;
	}

	public void setDescDetails(String descDetails) {
		this.descDetails = descDetails;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getSpecialParams() {
		return specialParams;
	}

	public void setSpecialParams(String specialParams) {
		this.specialParams = specialParams;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getRelatedIssueId() {
		return relatedIssueId;
	}

	public void setRelatedIssueId(String relatedIssueId) {
		this.relatedIssueId = relatedIssueId;
	}

	public String getExecutePeriod() {
		return executePeriod;
	}

	public void setExecutePeriod(String executePeriod) {
		this.executePeriod = executePeriod;
	}

	public String getExecuteLayer() {
		return executeLayer;
	}

	public void setExecuteLayer(String executeLayer) {
		this.executeLayer = executeLayer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
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
		RuleDefinitionVO other = (RuleDefinitionVO) obj;
		if (ruleId == null) {
			if (other.ruleId != null)
				return false;
		} else if (!ruleId.equals(other.ruleId))
			return false;
		return true;
	}

	@Override
	public String getId() {
		return ruleId;
	}

}
