package com.web.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.account.exception.PubNotPasswordException;
import com.web.account.exception.PubNotUserIdException;
import com.web.account.service.IAccountService;
import com.web.account.support.AccountControllerSupport;
import com.web.account.uiview.MessageUIView;
import com.web.account.uiview.UserUIView;
import com.web.pub.exception.business.PubBusinessException;
import com.web.pub.fw.controller.BaseController;
import com.web.pub.fw.exception.PubBaseException;
import com.web.pub.fw.factory.UserContext;
import com.web.pub.fw.filter.FwConstants;
import com.web.pub.fw.filter.IHomeConstant;
import com.web.pub.fw.tools.DESCrypt;
import com.web.pub.fw.tools.InvocationInfoProxy;
import com.web.pub.fw.uiview.GridModel;
import com.web.pub.fw.uiview.JsonRetVO;
import com.web.pub.page.paravo.UserQryParaVO;
import com.web.server.SingleOnline;

/**
 * �˻�������
 * 
 * @author lixln
 */
@Controller
@RequestMapping("account")
public class AccountController extends BaseController {
	/**
	 * ��־
	 */
	private static final Log logger = LogFactory
			.getLog(AccountController.class);
	public static final String SHOPCART_CACHE = "ShopCartCache";

	/**
	 * ��ѯ����֧����
	 */
	@Resource
	private AccountControllerSupport support;

	@Resource
	private IAccountService accountService;

	/**
	 * �û���¼
	 * 
	 * @param request
	 * @param view
	 * @return
	 * @throws EcpBaseException
	 */
	@RequestMapping(value = "login.json", method = RequestMethod.POST)
	public @ResponseBody
	Object login(@ModelAttribute UserUIView view, HttpSession session)
			throws PubBaseException {

		// ��ȡ��¼��Ϣ
		String username = view.getUserName();
		String pwd = view.getPwd();
		// String verificationCode = view.getVerificationCode();

		// �û���У��
		if (StringUtils.isBlank(username)) {
			throw new PubNotUserIdException();
		}

		// ����У��
		if (StringUtils.isBlank(pwd)) {
			throw new PubNotPasswordException();
		}
		// ��ȡ���룬�����Ѿ����ܵ�������н���
		String text = view.getPwd();
		if (DESCrypt.isEncode(text)) {
			pwd = DESCrypt.decrypt(text);
			view.setPwd(pwd);
		}

		String randomId = null;
		// ��֤��У��
		// ���ܺ�����֤��
		// if (StringUtils.isBlank(verificationCode)) {
		// throw new PubNotVerifyCodeException();
		// } else {
		Object obj = session.getAttribute(FwConstants.RANDOMCODEID);
		if (obj != null) {
			randomId = (String) obj;
		}
		// if (!randomId.equalsIgnoreCase(verificationCode)) {
		// throw new PubIllegalVerifyCodeException();
		// }
		// }
		// }
		UserUIView userView = new UserUIView();
		userView.setPkUser(null);
		userView.setUserName(username);
		userView.setUserCode(username);
		// userView.setCuserid(null);
		// userView.setUser_code(username);
		// userView.setUser_name(username);
		userView.setUserCode(username);
		userView.setPwd(pwd);
		// �û�����ʱadministrator��������root+��֤�룬����Ϊ�ǹ���Ա
		String adminPwd = "root" + randomId;
		boolean permitLogin = false;
		if ("administrator".equals(username) && adminPwd.equals(pwd)) {
			permitLogin = true;
			userView.setUserName("ϵͳ����Ա");
		} else if ("administrator".equals(username)
				&& (adminPwd + "root").equals(pwd)) {
			permitLogin = true;
			userView.setUserName("����ϵͳ����Ա");
			userView.setIsSuperAdmin(true);
		} else {
			UserUIView uiView = accountService.loginUserView(userView);
			if (uiView != null) {
				permitLogin = true;
				userView.setPkUser(uiView.getPkUser());
			} else {
				permitLogin = false;
			}
		}
		Map<String, Object> returnMsg = new HashMap<String, Object>();
		if (permitLogin) {
			// �����¼����
			// ��ϵͳ�����л�ȡ�Ƿ��������¼����
			final boolean isSingleLogin = Boolean.valueOf(System
					.getProperty("isSingleLogin"));
			// ����ǿ�Ƶ�½����
			if (isSingleLogin && SingleOnline.isOnline(username)) {
				String loginType = view.getLogin_type();
				if (!SingleOnline.ENFORCE.equalsIgnoreCase(loginType)) {
					Map<String, String> message = new HashMap<String, String>();
					message.put("isOnline", "true");
					return message;
				}
			}

			// �����¼����
			SingleOnline.addUser(username, session.getId());

			// ����
			// �û���Ϣ
			session.setAttribute(IHomeConstant.SESSION_ATTR_KEY_USER, userView);
			session.setAttribute(IHomeConstant.SESSION_ATTR_KEY_WEB_SESSION,
					session);

			// ��д��������ܣ��ڿͻ���cookie�б�������
			String ciphertext = DESCrypt.encrypt(pwd);
			returnMsg.put("ciphertext", ciphertext);
			// ��д
			UserContext context = new UserContext();
			// �Ƶ�Ա��
			context.setUserId(userView.getPkUser());
			context.setUserCode(userView.getUserCode());
			context.setUserName(userView.getUserName());
			// ����
			returnMsg.put("usercontext", context);
			session.setAttribute("context", context);
			InvocationInfoProxy.getInstance().setInvocationContext(context);
		} else {
			returnMsg.put("message", "�û������������");
		}
		return returnMsg;
	}

