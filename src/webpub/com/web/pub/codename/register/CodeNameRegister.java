package com.web.pub.codename.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.pub.codename.iquery.IQueryCodeName;
import com.web.pub.fw.exception.PubBaseException;
import com.web.rule.codename.query.QueryOrderStatusCodeName;
import com.web.rule.codename.query.QueryProdCodeName;
import com.web.rule.codename.query.QueryRecordCodeName;
import com.web.rule.service.IRuleQryService;

public class CodeNameRegister {
	private static final Log logger = LogFactory.getLog(CodeNameRegister.class);

	private final Map<String, IQueryCodeName> codenameServiceMap = new ConcurrentHashMap<String, IQueryCodeName>();

	public CodeNameRegister(IRuleQryService ruleService) {
		super();
		// ����״̬
		codenameServiceMap.put("CODENAME_RULE_STATUS",
				new QueryOrderStatusCodeName());
		// �������¼
		codenameServiceMap.put("CODENAME_RULE_RECORD", new QueryRecordCodeName(
				ruleService));
		// ��Ʒ
		codenameServiceMap.put("CODENAME_RULE_PROD", new QueryProdCodeName(
				ruleService));
	}

	public IQueryCodeName getQueryService(String key) throws PubBaseException {
		if (codenameServiceMap.containsKey(key)) {
			return codenameServiceMap.get(key);
		} else {
			logger.error("û�в�ѯ��" + key
					+ "��Ӧ�ı������Ʋ�ѯ����,����CodeNameRegister�����Ƿ�ע�ᣡ");
			throw new PubBaseException("û�в�ѯ��" + key
					+ "��Ӧ�ı������Ʋ�ѯ����,����CodeNameRegister�����Ƿ�ע�ᣡ");
		}
	}
}
