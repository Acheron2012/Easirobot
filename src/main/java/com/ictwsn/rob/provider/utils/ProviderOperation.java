package com.ictwsn.rob.provider.utils;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018-05-24.
 */
public class ProviderOperation {

	public static Logger logger = LoggerFactory.getLogger(ProviderOperation.class);

	private static final String ACCOUNT = "zky_robot";
	private static final String PWD = "zNBcPMAR9Lc26XExzj8o377k9cKL62hr";
	private static final String KEY = "DONv4LS3Xu76sWguI4pMgklzmMdvVCQF";
	private static final String LOGIN_URL = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=10000";
	private static final String USER_REGISTER = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=20000";
	private static final String USER_UPDATE = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=21002";
	private static final String CALL = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=30000";
	private static final String GET_USER_MESSAGE = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=20200";
	private static final String IS_ACCESS = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=20001";
	private static final String GET_RESULT = "http://api2.ytone.com.cn/api/ytone.ashx?cmd=30001";
	private static final String PUSH_PHY = "http://api.ytone.com.cn/lexin/health.ashx";

	public static void main(String[] args) {
//		System.out.println(loginProvider());
//		System.out.println(userRegister("9107510424",null));
//		System.out.println(userUpdate("9107510424"));
//		System.out.println(getUserMessage("9107510424","15313690617"));
//		{"code":1000,"msg":"注册成功","no":"1491685"}
//		System.out.println(isAccess("9107510424"));
//		System.out.println(call("9107510424"));
		System.out.println(getResult("9107510424"));
	}
	
