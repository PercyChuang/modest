package com.modest.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 项目名称：TravelingWeb
 * 
 * 类名称：HttpUtil
 * 
 * 类描述： 接口请求
 * 
 * 创建时间：2014年10月17日 下午3:03:25
 * 
 * Copyright (c) 深圳市XXX有限公司-版权所有
 */
@SuppressWarnings("all")
public class HttpUtil {
	 private final Logger log = LoggerFactory.getLogger(this.getClass());  


	private static HttpUtil httpUtil;
	private int connectionTimeout = new Integer(30); // ConnectionTimeout
	private int timeout = new Integer(20);// Timeout
	private int size = new Integer(819200);// 8192

	private HttpUtil() {

	}

	public static HttpUtil getInstance() {
		
		if (null == httpUtil) {
			synchronized (HttpUtil.class) {
				if (null == httpUtil) {
					httpUtil = new HttpUtil();
				}
			}
		}
		return httpUtil;
	}
	
	private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public String phpPost(String url, Object request) throws ClientProtocolException, ConnectTimeoutException, SocketTimeoutException, HttpHostConnectException, Exception {
		if (null == request) {
			return null;
		}
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, size);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httpRequest = null;
		if(url.indexOf("?") != -1 ){
			 httpRequest = new HttpPost(url);
		}else{
			 httpRequest = new HttpPost(url);
		}
		String paramJson = JSON.toJSONString(request);
		log.info("请求:" + url + "?param=" + paramJson);
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("param", paramJson));
		//String encoderJson = URLEncoder.encode(params.toString(), "utf-8");
		httpRequest.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
		log.info("params : " + request.toString());
        StringEntity se = new StringEntity(request.toString(), "utf-8");
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpRequest.setEntity(se);
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		String strResult = null;
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				InputStream instreams = entity.getContent();
				strResult = convertStreamToString(instreams);
			}
			log.info("返回:" + strResult);
			if (null != strResult && !"".equals(strResult)) {
				return strResult;
			} else {
				return null;
			}
		} else if (httpResponse.getStatusLine().getStatusCode() == 404 || httpResponse.getStatusLine().getStatusCode() == 500) {
			log.info(httpResponse.getStatusLine().getStatusCode()+"");
			throw new Exception(""+httpResponse.getStatusLine().getStatusCode());
		} else {
			log.info(httpResponse.getStatusLine().getStatusCode()+"");
			return null;
		}
	}

	public String smsPost(String url, String mobile, String content) throws Exception {
		HttpPost httpRequest = new HttpPost(url);
		String strResult = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("content", content));
		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				InputStream instreams = entity.getContent();
				strResult = convertStreamToString(instreams);
			}
			log.info("返回:" + strResult);
			if (null != strResult && !"".equals(strResult)) {
				return strResult;
			} else {
				return null;
			}
		} else if (httpResponse.getStatusLine().getStatusCode() == 404 || httpResponse.getStatusLine().getStatusCode() == 500) {
			log.info(httpResponse.getStatusLine().getStatusCode()+"");
			throw new Exception(""+httpResponse.getStatusLine().getStatusCode());
		} else {
			log.info(httpResponse.getStatusLine().getStatusCode()+"");
			return null;
		}
		
	}

	public static String convertStreamToString(InputStream is) {
		StringBuilder sb1 = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;

		try {
			while ((size = is.read(bytes)) > 0) {
				String str = new String(bytes, 0, size, "UTF-8");
				sb1.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb1.toString();
	}

	/**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public String httpGet(String url){
    	String strResult = "";
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                url = URLDecoder.decode(url, "UTF-8");
            } else {
            	log.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
        	log.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }
	
    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url,JSONObject json){
      DefaultHttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost(url);
      JSONObject response = null;
      try {
        StringEntity s = new StringEntity(json.toString());
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");//发送json数据需要设置contentType
        post.setEntity(s);
        HttpResponse res = client.execute(post);
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
          HttpEntity entity = res.getEntity();
          String result = EntityUtils.toString(res.getEntity());// 返回json格式：
          response = JSONObject.parseObject(result);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return response;
    }
  
	
	public static void main(String[] args) throws HttpHostConnectException, ConnectTimeoutException, SocketTimeoutException, ClientProtocolException, Exception {
		//System.out.println(HttpUtil.getInstance().smsPost("http://message.xiaoniuapp.com/xn-sms-service/sms/send", "13686441896","你购买的天天牛180产品"));
		//System.out.println(HttpUtil.getInstance().httpGet("http://10.10.17.147:8080/xn-wechat-activity/activity/recordClickNumber?number=30"));
		 Map<String,String> map = new HashMap<String,String>();
		 map.put("mobile", "13686441896");
		 map.put("context", "测试小牛普惠短信接口");
		 String url = "http://183.56.166.83:8080/xnphws/sendSms";
		System.out.println(HttpUtil.getInstance().phpPost(url,"{\"mobile\":\"13686441896\", \"context\":\"你好，欢迎注册。\"}"));
	}
	
	private  String dealResponse(CloseableHttpClient httpclient,
			HttpUriRequest request) throws IOException {
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(request);
			if ( 200 == response.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);
			}
		} catch (IOException e) {
			log.error("发送异常", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
		}
		return "";

	}

	public String doPost(String url, Map<String, String> map) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, map.get(key)));
		}
		try {
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			return dealResponse(httpclient, httpPost);
		} catch (UnsupportedEncodingException e) {
			log.error("发送异常", e);
		} finally {
			HttpClientUtils.closeQuietly(httpclient);
		}
		return "";
	}

}
