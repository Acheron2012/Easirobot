package com.ictwsn.utils.jpush;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
public class Jpush {
	
	public static final String appKey = "54642be1084bdd1303610ee1";
	public static final String masterSecret = "be880fb5915b7f581122bdaa";   
	
	
	public static final String TITLE = "智能孝子";
    public static final String ALERT = "1";
    public static final String MSG_CONTENT = "1";
    public static final String MSG_CONTENT2 = "2";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    
    public  static JPushClient jpushClient=null;
	
	@SuppressWarnings("deprecation")
	public static void testSendPush(String appKey ,String masterSecret) {
		

		 jpushClient = new JPushClient(masterSecret, appKey, 3);
		

       // PushPayload payload=buildPushObject_all_alias_alert();
        PushPayload payload = buildPushObject_diymessage();
        
        try {
        	System.out.println(payload.toString());
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result+"................................");
            
            //LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            //LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
           // LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //LOG.info("HTTP Status: " + e.getStatus());
            //LOG.info("Error Code: " + e.getErrorCode());
           // LOG.info("Error Message: " + e.getErrorMessage());
           // LOG.info("Msg ID: " + e.getMsgId());
        }
	}
	@SuppressWarnings("deprecation")
	public static void audioPush(String appKey,String masterSecret){
		jpushClient = new JPushClient(masterSecret, appKey,3);
		PushPayload payload = buildPushObject_diyAUDIOmessage();
		 try {
	    
	            PushResult result = jpushClient.sendPush(payload);
	           
	                
	        } catch (APIConnectionException e) {
	            
	            
	        } catch (APIRequestException e) {
	           
	        }
	}
	
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}
	
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())//���ý��ܵ�ƽ̨
                .setAudience(Audience.all())//Audience����Ϊall��˵�����ù㲥��ʽ���ͣ������û������Խ��յ�
                .setNotification(Notification.alert(ALERT))
                .build();
    }




    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    
    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                		.setAlert("alert content")
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle("Android Title").build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("extra_key", "extra_value").build())
                		.build())
                .build();
    }
    
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }
    
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
    
    public static PushPayload buildPushObject_diymessage(){
    	 return PushPayload.newBuilder()
                 .setPlatform(Platform.all())
                 .setAudience(Audience.all())
                 .setMessage(Message.newBuilder()
                         .setMsgContent(MSG_CONTENT)
                         .build())
                 .build();
    }
    	 public static PushPayload buildPushObject_diyAUDIOmessage(){
        	 return PushPayload.newBuilder()
                     .setPlatform(Platform.all())
                     .setAudience(Audience.all())
                     .setMessage(Message.newBuilder()
                             .setMsgContent(MSG_CONTENT2)
                             .build())
                     .build(); 	 
    }

	
}
