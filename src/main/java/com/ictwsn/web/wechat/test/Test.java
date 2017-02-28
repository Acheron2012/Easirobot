package com.ictwsn.web.wechat.test;

import com.ictwsn.web.wechat.po.AccessToken;
import com.ictwsn.web.wechat.util.WeixinUtil;
import org.apache.http.ParseException;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		AccessToken accessToken = WeixinUtil.getAccessToken();
		System.out.println("票据:"+accessToken.getToken());
		System.out.println("有效时间"+accessToken.getExpiresIn());
		//F5VQzM5o2vx-eD2Gl7bHXPh2ffWyIZTvGPArSj8Ox1OCwPhmGnU6xdiIH5OnW_oo
		//WeixinUtil.downLoad("C:\\Users\\Administrator\\Desktop\\weixindownload\\1.amr",	"xk0LuG7i8kYBbulwkr_5Np6VhYYuQ0fnouaZQ1uO64hrsO7vfmFVHWi1xn_YaIoTksPSpuHW81a6xeqnSr_VNkVnGSj06-9yONBXIvL6eSQd4cvnuDbA31g5WnWSZ0wmPQXbAFABCJ", "A6ayiqoJvrN0xlt--u3p-fs41qqpvKAQ3jLBY3q7cXDR6Ua_6EA2v6IekRFZFyot");
//		try {
//			WeixinUtil.upload("C:\\Users\\Administrator\\Desktop\\1.jpg","xk0LuG7i8kYBbulwkr_5Np6VhYYuQ0fnouaZQ1uO64hrsO7vfmFVHWi1xn_YaIoTksPSpuHW81a6xeqnSr_VNkVnGSj06-9yONBXIvL6eSQd4cvnuDbA31g5WnWSZ0wmPQXbAFABCJ", "image");
//		} catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
