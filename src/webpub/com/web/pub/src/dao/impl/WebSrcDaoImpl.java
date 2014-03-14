package com.web.pub.src.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.fw.tools.EsFwUtil;
import com.web.pub.page.paravo.MenusQryParaVO;
import com.web.pub.page.paravo.ResourQryParaVO;
import com.web.pub.src.dao.IWebSrcDao;
import com.web.rule.persistence.entity.MenuVO;
import com.web.rule.persistence.entity.ResourceVO;

/**
 * 
 * 
 * @author:sunshine
 * @date:2014-1-13 ����2:45:15
 */
@Repository
@SuppressWarnings("unchecked")
public class WebSrcDaoImpl extends HibernateDaoSupport implements IWebSrcDao {

	private static final Log logger = LogFactory.getLog(WebSrcDaoImpl.class);

	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * ��ѯ���и��ڵ�
	 * 
	 * @author:sunshine
	 * @date:2014-1-13 ����2:50:28
	 * @see com.web.pub.src.dao.IWebSrcDao#qryRootMenuVO()
	 */

	@Override
	public List<MenuVO> qryRootMenuVO(MenusQryParaVO qryParaVO)
			throws PubBusinessException {
		List<MenuVO> ruleVOList = null;
		try {
			StringBuffer sb = new StringBuffer(
					"from MenuVO where parentId='root'");
			if (null == qryParaVO || !qryParaVO.getIsManger()) {
				sb.append(" and manager=0 ");
			}
			sb.append("order by menuId asc");
			ruleVOList = getSession().createQuery(sb.toString()).list();
		} catch (HibernateException e) {
			throw new PubBusinessException(e.getMessage());
		}
		return ruleVOList;
	}

	/**
	 * ��ѯ���ж����˵����˵�ֻ֧��������
	 * 
	 * @author:sunshine
	 * @date:2014-1-13 ����2:50:31
	 * @see com.web.pub.src.dao.IWebSrcDao#qryLeafMenuVO()
	 */
	@Override
	public List<MenuVO> qryLeafMenuVO() throws PubBusinessException {
		List<MenuVO> ruleVOList = null;
		try {
			ruleVOList = getSession().createQuery(
					" from MenuVO where parentId!='root' order by menuId asc")
					.list();
		} catch (HibernateException e) {
			throw new PubBusinessException(e.getMessage());
		}
		return ruleVOList;
	}

	@Override
	public void initMenuData() throws PubBusinessException {
		// Transaction tx = null;
		// �����Ż���Դ
		try {
			getSession().createQuery("delete MenuVO ").executeUpdate();
			getSession().createQuery("delete ResourceVO ").executeUpdate();
			String str = null;
			File file = new File(
					EsFwUtil.getInitSqlFilePath("init_resouce.sql"));
			logger.error("Ԥ�ýű��ļ�·��Ϊ��" + file.getAbsolutePath());
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				// InputStreamReader isr = new InputStreamReader(in, "UTF-8");
				// size Ϊ�ִ��ĳ��� ������һ���Զ���
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
				in.close();
				str = new String(buffer, "UTF-8");
				excuteSQLFile(str);
				// getSession().createSQLQuery(str).executeUpdate();
			} else {
				logger.error(file.getAbsolutePath()
						+ "·���²������ļ�init_resouce.sql");
				throw new PubBusinessException("init_resouce.sql�ļ�������·����"
						+ EsFwUtil.getInitSqlFilePath(""));
			}
		} catch (Exception e) {
			logger.error(e);
		}

