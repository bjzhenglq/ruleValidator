package com.web.server;

/**
 * �����������
 * 
 * @since 6.3
 * @version 2013-12-23 ����01:49:28
 * @author sunshine
 */
public class ScheduleServiceJob {
    /**
     * �������session
     */
    public void removeAllSession() {
        SingleOnline.purge();
    }
}