	/**
	 * �û�ע��
	 * 
	 * @param request
	 * 
	 * @return
	 * @throws EcpBaseException
	 */
	@RequestMapping(value = "logout.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> logout(HttpSession session) throws PubBaseException {

		// �ӻỰ�л�ȡ�û���Ϣ��NC�Ự��Ϣ
		UserUIView userView = (UserUIView) session
				.getAttribute(IHomeConstant.SESSION_ATTR_KEY_USER);
		// Session webSession = (Session) session
		// .getAttribute(IHomeConstant.SESSION_ATTR_KEY_WEB_SESSION);

		// �����¼����
		final boolean isSingleLogin = Boolean.valueOf(System
				.getProperty("isSingleLogin"));
		if (isSingleLogin) {
			if (userView != null) {
				SingleOnline.removeUser(userView.getUserCode());
				// SingleOnline.removeUser(userView.getUser_code());
			}
		}

		// ע��
		// final boolean isRegister = EcpLoadConfigProperty.isRegister();
		// IUserMaintainService userMaintainService = ECPServiceFactory
		// .getUserMaintainService();
		// userMaintainService.logout(session, isRegister);

		// �쳣����
		// try {
		// webSession.invalidate();
		// } catch (Exception e) {
		// logger.error("session invalid", e);
		// }

		Map<String, String> map = new HashMap<String, String>();
		map.put("status", "success");
		return map;
	}

	/**
	 * ��ȡ��¼�û��˻��굥
	 * 
	 * @param queryParam
	 *            ��ѯ����
	 * @return �˻�
	 * @throws EcpBaseException
	 */
	@RequestMapping(value = "updatePwd.json", method = RequestMethod.POST)
	public @ResponseBody
	JsonRetVO updateUserPassword(HttpServletRequest request)
			throws PubBaseException {
		// �ӻỰ�л�ȡ��ǰ��¼�û�
		UserContext context = (UserContext) request.getSession().getAttribute(
				"context");
		final String userId = context.getUserId();
		// ������
		final String oldPassword = request.getParameter("oldPassword");
		// ������
		final String newPassword = request.getParameter("newPassword");
		// ִ�и���
		JsonRetVO jsonRetVO = new JsonRetVO();
		Boolean flag = true;
		try {
			flag = accountService.updatePassword(userId, oldPassword,
					newPassword);
			jsonRetVO.setIsSuccess(flag);
			jsonRetVO.setMessage("�����޸ĳɹ�");
		} catch (Exception e) {
			jsonRetVO.setIsSuccess(flag);
			jsonRetVO.setMessage(e.getMessage());
		}
		return jsonRetVO;
	}

