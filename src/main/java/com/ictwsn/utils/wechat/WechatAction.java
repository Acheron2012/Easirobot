package com.ictwsn.utils.wechat;

import com.ictwsn.weixin.util.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class WechatAction {

    public static Logger logger = LoggerFactory.getLogger(WechatAction.class);

    @RequestMapping(value="/wx.do",method= RequestMethod.GET)
    public String createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
    {
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
            logger.info("微信认证通过");
            out.flush();
            out.println(echostr);
        }
        return null;
    }
}