	public static String getResult(String vcode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		String serialNumber = "2474982";
		jsonObject.put("serialNumber", serialNumber);
		String signature = YTUtil.MD5(ACCOUNT + serialNumber + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		System.out.println(jsonObject.toString());
		return postMethod(GET_RESULT, jsonObject.toString());
	}
	
	public static String call(String vcode,String callPhone,int callType) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		jsonObject.put("callPhone", callPhone);
//		1表示:紧急呼叫 2表示: 综合服务
		jsonObject.put("callType",callType);
		String signature = YTUtil.MD5(ACCOUNT + callPhone + callType  + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		System.out.println(jsonObject.toString());
		return postMethod(CALL, jsonObject.toString());
	}

	public static String childUpdate(String vcode ,String device_id, OldBean oldBean,ChildBean childBean) {
		long id = oldBean.getUser_service_id();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		// 相关联系人
		JSONArray userRelateds = new JSONArray();
		JSONObject relatedObject = new JSONObject();
		relatedObject.put("name", childBean.getChild_name());
		relatedObject.put("user_id", id);
		relatedObject.put("id", 0);
		relatedObject.put("addr", childBean.getChild_address());
		relatedObject.put("age", 80);
		relatedObject.put("birthday", "");
		relatedObject.put("distance", "");
		relatedObject.put("gender", 0);
		relatedObject.put("is_emergency_contact", 1);
		relatedObject.put("is_online_hall", 0);
		relatedObject.put("is_sms", 1);
		relatedObject.put("location", "");
		relatedObject.put("mobile", String.valueOf(childBean.getChild_phone()));
		relatedObject.put("tel", "");
		relatedObject.put("passwd", "");
		//relationship为2表示关系为子女
		relatedObject.put("relationship", 2);
		relatedObject.put("spare_key", 0);
		relatedObject.put("spare_key_explain", "");
		userRelateds.add(relatedObject);
		jsonObject.put("userRelateds", userRelateds);
		// 用户信息
		JSONObject users = new JSONObject();
		users.put("id",id);
		users.put("addr", oldBean.getUser_address());
		users.put("age", oldBean.getUser_age());
		// 所在城区
		users.put("area", oldBean.getUser_area());
		users.put("birthday", "");
		users.put("blood", "");
		users.put("body_weight", oldBean.getUser_weight());
		users.put("call_phone", oldBean.getUser_phone());
		users.put("city", oldBean.getUser_city());
		users.put("community", "");
		users.put("device_class", "机器人");
		users.put("device_type", "援通移动型");
		users.put("deviceno", device_id);
		users.put("disease_history", "");
		users.put("drug_allergy", "");
		users.put("economic_conditions", "");
		users.put("educational_background", "");
		users.put("first_aid_nav", "");
		users.put("gender", oldBean.getUser_sex());
		users.put("health", "");
		users.put("height", oldBean.getUser_height());
		users.put("hometown", "");
		String identity_card_number = oldBean.getIdentity_card();
		users.put("identity_card_number", identity_card_number);
		users.put("is_help_age", 0);
		users.put("is_medicare", 0);
		users.put("is_nursing_care", 0);
		users.put("job", "");
		users.put("map_marker", "");
		users.put("medicare_card_number", "");
		users.put("memo", "");
		String name = oldBean.getUser_name();
		users.put("name", name);
		users.put("province", oldBean.getUser_province());
		users.put("street", oldBean.getUser_street());
		users.put("work_unit", "");
		users.put("group_id", 0);
		jsonObject.put("users", users);
		String signature = YTUtil.MD5(ACCOUNT + id + name + identity_card_number + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		logger.info(jsonObject.toString());
		return postMethod(USER_UPDATE, jsonObject.toString());
	}
	
	public static String userUpdate(String vcode ,String device_id, OldBean oldBean) {
		long id = oldBean.getUser_service_id();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		// 相关联系人
		JSONArray userRelateds = new JSONArray();
		JSONObject relatedObject = new JSONObject();
		relatedObject.put("name", "天合机器人");
		relatedObject.put("user_id", id);
		relatedObject.put("id", 0);
		relatedObject.put("addr", "");
		relatedObject.put("age", 80);
		relatedObject.put("birthday", "");
		relatedObject.put("distance", "");
		relatedObject.put("gender", 0);
		relatedObject.put("is_emergency_contact", 1);
		relatedObject.put("is_online_hall", 0);
		relatedObject.put("is_sms", 0);
		relatedObject.put("location", "");
		relatedObject.put("mobile", "");
		relatedObject.put("tel", "");
		relatedObject.put("passwd", "");
		relatedObject.put("relationship", 0);
		relatedObject.put("spare_key", 0);
		relatedObject.put("spare_key_explain", "");
		userRelateds.add(relatedObject);
		jsonObject.put("userRelateds", userRelateds);
		// 用户信息
		JSONObject users = new JSONObject();
		users.put("id",id);
		users.put("addr", oldBean.getUser_address());
		users.put("age", oldBean.getUser_age());
		// 所在城区
		users.put("area", oldBean.getUser_area());
		users.put("birthday", "");
		users.put("blood", "");
		users.put("body_weight", oldBean.getUser_weight());
		users.put("call_phone", oldBean.getUser_phone());
		users.put("city", oldBean.getUser_city());
		users.put("community", "");
		users.put("device_class", "机器人");
		users.put("device_type", "援通移动型");
		users.put("deviceno", device_id);
		users.put("disease_history", "");
		users.put("drug_allergy", "");
		users.put("economic_conditions", "");
		users.put("educational_background", "");
		users.put("first_aid_nav", "");
		users.put("gender", oldBean.getUser_sex());
		users.put("health", "");
		users.put("height", oldBean.getUser_height());
		users.put("hometown", "");
		String identity_card_number = oldBean.getIdentity_card();
		users.put("identity_card_number", identity_card_number);
		users.put("is_help_age", 0);
		users.put("is_medicare", 0);
		users.put("is_nursing_care", 0);
		users.put("job", "");
		users.put("map_marker", "");
		users.put("medicare_card_number", "");
		users.put("memo", "");
		String name = oldBean.getUser_name();
		users.put("name", name);
		users.put("province", oldBean.getUser_province());
		users.put("street", oldBean.getUser_street());
		users.put("work_unit", "");
		users.put("group_id", 0);
		jsonObject.put("users", users);
		String signature = YTUtil.MD5(ACCOUNT + id + name + identity_card_number + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		logger.info(jsonObject.toString());
		return postMethod(USER_UPDATE, jsonObject.toString());
	}

	public static String userRegister(String vcode, OldBean oldBean) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		// 相关联系人
		JSONArray userRelateds = new JSONArray();
		JSONObject relatedObject = new JSONObject();
		relatedObject.put("name", "天合机器人");
		relatedObject.put("user_id", 0);
		relatedObject.put("id", 0);
		relatedObject.put("addr", "");
		relatedObject.put("age", 80);
		relatedObject.put("birthday", "");
		relatedObject.put("distance", "");
		relatedObject.put("gender", 0);
		relatedObject.put("is_emergency_contact", 1);
		relatedObject.put("is_online_hall", 0);
		relatedObject.put("is_sms", 0);
		relatedObject.put("location", "");
		relatedObject.put("mobile", "");
		relatedObject.put("tel", "");
		relatedObject.put("passwd", "");
		relatedObject.put("relationship", 0);
		relatedObject.put("spare_key", 0);
		relatedObject.put("spare_key_explain", "");
		userRelateds.add(relatedObject);
		jsonObject.put("userRelateds", userRelateds);
		// 用户信息
		JSONObject users = new JSONObject();
		users.put("id",0);
		users.put("addr", oldBean.getUser_address());
		users.put("age", oldBean.getUser_age());
		// 所在城区
		users.put("area", oldBean.getUser_area());
		users.put("birthday", "");
		users.put("blood", "");
		users.put("body_weight", oldBean.getUser_weight());
		users.put("call_phone", oldBean.getUser_phone());
		users.put("city", oldBean.getUser_city());
		users.put("community", "");
		users.put("device_type", "援通移动型");
		users.put("disease_history", "");
		users.put("drug_allergy", "");
		users.put("economic_conditions", "");
		users.put("educational_background", "");
		users.put("first_aid_nav", "");
		users.put("gender", oldBean.getUser_sex());
		users.put("health", "");
		users.put("height", oldBean.getUser_height());
		users.put("hometown", "");
		String identity_card_number = oldBean.getIdentity_card();
		users.put("identity_card_number", identity_card_number);
		users.put("is_help_age", 0);
		users.put("is_medicare", 0);
		users.put("is_nursing_care", 0);
		users.put("job", "");
		users.put("map_marker", "");
		users.put("medicare_card_number", "");
		users.put("memo", "");
		String name = oldBean.getUser_name();
		users.put("name", name);
		users.put("province", oldBean.getUser_province());
		users.put("street", oldBean.getUser_street());
		users.put("work_unit", "");
		users.put("group_id", 0);
		jsonObject.put("users", users);
		String signature = YTUtil.MD5(ACCOUNT + name + identity_card_number + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		System.out.println(jsonObject.toString());
		return postMethod(USER_REGISTER, jsonObject.toString());
//		return null;
	}

	public static String getUserMessage(String vcode, String call_phone) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		jsonObject.put("call_phone", call_phone);
		jsonObject.put("user_id", 0);
		jsonObject.put("device_code", "");
		String signature = YTUtil.MD5(ACCOUNT + call_phone + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		System.out.println(jsonObject.toString());
//		return null;
		return postMethod(GET_USER_MESSAGE, jsonObject.toString());
	}

	public static String pushPhysiology(long user_service_id,String device_id, PhyBean phyBean) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dataType","BP");
		JSONObject dataObject = new JSONObject();
		dataObject.put("userId",user_service_id);
		dataObject.put("deviceId","b208053808fd");
		dataObject.put("sn",device_id);
		dataObject.put("userNo",1);
		Date date = Tools.getISODateByHourAndMinute(phyBean.getTime());
		dataObject.put("measurementDate",date.getTime());
		dataObject.put("systolicPressure",phyBean.getBlood_high());
		dataObject.put("diastolicPressure",phyBean.getBlood_low());
		dataObject.put("heartRate",phyBean.getHr());
		dataObject.put("temperature","");
		dataObject.put("irregularHeartbeat",false);
		dataObject.put("movementError",false);
		dataObject.put("rfid","");
		jsonObject.put("data",dataObject);
		System.out.println(jsonObject.toString());
		return postMethod(PUSH_PHY, jsonObject.toString());
	}

	public static String loginProvider() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", "");
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		String key = KEY.substring(0, 8);
		byte[] encrypted = DESUtil.DES_CBC_Encrypt(PWD.getBytes(), key.getBytes());
		String password = DESUtil.byteToHexString(encrypted);
		jsonObject.put("password", password);
		String signature = YTUtil.MD5(ACCOUNT + PWD + timespace + KEY);
		jsonObject.put("signature", signature);
		return postMethod(LOGIN_URL, jsonObject.toString());
	}
	
	public static String isAccess(String vcode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vcode", vcode);
		String timespace = Tools.getStringByNowDatetime();
		jsonObject.put("timespace", timespace);
		jsonObject.put("account", ACCOUNT);
		int deviceno = 1491685;
		jsonObject.put("deviceno", deviceno);
		String usercard = "510302199308040018";
		jsonObject.put("usercard", usercard);
		String signature = YTUtil.MD5(ACCOUNT + deviceno + usercard + timespace + vcode + KEY);
		jsonObject.put("signature", signature);
		System.out.println(jsonObject.toString());
		return postMethod(IS_ACCESS, jsonObject.toString());
	}

	public static String postMethod(String url, String json) {
		HttpEntity httpEntity = HttpUtil.httpPost(url, null, json);
		if (httpEntity != null) {
			try {
				String s = EntityUtils.toString(httpEntity);
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
