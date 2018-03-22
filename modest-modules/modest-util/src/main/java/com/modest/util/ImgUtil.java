package com.modest.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.modest.util.http.SslUtils;

public class ImgUtil {

	public static byte[] getRemoteBuffer(String remotePath) throws Exception {
		URL remoteUrl = new URL(remotePath);
		if ("https".equalsIgnoreCase(remoteUrl.getProtocol())) {
			SslUtils.ignoreSsl();
		}
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ImageOutputStream imOut = null;
		try {
			BufferedImage image = javax.imageio.ImageIO.read(remoteUrl);
			imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(image, "jpg", imOut);
			return bs.toByteArray();
		} finally {
			bs.close();
			if (imOut != null) {
				imOut.close();
			}
		}
	}

	public static void rmImg(String url, String svPath) throws Exception {
		String imgUrl = url;//
		byte[] imgByte = getRemoteBuffer(imgUrl);
		File imageFile = new File(svPath);
		if (!imageFile.exists()) {
			imageFile.createNewFile();
		}
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(imgByte);
		outStream.close();
	}

	/**
	 * 将图片转换成Base64编码
	 * 
	 * @param imgFile
	 *            待处理图片
	 * @return
	 */
	public static String getImgStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 *            图片数据
	 * @param imgFilePath
	 *            保存图片全路径地址
	 * @return
	 */
	public static boolean generateImage(String imgStr, String imgFilePath) {
		// Base64解码
		byte[] b = Base64.decodeBase64(imgStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}

		try {
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 *            图片数据
	 * @param imgFilePath
	 *            保存图片全路径地址
	 * @return
	 */
	public static byte[] generateImageByteArray(String imgStr) {
		// Base64解码
		byte[] b = Base64.decodeBase64(imgStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}

		return b;
	}

	public static void main(String[] args) throws Exception {
		String url = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
		String svPath = "F:/gitwork/modest/modest-modules/modest-util/src/main/resources/BeautyGirl.jpg";
		rmImg(url, svPath);
		System.out.println("复制成功!");
		String x = getImgStr(svPath);
		String xx = "F:/gitwork/modest/modest-modules/modest-util/src/main/resources/xx.jpg";
		generateImage(x,xx);
	}

}
