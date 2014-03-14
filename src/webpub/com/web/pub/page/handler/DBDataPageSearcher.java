package com.web.pub.page.handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;

/**
 * ����Result
 * 
 * 1) ��ͬ��ѯֱ������ԭ��ʵ�� 2) ʵ������Ĭ����3���ӵĴ���� 3) newSearchId��oldSearchId��ȣ���ʾ��ҳ��ѯ
 * 4)newSearchId��oldSearchId����ȣ���ʾ���²�ѯ
 * 
 * @author zhenghb
 * @date 2012-7-3 ����05:09:56
 */
@Scope(value = "singleton")
public class DBDataPageSearcher {
	private static final Log logger = LogFactory
			.getLog(DBDataPageSearcher.class);
	private final static int aliveTime = Integer.valueOf(System.getProperty(
			"web.page.alivetime", "180"));
	private final ConcurrentHashMap<String, Timer> timerMap = new ConcurrentHashMap<String, Timer>();
	private final ConcurrentHashMap<String, IPageAwareResult> resultMap = new ConcurrentHashMap<String, IPageAwareResult>();
	private static DBDataPageSearcher instance = new DBDataPageSearcher();

	private DBDataPageSearcher() {
	}

	public static DBDataPageSearcher getInstance(IDBQueryHandler newHandler) {
		return instance;
	}

	/**
	 * 
	 * 
	 * @param handler
	 * @param para
	 * @return
	 * @throws Exception
	 * @date 2012-8-16 ����10:43:53
	 */
	public synchronized IPageAwareResult getPageAwareResult(
			IDBQueryHandler handler, IDBQuery para) throws Exception {
		// ������ʶ���ж������²�ѯ���Ƿ�ҳ��ѯ
		String oldSearchId = para.getOldSearchId();
		String newSearchId = para.getNewSearchId();
		// ����ڴ���û��ƥ��Ľ�������µĲ�ѯ�������resultMap��
		if (!StringUtils.isEmpty(oldSearchId)
				&& !StringUtils.isEmpty(newSearchId)) {
			// �ж��Ƿ�Ϊ��ҳ��ѯ
			if (newSearchId.equals(oldSearchId)) {
				if (!resultMap.containsKey(newSearchId)) {
					// synchronized (resultMap) {
					if (!resultMap.containsKey(newSearchId)) {
						resultMap.put(newSearchId, new DBDataPageAwareResult(
								para, handler));
					}
					// }
				}
			} else {
				resultMap.remove(oldSearchId);
				timerMap.remove(oldSearchId);
				resultMap.put(newSearchId, new DBDataPageAwareResult(para,
						handler));
			}
		} else if (StringUtils.isEmpty(oldSearchId)) {
			// �״β�ѯoldSearchIdΪ��
			resultMap
					.put(newSearchId, new DBDataPageAwareResult(para, handler));
		} else {
			logger.error("��ҳ��ѯ���봫������id��!");
			throw new RuntimeException("��ҳ��ѯ���봫������id��!");
		}
		// ���Ŷ�ʱ����
		schedule(newSearchId);
		return resultMap.get(newSearchId);
	}

	/**
	 * ���Ŷ�ʱ����,�������
	 * 
	 * @param searchId
	 * @date 2012-8-16 ����10:43:24
	 */
	public void schedule(final String searchId) {
		Timer timer = null;
		// ��ҳ��ѯʱ�����°���timer
		if (timerMap.containsKey(searchId)) {
			synchronized (timerMap) {
				timer = timerMap.get(searchId);
				timer.cancel();
				timer.purge();
				timerMap.remove(searchId);
			}
		}
		timer = new Timer(searchId);
		synchronized (timerMap) {
			timer = new Timer(searchId);
			timerMap.put(searchId, timer);
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// clear the search result.
				resultMap.remove(searchId);
				timerMap.remove(searchId);
			}
		}, aliveTime * 1000);
	}
}