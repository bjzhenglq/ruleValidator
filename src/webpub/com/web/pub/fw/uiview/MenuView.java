package com.web.pub.fw.uiview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends UIView implements Serializable {

	/**
	 * @author:sunshine
	 * @date:2013-12-26 ����4:20:13
	 */
	private static final long serialVersionUID = -6493631311462034564L;

	/**
	 * �˵�id
	 */
	private String menuId;

	/**
	 * �˵�����
	 */
	private String menuTitle;

	/**
	 * ��Դid
	 */
	private String resourceId;

	/**
	 * ���ڵ�id
	 */
	private String parentId;

	private MenuView parentView;

	/**
	 * ����
	 */
	private Integer seq;

	/**
	 * �Ƿ�����
	 */
	private Boolean hide;

	/**
	 * ��Դ
	 */
	private ResourceView resourceView;

	/**
	 * ���ӽڵ�
	 */
	private List<MenuView> childrenView;

	/**
	 * �Ƿ�ΪҶ�ӽڵ�
	 */
	private Boolean leaf;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MenuView() {
		super();
		childrenView = new ArrayList<MenuView>();
	}

	public List<MenuView> getChildrenView() {
		return childrenView;
	}

	public void setChildrenView(List<MenuView> childrenView) {
		this.childrenView = childrenView;
	}

	public ResourceView getResourceView() {
		return resourceView;
	}

	public void setResourceView(ResourceView resourceView) {
		this.resourceView = resourceView;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
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

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public MenuView getParentView() {
		return parentView;
	}

	public void setParentView(MenuView parentView) {
		this.parentView = parentView;
	}

}
