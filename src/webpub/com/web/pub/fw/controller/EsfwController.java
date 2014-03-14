package com.web.pub.fw.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.pub.fw.exception.PubBaseException;
import com.web.pub.fw.tools.EsFwConstants;
import com.web.pub.fw.tools.EsFwUtil;

/**
 * ���ݻ�����������
 * 
 * @author:sunshine
 * @date:2014-1-7 ����4:50:02
 */
@Controller
@RequestMapping("esfw/pub")
public class EsfwController extends BaseController {
	private static final Log logger = LogFactory.getLog(EsfwController.class);

	/**
	 * 
	 * @param pk
	 * @param nodecode
	 * @param popConditionView
	 * @param bqparameters
	 * @return
	 * @throws PubBaseException
	 * @throws IOException
	 */
	@RequestMapping(value = "bapforward.htm", method = RequestMethod.GET)
	public String uploadFile(@RequestParam String pk,
			@RequestParam String nodecode,
			@RequestParam String popConditionView,
			@RequestParam String bqparameters) throws PubBaseException,
			IOException {
		// String portal = EcpLoadConfigProperty.getPortal();
		String portal = "";
		String registerAddress = portal
				+ "/registerServlet?type=2&dsname=design&userid=woojun&password=aa1111";
		InputStream is = null;
		BufferedReader br = null;
		try {
			URL ssoUrl = new URL(registerAddress);
			String ssoKey = "";
			URLConnection connection = ssoUrl.openConnection();
			connection.setDoOutput(true);
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String tempLine = br.readLine();
			StringBuffer temp = new StringBuffer();
			while (tempLine != null) {
				temp.append(tempLine);
				tempLine = br.readLine();
			}
			String[] keys = temp.toString().split(":");

			if (keys.length > 1) {
				ssoKey = keys[1];
			}
			StringBuffer sb = new StringBuffer();
			bqparameters = buildParameter(bqparameters);
			sb.append("redirect:").append(portal).append("/app/bap.app?")
					.append("pk=").append(pk).append("&nodecode=")
					.append(nodecode).append("&popConditionView=")
					.append(popConditionView).append("&bqparameters=")
					.append(bqparameters).append("&ssoKey=").append(ssoKey);
			logger.debug("bap address:" + sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			throw new PubBaseException(e);
		} catch (IOException e) {
			throw new PubBaseException(e);
		} finally {
			is.close();
			br.close();
		}
	}

	private String buildParameter(String bqparameters)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = objectMapper.readValue(bqparameters,
				Map.class);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String value = entry.getValue().toString();
			if (value.startsWith("@")) {
				value = value.substring(1, value.length());
				value = convert(value);
			}
			;
			entry.setValue(value);
		}
		String result = objectMapper.writeValueAsString(map);
		return result;
	}

	/**
	 * �ѱ����滻Ϊ�����ֵ
	 * 
	 * @param value
	 * @return
	 */
	private String convert(String value) {
		if (value.equals("pkcustomer")) {
			// value = EcpInvocationInfoProxy.getInstance().getPkCustomer();
		}
		return value;
	}

	/**
	 * ������������ַ���
	 */
	public static final String REQUEST_PARAMETER_ENCODE_CHARSET = "ISO8859-1";

	/**
	 * ����˱����ַ���
	 */
	public static final String SERVER_ENCODE_CHARSET = "UTF-8";

	/**
	 * �����ļ�
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws PubBaseException
	 */
	@RequestMapping(value = "downloadByRealPathAndFileName.json", method = RequestMethod.GET)
	public @ResponseBody
	void downloadFile(@RequestParam String realPath,
			@RequestParam String fileName) throws PubBaseException {
		try {
			realPath = URLDecoder.decode(realPath, SERVER_ENCODE_CHARSET);
			// ��ȡ�ļ�����
			String strFileName = URLDecoder.decode(fileName,
					SERVER_ENCODE_CHARSET);
			// ��ȡ����Դ����
			// String strDsName = EcpLoadConfigProperty.getDataSource();
			String strDsName = "design";
			download(getRequest(), getResponse(), strDsName, strFileName,
					realPath);
		} catch (UnsupportedEncodingException e1) {
			if (logger.isDebugEnabled()) {
				logger.debug("�������뱨��");
				throw new PubBaseException("�������뱨��");
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("���ر���");
				throw new PubBaseException("���ر���");
			}
		}

	}

	/**
	 * �����ļ�
	 * 
	 * @param request
	 *            ����
	 * @param response
	 *            ��Ӧ
	 * @param strDsName
	 *            ����Դ
	 * @param strFileName
	 *            �ļ���
	 * @param strFilePath
	 *            �ļ�·��(�ļ�·��Ϊҵ������/�ļ���)
	 * @throws Exception
	 */
	private void download(HttpServletRequest request,
			HttpServletResponse response, String strDsName, String strFileName,
			String strFilePath) throws Exception {
		ObjectOutputStream oos = null;
		InputStream in = null;
		// String ncHomeHostAndPort = EcpLoadConfigProperty.getEjbAddress();
		String ncHomeHostAndPort = "127.0.0.1:80";
		if ("127.0.0.1:80".equals(ncHomeHostAndPort)) {
			ncHomeHostAndPort = request.getServerName() + ":"
					+ request.getServerPort();
		}
		StringBuilder sbUrl = new StringBuilder(request.getScheme())
				.append("://").append(ncHomeHostAndPort)
				.append("/service/FileManageServlet");
		try {
			response.setContentType("application/x-download");
			String fileNameEncoded = encodeFileName(request, strFileName);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileNameEncoded);
			URL url = new URL(sbUrl.toString());
			URLConnection conn = url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("content-type",
					"application/x-java-serialized-object,charset=ISO8895-1");

			// ����token
			// byte[] token = NetStreamContext.getToken();
			// String tokenStr = KeyUtil.encodeToken(token);
			// String cookieStr = "security_token=" + tokenStr;
			String cookieStr = "security_token=";
			conn.setRequestProperty("Cookie", cookieStr);

			oos = new ObjectOutputStream(conn.getOutputStream());
			HashMap<String, String> headInfo = new HashMap<String, String>();
			headInfo.put("dsName", strDsName);
			headInfo.put("operType", "download");
			headInfo.put("path", strFilePath);
			// headInfo.put("user_code", EcpInvocationInfoProxy.getInstance()
			// .getUserCode());
			oos.writeObject(headInfo);
			oos.flush();
			in = conn.getInputStream();
			byte[] buf = new byte[2048];
			int len = -1;
			// logger.info("�ļ�����token=" + tokenStr);
			while ((len = in.read(buf)) != -1) {
				response.getOutputStream().write(buf, 0, len);
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * �������ļ�������
	 * <p>
	 * ����������룺
	 * </p>
	 * <ul>
	 * <li>FireFox�������ʹ��iso8859-1����</li>
	 * <li>Chrome�������ʹ��iso8859-1����utf-8����</li>
	 * <li>IE�������ʹ��utf-8����</li>
	 * </ul>
	 * <p>
	 * ����ո�
	 * </p>
	 * <ul>
	 * <li>FireFox�������ʹ��java.lang.String���봦��֮�󣬰����ո����������ӵ�һ���ո�ضϣ�������ʾ������ļ�����
	 * ���Բ����»����滻���еĿո�</li>
	 * <li>Chrome�������IE�����ʹ��java.net.URLEncoder���봦��֮�����������ʶ��ո񣬲���ض�</li>
	 * </ul>
	 * 
	 * @param request
	 *            ���󣨻�ȡ�������Ϣ��
	 * @param fileName
	 *            Ҫ������ļ���
	 * @return ����֮����ļ���
	 * @author:lixl
	 * @throws IOException
	 * @date:2012-8-21 ����01:51:14
	 */
	private static String encodeFileName(HttpServletRequest request,
			final String fileName) throws IOException {
		String browserType = request.getHeader("User-Agent");
		// FireFox��Chrome�ȶ��������ʹ��iso8859-1����
		String fileNameEncoded = new String(fileName.getBytes("UTF-8"),
				"ISO8859-1");
		// �滻�ո�Ϊ�»���
		fileNameEncoded = fileNameEncoded.replaceAll(" ", "_");
		// fileNameEncoded = MimeUtility.encodeText(fileName, "UTF-8", "B");
		if (browserType != null
				&& !browserType.trim().equals("")
				&& (browserType.toUpperCase().indexOf("MSIE") > -1
						|| browserType.toUpperCase().indexOf("CHROME") > -1 || browserType
						.toUpperCase().indexOf("OPERA") > -1)) {
			// IE��Chrome
			// fileNameEncoded = processFileName(fileName, "UTF-8");
			fileNameEncoded = URLEncoder.encode(fileName, "UTF-8");
			fileNameEncoded = fileNameEncoded.replaceAll("[+]", "%20");
		}
		return fileNameEncoded;
	}

	/**
	 * ��ת����ҳ��
	 * 
	 * @return
	 */
	@RequestMapping(value = "error.htm", method = RequestMethod.GET)
	public String error() {
		return "/error/error";
	}

	/**
	 * ���������֤��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author:sunshine
	 * @throws IOException
	 * @date:2012-4-12 ����09:37:42
	 */
	@RequestMapping(value = "randomid.json", method = RequestMethod.GET)
	public @ResponseBody
	String randomid(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// ��֤��ͼƬ�Ŀ�ȡ�
		int width = 80;
		// ��֤��ͼƬ�ĸ߶ȡ�
		int height = 20;
		// ��֤���ַ�����
		int codeCount = 4;
		// ����߶�
		int fontHeight = height - 2;
		// X����
		int xx = width / (codeCount + 1);
		// Y����
		int codeY = height - 4;

		String randomCode = EsFwUtil.genNumPassword(codeCount);

		// ����������ɵ���֤������ͼƬ��������
		// ����ͼ��buffer
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gra = image.createGraphics();

		// ��ͼ�����Ϊ��ɫ
		gra.setColor(Color.WHITE);
		gra.fillRect(0, 0, width, height);

		// �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�������
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// �������塣
		gra.setFont(font);

		// ���߿�
		gra.setColor(Color.BLACK);
		// gra.drawRect(0, 0, width - 1, height - 1);
		Random random = new Random();
		// �������160�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽��
		gra.setColor(Color.BLACK);
		int red = 0, green = 0, blue = 0;
		char c;
		// ��ɫ���ϵ������ֵԽ��RGB��ɫֵԽС����ɫԽ��
		float colorDeep = 2;
		int rgbColorMax = Float.valueOf(255 / colorDeep).intValue();
		for (int i = 0; i < 4; i++) {
			c = randomCode.charAt(i);
			red = random.nextInt(rgbColorMax);
			green = random.nextInt(rgbColorMax);
			blue = random.nextInt(rgbColorMax);
			// �������������ɫ����֤����Ƶ�ͼ���С�
			gra.setColor(new Color(red, green, blue));
			gra.drawString("" + c, (i + 1) * xx, codeY);
		}
		// �ѵ�ǰ����֤�����session�У��Թ���¼ʱ��֤
		request.getSession().setAttribute(EsFwConstants.RANDOMCODEID,
				randomCode);
		// ��ֹͼ�񻺴档
		// HttpServletResponse response = getResponse();
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "jpeg", sos);
		sos.close();

		return null;
	}
}