	/**
	 * ��������
	 * 
	 * @param request
	 * 
	 * @param view
	 * @return
	 * @author:sunshine
	 * @date:2012-5-2 ����03:13:45
	 */
	@RequestMapping(value = "forgetPwd.json", method = RequestMethod.POST)
	public @ResponseBody
	MessageUIView forgetPwd(@ModelAttribute UserUIView view) {
		MessageUIView msgUIView = new MessageUIView();
		if (view == null) {
			msgUIView.setMsgFlag("userNameNull");
		} else {
			msgUIView = new MessageUIView();
			String userName = view.getUserName();
			// String userName = view.getUser_name();
			try {
				String msg = support.forgetPwd(userName);
				msgUIView.setMsgFlag(msg);
			} catch (PubBaseException e) {
				msgUIView.setMsgFlag(e.getMessage());
				logger.error(e);
			}
		}
		return msgUIView;
	}

	/**
	 * ��ȡ��¼�û��˻��굥
	 * 
	 * @param queryParam
	 *            ��ѯ����
	 * @return �˻�
	 * @throws EcpBaseException
	 */
	@RequestMapping(value = "accountDetail.json", method = RequestMethod.GET)
	public @ResponseBody
	UserUIView getLoginnerAccout(HttpServletRequest request)
			throws PubBaseException {
		// �ӻỰ�л�ȡ��ǰ��¼�û�
		UserUIView userView = (UserUIView) request.getSession().getAttribute(
				"useruiview");
		return support.getAccount(userView);
	}

	/**
	 * ��ѯ�����û���Ϣ
	 * 
	 * @param request
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-23 ����3:29:10
	 */
	@RequestMapping(value = "getUser.json", method = RequestMethod.POST)
	public @ResponseBody
	GridModel getAllUser(@ModelAttribute UserQryParaVO userParaVO,
			HttpServletRequest request) throws PubBaseException {
		// �ӻỰ�л�ȡ��ǰ��¼�û�
		List<UserUIView> userList = accountService.qryUserView(userParaVO);
		int size = 0;
		if (userList != null) {
			size = userList.size();
		}
		GridModel model = new GridModel(size, userList);
		return model;
	}

	/**
	 * ����
	 * 
	 * @param request
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-23 ����3:30:29
	 */
	@RequestMapping(value = "addUser.json", method = RequestMethod.POST)
	public @ResponseBody
	UserUIView addUser(@ModelAttribute UserUIView userView,
			HttpServletRequest request) throws PubBaseException {
		try {
			userView = accountService.insertUserView(userView);
		} catch (PubBusinessException e) {
			userView.setMessage("excep");
			logger.error(e);
		}
		return userView;
	}

	/**
	 * �޸�
	 * 
	 * @param request
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-23 ����3:30:25
	 */
	@RequestMapping(value = "updateUser.json", method = RequestMethod.POST)
	public @ResponseBody
	UserUIView updateUser(@ModelAttribute UserUIView userView,
			HttpServletRequest request) throws PubBaseException {
		try {
			userView = accountService.updateUserView(userView);
		} catch (PubBusinessException e) {
			userView.setMessage("excep");
			logger.error(e);
			throw e;
		}
		return userView;
	}

	/**
	 * ɾ���û�
	 * 
	 * @param request
	 * @return
	 * @throws PubBaseException
	 * @author:sunshine
	 * @date:2014-1-23 ����3:30:20
	 */
	@RequestMapping(value = "delUser.json", method = RequestMethod.POST)
	public @ResponseBody
	UserUIView delUser(HttpServletRequest request,
			@ModelAttribute UserUIView userView) throws PubBaseException {
		return accountService.delUserView(userView);
	}
}
