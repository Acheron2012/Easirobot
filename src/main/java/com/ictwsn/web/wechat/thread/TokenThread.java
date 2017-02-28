package com.ictwsn.web.wechat.thread;

import com.ictwsn.web.wechat.util.WeixinUtil;
import com.ictwsn.web.wechat.po.AccessToken;
import org.apache.http.ParseException;

import java.io.IOException;

public class TokenThread implements Runnable {

	
    // 第三方用户唯一凭证
    public static String appid = "";  
    // 第三方用户唯一密钥 
    public static String appsecret = "";  
    public static AccessToken accessToken = null;

	public void run() {
		// TODO Auto-generated method stub

		        while (true) {  
		            try {  
		                try {
							accessToken = WeixinUtil.getAccessToken();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
		                if (null != accessToken) {  
		                    
		                    // 休眠7000毫秒  
		                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
		                } else {  
		                    // 如果access_token为null，60秒后再获取 
		                    Thread.sleep(60 * 1000);  
		                }  
		            } catch (InterruptedException e) {  
		                try {  
		                    Thread.sleep(60 * 1000);  
		                } catch (InterruptedException e1) {  
		                     
		                }  
		            }  
		        }   
	}

}
