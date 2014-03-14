package com.web.pub.console.management;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.web.pub.fw.controller.BaseController;
import com.web.pub.fw.exception.PubBaseException;

/**
 * ��̨����controller
 * 
 * @author:songjl
 * @date:2013-09-10 ����03:17:28
 */
@Controller
@SuppressWarnings("restriction")
@RequestMapping("esfw/management")
public class ManagementController extends BaseController {
	private static final Log logger = LogFactory
			.getLog(ManagementController.class);

	/**
	 * �����ϴ���logoͼƬ
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "logoupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String logoUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.logoUploadCallback('" + savePicture(request)
				+ "')</script>";
	}

	/**
	 * �����ϴ���¼ҳ��logoͼƬ
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "loginlogoupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String loginLogoUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.loginLogoUploadCallback('"
				+ savePicture(request) + "')</script>";
	}

	/**
	 * �����ϴ���¼ҳ�����ͼƬ
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "loginleftupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String loginLeftUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.loginLeftUploadCallback('"
				+ savePicture(request) + "')</script>";
	}

	/**
	 * �����ϴ���¼ҳ�����ͼƬ
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "loginbgupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String loginBGUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.loginLBGUploadCallback('" + savePicture(request)
				+ "')</script>";
	}

	/**
	 * �����ϴ���ͷ��ͼƬ
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "toppicupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String topPicUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.topPicUploadCallback('" + savePicture(request)
				+ "')</script>";
	}

	/**
	 * ���浼��ͼƬ ѡ�к�δѡ������״̬
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "navpicupload.json", method = RequestMethod.POST)
	public @ResponseBody
	String navPicUpload(DefaultMultipartHttpServletRequest request)
			throws IOException {
		return "<script>parent.navPicUploadCallback('" + savePicture(request)
				+ "')</script>";
	}

	/**
	 * ��ȡecpsystem.properties�����ļ�����
	 * 
	 * @return Map<String,String>
	 * @throws EcpBaseException
	 *             ,IOException
	 */
	@RequestMapping(value = "systemPorpertiesList.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> protletConfigList() throws PubBaseException,
			IOException {
		Properties props = new Properties();
		InputStream is = null;
		InputStreamReader isr = null;
		// try {
		// // ��ȡӦ���µ������ļ�
		// String path = ServerRuntime.getECPAppHome()
		// + "/WEB-INF/classes/ecpsystem.properties";
		// is = new BufferedInputStream(new FileInputStream(path));
		// isr = new InputStreamReader(is, "utf-8");
		// props.load(isr);
		// } catch (Exception e) {
		// logger.error("��ȡϵͳ���ô���,��������ȷ��system.config��Ϣ!", e);
		// } finally {
		// IOUtils.closeQuietly(isr);
		// IOUtils.closeQuietly(is);
		// }
		// // ��ȡprops�е�ֵ
		Map<String, String> propsMap = new HashMap<String, String>();
		// Enumeration<Object> en = props.keys();
		// while (en.hasMoreElements()) {
		// String key = en.nextElement().toString();
		// String value = props.getProperty(key);
		// propsMap.put(key, value);
		// }
		return propsMap;
	}

	/**
	 * ����properties����
	 * 
	 * @param queryParam
	 * @return
	 * @throws EcpBaseException
	 */
	@RequestMapping(value = "systemPorpertiesUpdate.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> protletConfigSave(
			@RequestParam(value = "data") String data) throws PubBaseException {
		InputStream is = null;
		InputStreamReader isr = null;
		Boolean result = false;
		Map<String, String> map = new HashMap<String, String>();
		// try {
		// // ��ȡӦ���µ������ļ�
		// String path = ServerRuntime.getECPAppHome()
		// + "/WEB-INF/classes/ecpsystem.properties";
		// JSONObject jsonObj = JSONObject.fromObject(data);
		// @SuppressWarnings("unchecked")
		// Map<String, String> jsonMap = jsonObj;
		// result = PropertyFileTool.setProfileString(path, jsonMap);
		// } catch (IOException e) {
		// logger.error("��ȡϵͳ���������ļ�ecpsystem.properties����!", e);
		// } catch (Exception e) {
		// logger.error("����ϵͳ���������ļ�ecpsystem.properties����!", e);
		// } finally {
		// IOUtils.closeQuietly(isr);
		// IOUtils.closeQuietly(is);
		// }
		// if (result) {
		// map.put("result", "����ɹ�");
		// } else {
		// map.put("result", "��ȡϵͳ���������ļ�ecpsystem.properties����!");
		// }
		return map;
	}

	/**
	 * ����ecpuiproperties����
	 * 
	 * @param queryParam
	 * @return
	 * @throws EcpBaseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "uiPorpertiesUpdate.json", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, String> uiPropConfigSave(
			@RequestParam(value = "data") String data) {
		// IManagementService service = NCLocator.getInstance().lookup(
		// IManagementService.class);
		// return service.uiPropConfigSave(data);
		return null;
	}

	/**
	 * ��ȡecpui.properties�����ļ�����
	 * 
	 * @return Map<String,String>
	 * @throws EcpBaseException
	 *             ,IOException
	 */
	@RequestMapping(value = "uiPorpertiesQuery.json", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, String> uiConfigList() throws PubBaseException, IOException {
		Properties props = new Properties();
		InputStream is = null;
		InputStreamReader isr = null;
		// try {
		// // ��ȡӦ���µ������ļ�
		// String path = ServerRuntime.getECPAppHome()
		// + "/WEB-INF/classes/ecpui.properties";
		// is = new BufferedInputStream(new FileInputStream(path));
		// isr = new InputStreamReader(is, "utf-8");
		// props.load(isr);
		// } catch (Exception e) {
		// logger.error("��ȡϵͳ���ô���,��������ȷ��system.config��Ϣ!", e);
		// } finally {
		// IOUtils.closeQuietly(isr);
		// IOUtils.closeQuietly(is);
		// }
		// ��ȡprops�е�ֵ
		Map<String, String> propsMap = new HashMap<String, String>();
		Enumeration<Object> en = props.keys();
		while (en.hasMoreElements()) {
			String key = en.nextElement().toString();
			String value = props.getProperty(key);
			propsMap.put(key, value);
		}
		return propsMap;
	}

	/**
	 * ���ϴ���ͼƬ���浽����·��
	 * 
	 * @param obj
	 * @return
	 * @author:songjl
	 * @throws IOException
	 * @date:2013-09-16 ����08:40:05
	 */
	private String savePicture(DefaultMultipartHttpServletRequest req)
			throws IOException {
		// ��ȡ�ϴ�ͼƬ����
		Map<String, MultipartFile> hashMap = req.getFileMap();
		// ����ϴ���ͼƬ
		// if (hashMap.entrySet().size() > 0) {
		// IManagementService service = NCLocator.getInstance().lookup(
		// IManagementService.class);
		// for (Map.Entry<String, MultipartFile> entry : hashMap.entrySet()) {
		// CommonsMultipartFile partFile = (CommonsMultipartFile) entry
		// .getValue();
		// return service.uiManagementPictureSave(partFile);
		// }
		// }
		// ���ؿձ�ʾû���ϴ�ͼƬ
		return "";
	}

	/**
	 * ��ת����̨����ҳ��
	 * 
	 * @param obj
	 * @return
	 * @author:songjl
	 * @throws IOException
	 * @date:2013-09-16 ����08:40:05
	 */
	@RequestMapping(value = "manage.htm", method = RequestMethod.GET)
	public @ResponseBody
	String managehtmredirect(HttpServletResponse response)
			throws PubBaseException, IOException {
		// byte[] token = NetStreamContext.getToken();
		// UserContext context =
		// UserWhiteList.getInstance().getUserContext(token);
		// if (!(context.getUserType().equals(
		// IUserMaintainService.USER_TYPE_PERSON) && context
		// .getBaseDocType().equals(
		// IUserMaintainService.BASE_DOC_TYPE_G_ADMIN))) {
		// // û��Ȩ��
		// return "false";
		// }
		return "true";
	}

}