		// �����Ż��˵�
		try {

			String str = null;
			File file = new File(EsFwUtil.getInitSqlFilePath("init_menus.sql"));
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				// size Ϊ�ִ��ĳ��� ������һ���Զ���
				int size = in.available();
				byte[] buffer = new byte[size];
				in.read(buffer);
				in.close();
				str = new String(buffer, "UTF-8");
				excuteSQLFile(str);
				// getSession().createSQLQuery(str).executeUpdate();
			} else {
				logger.error(file.getAbsolutePath() + "·���²������ļ�init_menus.sql");
				throw new PubBusinessException("init_menus.sql�ļ�������·����"
						+ EsFwUtil.getInitSqlFilePath(""));
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	private void excuteSQLFile(String str) {
		if (!StringUtils.isEmpty(str)) {
			String[] sqls = str.split(";");
			Session session = getSession();
			for (String sql : sqls) {
				session.createSQLQuery(sql).executeUpdate();
			}
		}
	}

	/**
	 * ����������ѯ�˵�VO
	 * 
	 * @param menuID
	 * @return
	 * @throws PubBusinessException
	 * @author:sunshine
	 * @date:2014-2-12 ����9:37:46
	 */
	@Override
	public MenuVO qryMenuVOByID(String menuID) throws PubBusinessException {
		return (MenuVO) getSession().get(MenuVO.class, menuID);
	}

	/**
	 * ������Դ
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:30
	 * @see com.web.pub.src.dao.IWebSrcDao#addResource(com.web.rule.persistence.entity.ResourceVO)
	 */
	@Override
	public ResourceVO addResource(ResourceVO recourceVO)
			throws PubBusinessException {
		recourceVO.setResourceId(UUID.randomUUID().toString());
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession().save(recourceVO);
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			logger.equals(e);
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}
		return recourceVO;
	}

