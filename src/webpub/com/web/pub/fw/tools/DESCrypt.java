package com.web.pub.fw.tools;

import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES�ԳƼ���
 * <ul>
 * <li>������ʹ��DES/ECB/PKCS5Padding�㷨</li>
 * <li>������Կʹ����DES�㷨</li>
 * <li>ʹ����ͳһ��Կ�������漴��Կ</li>
 * <li>��Կʹ��base64����洢</li>
 * <li>��Կʹ��main������ȡ</li>
 * <li>
 * Ϊ�˽�����ĸ�ʽbyte[]��Ŀ���ʽjava.lang.String�������ݲ����໥ת�������⣬�м������base64���룬�ǵ�byte[]��java
 * .lang. String���������໥ת��</li>
 * 
 * @author:sunshine
 * @date:2013-12-25 ����10:43:15
 */
@SuppressWarnings("restriction")
public class DESCrypt {

	/**
	 * ��־���
	 */
	protected static Log log = LogFactory.getLog(DESCrypt.class);

	/**
	 * ��Կ�㷨
	 */
	private final static String KEY_ALGORITHM = "DES";

	/**
	 * �㷨����/����ģʽ/��䷽ʽ
	 */
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	/**
	 * �ַ���
	 */
	private final static String CHAR_SET;

	/**
	 * ��
	 */
	private final static String SALT = "---==UFIDA==--";

	/**
	 * ��Կ��base64���룩
	 */
	private final static String KEY_BASE64 = "YXo+5SNb+NM=";

	/**
	 * ��Կ��ʹ��ͳһ�����룬������ܺͽ��ܣ�
	 */
	private final static SecretKey KEY;

	static {
		// �ַ���ȡϵͳ�ļ����뼯
		// CHAR_SET = System.getProperty("file.encoding");
		CHAR_SET = "UTF-8";

		// ��ԭkey
		BASE64Decoder decorder = new BASE64Decoder();
		byte[] key = null;
		try {
			key = decorder.decodeBuffer(KEY_BASE64);
		} catch (IOException e) {
			if (log.isErrorEnabled()) {
				log.error("ʹ��base64������Կʧ��");
			}
		}
		KEY = new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * ����
	 * 
	 * @param plaintext
	 *            ����
	 * @return ����
	 * @author:lixl
	 * @date:2012-8-14 ����02:28:34
	 */
	public static String encrypt(final String plaintext) {
		// Ŀ������
		String ciphertext = null;
		try {
			Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, KEY);
			// ����
			String plaintextSalted = addSalt(plaintext);
			// ����
			byte[] plaintextSaltedArray = plaintextSalted.getBytes(CHAR_SET);
			byte[] ciphertextArary = c.doFinal(plaintextSaltedArray);
			// base64���룬�������byte[]��String�Ĳ�����
			BASE64Encoder base64Encoder = new BASE64Encoder();
			ciphertext = base64Encoder.encode(ciphertextArary);

			// ciphertext = new String(ciphertextArary, CHAR_SET);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("���ܳ���", e);
			}
		}
		return ciphertext;
	}

	/**
	 * ����
	 * 
	 * @param ciphertext
	 *            ����
	 * @return ����
	 * @author:lixl
	 * @date:2012-8-14 ����02:29:35
	 */
	public static String decrypt(final String ciphertext) {
		// Ŀ������
		String plaintext = null;
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] ciphertextArary = base64Decoder.decodeBuffer(ciphertext);
			Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, KEY);
			// ����
			byte[] plaintextSaltedArray = c.doFinal(ciphertextArary);
			String plaintextSalted = new String(plaintextSaltedArray, CHAR_SET);
			// ϴ��
			plaintext = removeSalt(plaintextSalted);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("���ܳ���", e);
			}
		}
		return plaintext;
	}

	/**
	 * �ж��Ƿ����
	 * 
	 * @param text
	 *            �ı�
	 * @return �Ƿ���ܣ���ѡֵ:0,�Ѽ���;1:δ����
	 * @author:lixl
	 * @date:2012-8-13 ����06:19:16
	 */
	public static boolean isEncode(final String text) {
		// �Ƿ���ܣ���ѡֵ false:û�м���,true:����
		boolean isEncode = false;
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] ciphertextArary = base64Decoder.decodeBuffer(text);
			Cipher c = Cipher.getInstance(CIPHER_ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, KEY);
			// ����
			byte[] plaintextSaltedArray = c.doFinal(ciphertextArary);
			String plaintextSalted = new String(plaintextSaltedArray, CHAR_SET);
			// ��һ���Ƿ����̵�
			if (plaintextSalted.indexOf(SALT) > 0) {
				isEncode = true;
			}
		} catch (IllegalBlockSizeException e) {
			isEncode = false;
		} catch (BadPaddingException e) {
			isEncode = false;
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("�ж��Ƿ���ܹ����г���", e);
			}
		}
		return isEncode;
	}

	/**
	 * ����
	 * 
	 * @param plaintext
	 * @param salt
	 * @return
	 * @author:lixl
	 * @date:2012-8-13 ����02:23:15
	 */
	private static String addSalt(String plaintext) {
		return plaintext + SALT;
	}

	/**
	 * ϴ��
	 * 
	 * @param ciphertext
	 *            ���κ�����ģ�һ���ǽ���֮��
	 * @return
	 * @author:lixl
	 * @date:2012-8-13 ����02:51:53
	 */
	private static String removeSalt(String plaintextSalted) {
		return plaintextSalted.substring(0,
				plaintextSalted.length() - SALT.length());
	}

	// /**
	// * ����base64�������Կ
	// *
	// * @param args
	// * @author:lixl
	// * @date:2012-8-13 ����04:27:35
	// */
	// public static void main(String[] args) {
	// // ����base64�������Կ
	// KeyGenerator key = null;
	// try {
	// key = KeyGenerator.getInstance(KEY_ALGORITHM);
	// } catch (NoSuchAlgorithmException e) {
	// e.printStackTrace();
	// }
	// key.init(56);
	// SecretKey sk = key.generateKey();
	// // return encoder.encode(keyArray);
	// byte[] keyArray = sk.getEncoded();
	// BASE64Encoder encoder = new BASE64Encoder();
	// String keyCoded = encoder.encode(keyArray);
	// System.out.println("���ɵ���Կ��base64���룩Ϊ��" + keyCoded);
	// System.exit(0);
	// }
}
