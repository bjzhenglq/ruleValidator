package com.web.pub.comp.combobox;

/**
 * ������ѡ��
 * 
 * @author:��־��
 * @date:2013-1-18 ����02:25:43
 */
public class ComboBoxItem implements IComboBoxItem {
	private String name;
	private String code;

	/**
	 * @author:��־��
	 * @date:2013-1-21 ����10:54:12
	 * @see com.web.pub.comp.combobox.IComboBoxItem#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @author:��־��
	 * @date:2013-1-21 ����10:54:12
	 * @see com.web.pub.comp.combobox.IComboBoxItem#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @author:��־��
	 * @date:2013-1-21 ����10:54:12
	 * @see com.web.pub.comp.combobox.IComboBoxItem#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * @author:��־��
	 * @date:2013-1-21 ����10:54:12
	 * @see com.web.pub.comp.combobox.IComboBoxItem#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	public ComboBoxItem(String code, String name) {
		super();
		this.name = name;
		this.code = code;
	}
}
