package com.modest.util.http;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Description: ssl工具类
 * @author yangyi
 * @date 2017-1-17
 * @company 深圳利民网金融信息服务有限公司
 */
public class SslUtils {
 
    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
 
    static class miTM implements TrustManager,X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
 
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }
     
    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     * @throws Exception
     */
    public static void ignoreSsl() throws Exception{
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
    
    
    public static byte[] getRemoteBuffer(String remotePath)throws Exception{
	  	URL remoteUrl = new URL(remotePath);
	  	if("https".equalsIgnoreCase(remoteUrl.getProtocol())){
	  		SslUtils.ignoreSsl();
	  	}
	  	ByteArrayOutputStream bs = new ByteArrayOutputStream();
	  	ImageOutputStream imOut = null;
	  	try{
	  		BufferedImage image = javax.imageio.ImageIO.read(remoteUrl);
	        imOut = ImageIO.createImageOutputStream(bs); 
	        ImageIO.write(image, "jpg",imOut); 
			return bs.toByteArray();
	  	}finally{
	  		bs.close();
	  		if(imOut != null){
	  			imOut.close();
	  		}
	  	}
    }
    
    public static void main(String[] args) throws Exception {
		String imgUrl = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
		byte[] imgByte = getRemoteBuffer(imgUrl);
		
		File imageFile = new File("F:/gitwork/modest/modest-modules/modest-util/src/main/resources/BeautyGirl.jpg");  
		if (!imageFile.exists()){
			imageFile.createNewFile();
		}
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        //写入数据  
        outStream.write(imgByte);  
        outStream.close();
	}
}
