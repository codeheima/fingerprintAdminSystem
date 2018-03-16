package org.ma.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpByJdk {

	private static Logger log = Logger.getLogger(HttpByJdk.class);
	
	/**
	 * 查询条件
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param timeOut 超时时间,至少大于1000
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static HttpResult sendGet(String url, String param, int timeOut) {
		// 超时时间 ： 20miao
		if (timeOut <= 1000) {
			timeOut = 20000;
		}
		HttpResult result = new HttpResult();
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl
					.openConnection();
			
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36");

			// 超时时间
			connection.setConnectTimeout(timeOut);
			
			// 建立实际的连接
			connection.connect();
			
		
			int responseCode = connection.getResponseCode();
			result.setResponseCode(responseCode);

			// 获取所有响应头字段
			/*
			 * Map<String, List<String>> map = connection.getHeaderFields(); //
			 * 遍历所有的响应头字段 for (String key : map.keySet()) {
			 * System.out.println(key + "--->" + map.get(key)); }
			 */
			// 定义 BufferedReader输入流来读取URL的响应
			long l1 = System.currentTimeMillis();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream(),"UTF-8"));
			} else {
				in = new BufferedReader(new InputStreamReader(
						(InputStream) connection.getErrorStream(),"UTF-8"));
			}
			
			StringBuilder sb = new StringBuilder();
			char [] tempArr = new char[2048];
			int tempIndex = -1;
			while ((tempIndex = in.read(tempArr)) > 0) {
				sb.append(tempArr,0,tempIndex);
			}
			/*
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			*/
			result.setCommonResp(true);
			result.setResponseMsg(sb.toString());
			long l2 = System.currentTimeMillis();
			System.out.println("字符串处理时间：" + (l2 - l1));
		} catch (ConnectException connErr) {
			if (connErr.getMessage().contains("timed out")) {
				result.setTimeOut(true);
			}
			result.setCommonResp(false);
			result.setResponseMsg(connErr.getMessage());
		} catch(SocketTimeoutException e1){
			if (e1.getMessage().contains("timed out")) {
				result.setTimeOut(true);
			}
			result.setCommonResp(false);
			result.setResponseMsg(e1.getMessage());
		}catch (Exception e) {
			log.error("发送GET请求出现异常！" + e);
			result.setCommonResp(false);
			result.setResponseMsg(e.getMessage());
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			
			int responseCode = conn.getResponseCode();
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String [] args){
		 HttpResult  result = sendGet("http://blog.csdn.net/iris0123456/article/details/6535819",null,1500);
		 if(result.isTimeOut()){
			 System.out.println("timeout");
		 }else{
			 System.out.println( result.getResponseCode());
		 }
	}
}
