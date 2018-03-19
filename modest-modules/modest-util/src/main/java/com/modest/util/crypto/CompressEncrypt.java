package com.modest.util.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 带压缩功能的加密DES工具类 1.原始字符串超大，加密后的字符串越小 2.密钥不要太长了。
 * 整个上下文用同一个对象，但key用不同的key
 * 线程安全
 * @author EDMOND CHUANG
 */
@SuppressWarnings("restriction")
public class CompressEncrypt {
	
	private String key;
	private CompressEncrypt(){};
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public static CompressEncrypt  getInstance(String key) {
		CompressEncrypt instance = new CompressEncrypt();
		instance.setKey(key);
		return instance;
	}
	
	/**
	 * 加密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String enCode(String content) throws Exception {
		BASE64Encoder base64en = new BASE64Encoder();
        try {
        	content = compress(content);
            return base64en.encode(desEnCode(content.getBytes("UTF-8")));
        }catch(Exception e){
           e.printStackTrace();
        }
		return content;
	}

	/**
	 * 解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String deCode(String content) throws Exception {
		BASE64Decoder base64De = new BASE64Decoder();
        try {
            String result =  new String(desDeCode(base64De.decodeBuffer(content)), "UTF-8");
            return uncompress(result);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
	}
	
	
	private byte[] desEnCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(this.key));
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private byte[] desDeCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, generateKey(this.key));
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	private static Key generateKey(String keyStr) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(keyStr.getBytes()));
			Key key = _generator.generateKey();
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private  String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	private  String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		return out.toString();
	}

	public static void main(String[] args) {
		try {
			CompressEncrypt cri = CompressEncrypt.getInstance("lmwkey");
			
	//		String str = "rrr!!!!!!@#$%^&*()_+尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！尊敬的用户，您于2017-06-23 10:58:17提交了提现申请，我们将立即审核；提现金额：102.00元，手续费：2.00元，实际到账金额：100.00元。如有疑问，请联系在线客服或拨打客服热线400-8318-999！小朋友";
			String str = "消息内容(消息内容是根据消息模板表中的消息模板内容生成)消息内容(消息内容是根据消息模板表中的消息模板内容生成)消息内容(消息内容是根据消息模板表中的消息模板内容生成)消息内容(消息内容是根据消息模板表中的消息模板内容生成)消息内容(消息内容是根据消息模板表中的消息模板内容生成)"; //内容少的，不太合适用这个加密，因为加密后会更大。内容多的，用这个，加密后会很少
			System.out.println("加密前大小:"+str.length());
			String content = cri.enCode(str);
			System.out.println("加密后："+content);
			System.out.println("加密后大小："+content.length());
			
			System.out.println(cri.deCode(content));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
