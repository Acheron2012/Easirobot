package com.ictwsn.weixin.zy.servlet;

import java.awt.font.TextMeasurer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.OverlayLayout;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.http.ParseException;
import org.dom4j.DocumentException;

import com.Ictwsn.Jpush.Jpush;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import com.util.CheckUtil;
import com.util.MessageUtil;
import com.util.WeixinUtil;
import com.zy.po.*;
import com.zy.thread.TokenThread;

import net.sf.json.JSONObject;

import com.zy.database.*;

@SuppressWarnings("serial")
@WebServlet("/WeinxinServlet")
public class WeinxinServlet extends HttpServlet {
	String id = null;
	boolean idFlag= false;//����bug�����޸�
	//windows端语音保存路径
	//final static String localFilepath = "D:\\Program Files\\eclipse-jee-neon-R-win32\\Project\\Downloadfiles\\data";
	//linux服务器端语音保存路径
	final static String localFilepath = "/opt/tomcat9/webapps/Downloadfiles/data/";
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		 // 获取web.xml中配置的参数
        TokenThread.appid = getInitParameter("appid");  
        TokenThread.appsecret = getInitParameter("appsecret");  
  
        //log.info("weixin api appid:{}", TokenThread.appid);  
       // log.info("weixin api appsecret:{}", TokenThread.appsecret);  
  
        // 未配置appid和appsecret时给出
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            //log.error("appid and appsecret configuration error, please check carefully.");  
        } else {  
            // 启动定时获取access_token的线程
            new Thread(new TokenThread()).start();  
        }
        
	}
   
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		super.doDelete(req, resp);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数，nonce参数
		String signature = request.getParameter("signature");
		//时间戳
		String timestamp = request.getParameter("timestamp");
		//随机数
		String nonce = request.getParameter("nonce");
		//随机字符串
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		if(CheckUtil.checkSignature(signature,timestamp,nonce )){
			out.flush();
			out.println(echostr);
		}
	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//boolean idFlag= false;//����bug�����޸�
		try {
			Map<String, String> map;
			map = MessageUtil.xmlToMap(request);
			String ToUserName = map.get("ToUserName");
			String FromUserName = map.get("FromUserName");
			String MsgType = map.get("MsgType");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
				//System.out.println("接收到的是text类型消息");
				String Content = map.get("Content");
				//�������������Ϣ
				if("1".equals(Content)){
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.firstMenu());
				}
				//用户提交的语音id
				else if(Content.contains("id=")){
//					message = MessageUtil.initText(ToUserName, FromUserName, "收到了您的id");
//					out.print(message);
					String sid = Content.substring(Content.indexOf("=")+1);
					//this.id = Integer.parseInt(sid);
					this.id = sid;
					idFlag = true;
					if(idFlag){
						message = MessageUtil.initText(ToUserName, FromUserName, "您输入的语音id为:"+sid+"请录入您要提交的语音");
						out.print(message);
					}
					
					
				}
				else if ("2".equals(Content)) {
					message = MessageUtil.initNewsMessage(ToUserName, FromUserName);
				}
				else if ("3".equals(Content)) {
					message = MessageUtil.initImageMessage(ToUserName, FromUserName);
				}
				else if ("4".equals(Content)) {
					message = MessageUtil.initMusicMessage(ToUserName, FromUserName);
				}
				else if("?".equals(Content)){
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}
			}else if(MessageUtil.MESSAGE_EVNET.equals(MsgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					System.out.println("接收到的是关注事件");
					 String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
				        try {
							int menucreatResult = WeixinUtil.createMenu(TokenThread.accessToken.getToken(), menu);
							if(menucreatResult==0){
								System.out.println("创建菜单成功");		
							}
							else {
								System.out.println("创建菜单失败");
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.welcomeMenuText());
				}else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
					String key = map.get("EventKey");
					if(key.equals("11")){
						message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.firstMenu());
					}
					else if (key.equals("12")) {
						message = MessageUtil.initNewsMessageDelAudioList(ToUserName, FromUserName);
					}
					else if (key.equals("13")) {
//						RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/Audioshow.jsp");
//						dispatcher.forward(request, response);
						message = MessageUtil.initNewsMessageShowAudioList(ToUserName, FromUserName);
					}
					else if (key.equals("21")) {
						message = MessageUtil.initNewsMessageAddSchList(ToUserName, FromUserName);
					}
					else if (key.equals("22")) {
						message = MessageUtil.initNewsMessageDelSchList(ToUserName, FromUserName);
					}
					else if (key.equals("23")) {				
						message = MessageUtil.initNewsMessageShowSchList(ToUserName, FromUserName);
					}
				}
			}else if(MessageUtil.MESSAGE_IMAGE.equals(MsgType)){
				System.out.println("收到的是图片类型消息");
				System.out.println(map.toString());
				//message = MessageUtil.initImageMessage(ToUserName, FromUserName);
				message = MessageUtil.initText(ToUserName, FromUserName, "图片上传成功msgId为:"+map.get("MsgId"));
			}else if (MessageUtil.MESSAGE_VOICE.equals(MsgType)) {
				System.out.println("接收到的是语音消息");
				System.out.println(map.toString());
				if(idFlag){
//					message = MessageUtil.initText(ToUserName, FromUserName, "语音上传中... mediaId为:"+map.get("MediaId"));
//					out.println(message);
//					out.flush();
					String m_medid = map.get("MediaId");
					String fileid = this.id;//暂时性，具体实现细节需讨论
					//将用户上传到微信服务器的语音提醒音频下载到服务器目录下，并在相应数据库中进行增加或修改
					if((WeixinUtil.downLoad((localFilepath+fileid +".amr"), TokenThread.accessToken.getToken(), m_medid))==true){
						if(AudioDB.updateDB(Integer.parseInt(this.id))){
							idFlag = false;
							//Jpush.buildPushObject_diyAUDIOmessage();
							Jpush.audioPush(Jpush.appKey,Jpush.masterSecret);
							message = MessageUtil.initText(ToUserName, FromUserName, "语音上传成功");
							out.print(message);
							out.flush();
						}else{
							message = MessageUtil.initText(ToUserName, FromUserName, "语音上传失败");
							out.print(message);
							out.flush();
						}
					}
					else{
						message = MessageUtil.initText(ToUserName, FromUserName, "语音上传失败");
						out.print(message);
						out.flush();
					}
				}
				else {
					message = MessageUtil.initText(ToUserName, FromUserName, "无效，请按照说明操作!");
				}
			}
			
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
	        out.close();  
		}
		
	}

}
