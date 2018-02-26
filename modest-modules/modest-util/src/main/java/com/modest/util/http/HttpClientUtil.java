package com.modest.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	public static int HTTP_OK = 200;
	public static String doGet(String uri) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(uri);
		try {
			return dealResponse(httpclient, httpGet);
		} catch (Exception e) {
			log.error(uri, e);
		} finally {
			HttpClientUtils.closeQuietly(httpclient);
		}
		return "";
	}
	
	public static String httpPost(String url,Map<String,String> params) throws Exception {
        // 创建默认的httpClient实例. 
		CloseableHttpClient httpclient = null;
		if(url.contains("https")){
			httpclient = SSLClient.createSSLClientDefault();
		}else{
			httpclient = HttpClients.createDefault();
		}
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for(String key:params.keySet()){
        	formparams.add(new BasicNameValuePair(key,params.get(key)));  
        } 
        UrlEncodedFormEntity uefEntity;  
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "utf-8");  
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();  
                InputStream  in = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = reader.readLine()) != null) {
					//str = new String(str.getBytes(),"utf-8");
					stringBuffer.append(str);
				}
				in.close();
				return stringBuffer.toString();
            } finally {  
                response.close();  
            }  
        }catch (IOException e) {
			log.error(e);
		} finally {  
            // 关闭连接,释放资源    
        	httpclient.close();
        }
		return url;  
    }  
	
	public static String doGetHttps(String uri) {
		CloseableHttpClient httpclient = createSSLInsecureClient();
		HttpGet httpGet = new HttpGet(uri);
		try {
			return dealResponse(httpclient, httpGet);
		} catch (Exception e) {
			log.error(uri, e);
		} finally {
			HttpClientUtils.closeQuietly(httpclient);
		}
		return "";
	}

	private static String dealResponse(CloseableHttpClient httpclient,
			HttpUriRequest request) throws Exception {
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(request);
			if (HTTP_OK == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity,"utf-8");
			}
		} catch (IOException e) {
			log.error(response, e);
		} finally {
			HttpClientUtils.closeQuietly(response);
		}
		return "";

	}

	public static String doPost(String url, Map<String, String> map,
			String charset) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		if (charset == null || "".equals(charset)) {
			charset = "utf-8";
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			String params=EntityUtils.toString(httpPost.getEntity());
			log.info("params:"+params);
			return dealResponse(httpclient, httpPost);
		} catch (UnsupportedEncodingException e) {
			log.error(nvps, e);
		} finally {
			HttpClientUtils.closeQuietly(httpclient);
		}
		return "";
	}

	public static String doPostHttps(String url, Map<String, String> map,
			String charset) throws Exception {
		CloseableHttpClient client = createSSLInsecureClient();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		if (charset == null || "".equals(charset)) {
			charset = "utf-8";
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			String params=EntityUtils.toString(httpPost.getEntity());
			log.info("params:"+params);
			return dealResponse(client, httpPost);

		} catch (Exception e) {
			log.error(nvps, e);
		} finally {
			HttpClientUtils.closeQuietly(client);
		}

		return "";
	}

	private static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			log.error("访问失败KeyManagementException", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("访问失败NoSuchAlgorithmException", e);
		} catch (KeyStoreException e) {
			log.error("访问失败KeyStoreException", e);
		}
		return HttpClients.createDefault();
	}

	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if(httpEntity != null){
			String result = EntityUtils.toString(httpEntity, "UTF-8");
			jsonObject = JSONObject.parseObject(result);
		}
		
		return jsonObject;
	}

	/**
	 * Post请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ClientProtocolException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		HttpEntity httpEntity = new StringEntity(outStr, "UTF-8");
		httpPost.setEntity(httpEntity);
		HttpResponse httpResponse = client.execute(httpPost);
		String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		jsonObject = JSONObject.parseObject(result);
		
		return jsonObject;
	}
	
	public static void main(String[] args) {
		String url="http://192.168.2.130:9000/mockjs/1/to.recharge?token=";
		String result=null;
		try {
			result = doGetHttps(url);
			System.out.println("result="+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
