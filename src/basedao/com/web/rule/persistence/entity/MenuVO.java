package com.web.rule.persistence.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rv_menus")
public class MenuVO implements Serializable {

	/**
	 * @author:sunshine
	 * @date:2014-1-13 ����1:21:40
	 */
	private static final long serialVersionUID = -688700665364417438L;

	/**
	 * �˵�id
	 */
	@Id
	@Column(name = "menuid")
	private String menuId;

	/**
	 * �˵�����
	 */
	@Column(name = "menutitle")
	private String menuTitle;

	/**
	 * ��Դid
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	@JoinColumn(name = "resourceid")
	private ResourceVO resourceVO;

	/**
	 * ���ڵ�id
	 */
	@Column(name = "parentid")
	private String parentId;

	// private MenuVO parentMenu;

	/**
	 * ����
	 */
	@Column(name = "seq")
	private Integer seq;

	/**
	 * �Ƿ�����
	 */
	@Column(name = "hide")
	private String hide;

	// /**
	// * ���ӽڵ�
	// */
	// private List<MenuVO> childrenVO;

	/**
	 * �Ƿ�ΪҶ�ӽڵ�
	 */
	@Column(name = "leaf")
	private String leaf;

	// public List<MenuVO> getChildrenVO() {
	// return childrenVO;
	// }
	//
	// public void setChildrenVO(List<MenuVO> childrenVO) {
	// this.childrenVO = childrenVO;
	// }
	@Column(name = "manager")
	private String manager;

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	// public MenuVO getParentMenu() {
	// if (!StringUtils.isEmpty(this.parentId)) {
	//
	// }
	// return parentMenu;
	// }
	//
	// public void setParentMenu(MenuVO parentMenu) {
	// this.parentMenu = parentMenu;
	// }

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public ResourceVO getResourceVO() {
		return resourceVO;
	}

	public void setResourceVO(ResourceVO resourceVO) {
		this.resourceVO = resourceVO;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