	/**
	 * ɾ����Դ
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:34
	 * @see com.web.pub.src.dao.IWebSrcDao#delResource(com.web.rule.persistence.entity.ResourceVO)
	 */
	@Override
	public ResourceVO delResource(ResourceVO recourceVO)
			throws PubBusinessException {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession().delete(recourceVO);
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}
		return recourceVO;
	}

	/**
	 * �޸���Դ
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:38
	 * @see com.web.pub.src.dao.IWebSrcDao#updateResource(com.web.rule.persistence.entity.ResourceVO)
	 */
	@Override
	public ResourceVO updateResource(ResourceVO recourceVO)
			throws PubBusinessException {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession()
					.createQuery(
							" update ResourceVO set resourceCode=?, resourceName=?,securityLevel=?, moduleId=?, url=?, seq=? where resourceId=?")
					.setParameter(0, recourceVO.getResourceCode())
					.setParameter(1, recourceVO.getResourceName())
					.setParameter(2, recourceVO.getSecurityLevel())
					.setParameter(3, recourceVO.getModuleId())
					.setParameter(4, recourceVO.getUrl())
					.setParameter(5, recourceVO.getSeq())
					.setParameter(6, recourceVO.getResourceId())
					.executeUpdate();
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}
		return recourceVO;
	}

	/**
	 * ����������ѯ��Դ
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:41
	 * @see com.web.pub.src.dao.IWebSrcDao#getResource(com.web.pub.page.paravo.ResourQryParaVO)
	 */
	@Override
	public List<ResourceVO> getResource(ResourQryParaVO resQryParaVO)
			throws PubBusinessException {
		List<ResourceVO> resVOList = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" from ResourceVO where 1=1 ");
		if (!StringUtils.isEmpty(resQryParaVO.getPkRes())) {
			sb.append(" and resourceId=?");
		}
		if (!StringUtils.isEmpty(resQryParaVO.getResCode())) {
			sb.append(" and resourceCode like ?");
		}
		if (!StringUtils.isEmpty(resQryParaVO.getResName())) {
			sb.append(" and resourceName like ?");
		}
		sb.append(" order by resourceId asc ");
		Query qry = getSession().createQuery(sb.toString());
		int i = 0;
		if (!StringUtils.isEmpty(resQryParaVO.getPkRes())) {
			qry.setParameter(i++, resQryParaVO.getPkRes());
		}
		if (!StringUtils.isEmpty(resQryParaVO.getResCode())) {
			qry.setParameter(i++, "%" + resQryParaVO.getResCode() + "%");
		}
		if (!StringUtils.isEmpty(resQryParaVO.getResName())) {
			qry.setParameter(i++, "%" + resQryParaVO.getResName() + "%");
		}

		try {
			resVOList = qry.list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new PubBusinessException(e.getMessage());
		}
		return resVOList;
	}

	/**
	 * �����˵�
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:44
	 * @see com.web.pub.src.dao.IWebSrcDao#addMenus(com.web.rule.persistence.entity.MenuVO)
	 */
	@Override
	public MenuVO addMenus(MenuVO meunVO) throws PubBusinessException {
		meunVO.setMenuId(UUID.randomUUID().toString());
		Transaction tx = null;
		// ���Ϊ�գ�Ĭ���ǹ��ܽڵ㣬�ǹ���ڵ�
		if (StringUtils.isEmpty(meunVO.getManager())) {
			meunVO.setManager("0");
		}
		try {
			tx = getSession().beginTransaction();
			getSession().save(meunVO);
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			logger.equals(e);
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}
		return meunVO;
	}

	/**
	 * ɾ���˵�
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:48
	 * @see com.web.pub.src.dao.IWebSrcDao#delMenus(com.web.rule.persistence.entity.MenuVO)
	 */
	@Override
	public MenuVO delMenus(MenuVO menuVO) throws PubBusinessException {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession().delete(menuVO);
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}
		return menuVO;
	}

	/**
	 * �޸Ĳ˵�
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:51
	 * @see com.web.pub.src.dao.IWebSrcDao#updateMenus(com.web.rule.persistence.entity.MenuVO)
	 */
	@Override
	public MenuVO updateMenus(MenuVO menuVO) throws PubBusinessException {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession()
					.createQuery(
							" update MenuVO set menuTitle=?, parentId=?,leaf=?, hide=?,seq=? where menuId=?")
					.setParameter(0, menuVO.getMenuTitle())
					.setParameter(1, menuVO.getParentId())
					// .setParameter(1, menuVO.getParentMenu().getMenuId())
					.setParameter(2, menuVO.getLeaf())
					.setParameter(3, menuVO.getHide())
					.setParameter(4, menuVO.getSeq())
					.setParameter(5, menuVO.getMenuId()).executeUpdate();
			tx.commit();
			getSession().flush();
		} catch (Exception e) {
			throw new PubBusinessException(e.getMessage());
		} finally {
			getSession().close();
		}

		return null;
	}

	/**
	 * ����������ѯ�˵�
	 * 
	 * @author:sunshine
	 * @date:2014-2-11 ����3:40:55
	 * @see com.web.pub.src.dao.IWebSrcDao#getMenus(com.web.pub.page.paravo.MenusQryParaVO)
	 */
	@Override
	public List<MenuVO> getMenus(MenusQryParaVO menuQryParaVO)
			throws PubBusinessException {
		List<MenuVO> menuVOList = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" from MenuVO where 1=1");
		if (!StringUtils.isEmpty(menuQryParaVO.getPkMenu())) {
			sb.append(" and menuId=?");
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getMenuName())) {
			sb.append(" and menuTitle like ?");
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getResCode())) {
			sb.append(" and resourceVO.resourceCode like ?");
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getResName())) {
			sb.append(" and resourceVO.resourceName like ?");
		}
		if (menuQryParaVO.getIsRoot() != null && menuQryParaVO.getIsRoot()) {
			sb.append(" and parentId='root' ");
		}
		sb.append(" order by menuId asc ");
		Query qry = getSession().createQuery(sb.toString());
		int i = 0;
		if (!StringUtils.isEmpty(menuQryParaVO.getPkMenu())) {
			qry.setParameter(i++, menuQryParaVO.getPkMenu());
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getMenuName())) {
			qry.setParameter(i++, "%" + menuQryParaVO.getMenuName() + "%");
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getResCode())) {
			qry.setParameter(i++, "%" + menuQryParaVO.getResCode() + "%");
		}
		if (!StringUtils.isEmpty(menuQryParaVO.getResName())) {
			qry.setParameter(i++, "%" + menuQryParaVO.getResName() + "%");
		}

		try {
			menuVOList = qry.list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new PubBusinessException(e.getMessage());
		}
		return menuVOList;
	}

	/**
	 * ����������ѯ�˵�
	 * 
	 * @author:sunshine
	 * @date:2014-2-12 ����2:50:52
	 * @see com.web.pub.src.dao.IWebSrcDao#qrySourceVOByID(java.lang.String)
	 */
	@Override
	public ResourceVO qrySourceVOByID(String sourceID)
			throws PubBusinessException {
		return (ResourceVO) getSession().get(ResourceVO.class, sourceID);
	}
}
