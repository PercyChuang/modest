package com.modest.util.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
	private void setKey(String key) {
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
            return base64en.encode(content.getBytes("UTF-8"));
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
		content = new String(base64De.decodeBuffer(content), "UTF-8");
		return uncompress(content);
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
			CompressEncrypt cri = CompressEncrypt.getInstance("lmw123456");
			
			String str = "拨打客服热线400-8318-999！";
			System.out.println("原始大小："+str.length());
			String content = cri.enCode(str);
			System.out.println(content);
			System.out.println("加密后的大小："+content.length());
			
			System.out.println(cri.deCode(content));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
