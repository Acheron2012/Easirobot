package com.ictwsn.web.wechat.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.ictwsn.web.wechat.bean.menu.Button;
import com.ictwsn.web.wechat.bean.menu.Menu;
import com.ictwsn.web.wechat.bean.menu.ViewButton;
import com.ictwsn.web.wechat.bean.AccessToken;
import com.ictwsn.web.wechat.bean.trans.Data;
import com.ictwsn.web.wechat.bean.trans.Parts;
import com.ictwsn.web.wechat.bean.trans.Symbols;
import com.ictwsn.web.wechat.bean.trans.TransResult;

/**
 * 微信工具类
 * @author Zhengye
 *
 */
public class WeixinUtil{
	private static final String APPID = "wxcbea35f7077ac9ce";
	private static final String APPSECRET = "01a79af317b9405fbafc1c486a64aab7";
	
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	private static final String DownLoad_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件以流的方式写入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 文件下载
	 * @param filePath
	 * @param accessToken
	 * @param media_id
	 * @return
	 * @throws IOException
	 */
	public static boolean downLoad (String filepath,String accessToken,String media_id) throws IOException {
		File file = new File(filepath);
		InputStream is =null;
//		if (!file.exists() || !file.isFile()) {
//			throw new IOException("�ļ�������");
//		}
		String url = DownLoad_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID",media_id);
		//URL urlObj = new URL(url);
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();
			//存储到硬盘，原本音频格式为.amr
			ByteArrayOutputStream output = new ByteArrayOutputStream();  
            byte[] buffer = new byte[4096];  
            int r = 0;  

            while ((r = is.read(buffer)) > 0) {  
              output.write(buffer, 0, r);   
            }  
            FileOutputStream fos = new FileOutputStream(filepath);  
            output.writeTo(fos);  
            output.flush();  
            output.close();  
            fos.close();  
			//arm Convert Mp3(在download中实现了)	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet(url);
//		HttpResponse httpResponse = client.execute(httpGet);
//		HttpEntity entity = httpResponse.getEntity();
//		if(entity != null){
//			InputStream in = entity.getContent();
//
//			//String result = EntityUtils.toString(entity,"UTF-8");
//			//System.out.println("�ļ����صķ���ֵΪ��"+result);
//			ByteArrayOutputStream output = new ByteArrayOutputStream();  
//            byte[] buffer = new byte[4096];  
//            int r = 0;  
//  
//            while ((r = in.read(buffer)) > 0) {  
//                output.write(buffer, 0, r);   
//            }  
//            FileOutputStream fos = new FileOutputStream(filepath);  
//            output.writeTo(fos);  
//            output.flush();  
//            output.close();  
//            fos.close();  
//            EntityUtils.consume(entity); 
//			return true;
//		}else {
//			return false;
//		}
	}
	/**
	 * ��ȡaccessToken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 * ��װ�˵�
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		ViewButton button1 = new ViewButton();
		button1.setName("控制台");
		button1.setType("view");
		button1.setUrl("http://115.28.90.164/Weixin/html/index.html");
		
		
//		ClickButton button11 = new ClickButton();
//		button11.setName("添加语音提醒");
//		button11.setType("click");
//		button11.setKey("11");
//		
//		ClickButton button12 = new ClickButton();
//		button12.setName("删除语音提醒");
//		button12.setType("click");
//		button12.setKey("12");
//		
//		ClickButton button13 = new ClickButton();
//		button13.setName("当前语音提醒");
//		button13.setType("click");
//		button13.setKey("13");
//		
//		ClickButton button1 = new ClickButton();
//		button1.setName("语音提醒");
//		button1.setSub_button(new Button[]{button11,button12,button13});
//		
//		
//		ClickButton button21 = new ClickButton();
//		button21.setName("添加日程提醒");
//		button21.setType("click");
//		button21.setKey("21");
//		
//		ClickButton button22 = new ClickButton();
//		button22.setName("删除日程提醒");
//		button22.setType("click");
//		button22.setKey("22");
//		
//		ClickButton button23 = new ClickButton();
//		button23.setName("当前日程提醒");
//		button23.setType("click");
//		button23.setKey("23");
//		
//		ClickButton button2 = new ClickButton();
//		button2.setName("日程提醒");
//		button2.setSub_button(new Button[] {button21,button22,button23});
		
		ViewButton button3 = new ViewButton();
		button3.setName("关于");
		button3.setType("view");
		button3.setUrl("http://www.easinet.cn/cn/index.htm");
		
		
//		Button button = new Button();
//		button.setName("菜单");
//		button.setSub_button(new Button[]{button31,button32});
		
		//menu.setButton(new Button[]{button1,button2,button3});//初始化这3个按钮
		menu.setButton(new Button[]{button1,button3});
		return menu;
	}
	
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	
	
	public static String translate(String source) throws ParseException, IOException{
		String url = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = doGetStr(url);
		String errno = jsonObject.getString("errno");
		Object obj = jsonObject.get("data");
		StringBuffer dst = new StringBuffer();
		if("0".equals(errno) && !"[]".equals(obj.toString())){
			TransResult transResult = (TransResult) JSONObject.toBean(jsonObject, TransResult.class);
			Data data = transResult.getData();
			Symbols symbols = data.getSymbols()[0];
			String phzh = symbols.getPh_zh()==null ? "" : "中文拼音："+symbols.getPh_zh()+"\n";
			String phen = symbols.getPh_en()==null ? "" : "英式音标："+symbols.getPh_en()+"\n";
			String pham = symbols.getPh_am()==null ? "" : "美式音标："+symbols.getPh_am()+"\n";
			dst.append(phzh+phen+pham);
			
			Parts[] parts = symbols.getParts();
			String pat = null;
			for(Parts part : parts){
				pat = (part.getPart()!=null && !"".equals(part.getPart())) ? "["+part.getPart()+"]" : "";
				String[] means = part.getMeans();
				dst.append(pat);
				for(String mean : means){
					dst.append(mean+";");
				}
			}
		}else{
			dst.append(translateFull(source));
		}
		return dst.toString();
	}
	
	public static String translateFull(String source) throws ParseException, IOException{
		String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
		url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
		JSONObject jsonObject = doGetStr(url);
		StringBuffer dst = new StringBuffer();
		List<Map> list = (List<Map>) jsonObject.get("trans_result");
		for(Map map : list){
			dst.append(map.get("dst"));
		}
		return dst.toString();
	}
}
