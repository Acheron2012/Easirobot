package com.ictwsn.rob.message.action;

import com.ictwsn.rob.message.bean.MessageBean;
import com.ictwsn.rob.message.service.MessageService;
import com.ictwsn.rob.voice.action.VoiceAction;
import com.ictwsn.utils.jpush.ChatbotPush;
import com.ictwsn.utils.tools.ActionTool;
import com.ictwsn.utils.tools.ChangeAmrToMp3;
import com.ictwsn.utils.tools.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-04-27.
 */
@Controller
public class MessageAction {

    public static Logger logger = LoggerFactory.getLogger(MessageAction.class);

    /**
     * 获取音频文件目录
     */
    private static final String ROOT_PATH = Tools.getConfigureValue("audio.path");

    private static final String AUDIO_URL = "http://easirobot.zhongketianhe.com.cn:8080/Easirobot/rob/get_audio";

    private static final String DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    @Resource
    MessageService messageService;

    /**
     * admin=1是支部上级，0是子女
     */
    @RequestMapping("/wechat/download_message")
    public String downloadWechatAudio(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "open_id", required = true) String open_id,
                                      @RequestParam(value = "accessToken", required = true) String accessToken,
                                      @RequestParam(value = "admin", required = true) Integer admin,
                                      @RequestParam(value = "media_id", required = true) String media_id,
                                      @RequestParam(value = "older_name", required = true) String older_name)
            throws IOException, ServletException {
        String url = DOWNLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", media_id);
        logger.info("请求下载的音频url为：{}", url);
        List<String> device_ids = new ArrayList<String>();
        //判断是否是管理员
        if (admin == 1) {
            //是否是群发
            if("全部".equals(older_name.trim())) {
                //查询该管理员下所有的device_id
                device_ids = messageService.getDeviceIdsByAdminOpenId(open_id);
            } else {
                //根据admin_id和old_name确定发送的device_id
                device_ids = messageService.getDeviceIdsByAdminOpenIdAndOldName(open_id,older_name);
                //未搜索到该名字，不做操作
                if(device_ids == null) {
                    return null;
                }
            }
        } else {
            //在子女列表内获取device_id *
            String device_id = messageService.getDeviceIdByChildOpenId(open_id);
            device_ids.add(device_id);
        }
        logger.info("device长度为:{},是否是管理员：{},发送给:{}", device_ids.size(), admin,older_name);
        for (String device_id : device_ids) {
            //通过oepn_id和deviev_id创建文件夹
            messageService.createDirByDeviceIdAndOpenId(device_id, open_id);
            //通过时间创建音频文件的名字
            String audio_name = Tools.getStringByNowDatetime();
            //下载音频文件放入文件夹
            boolean flag = messageService.downloadAmrByDeviceAndOpenId(device_id, open_id, audio_name, url);
            logger.info("下载完成");
            //转换音频文件为MP3
            String sourcePath = ROOT_PATH + "/" + "amr" + "/" + device_id + "/" + open_id + "/" +
                    audio_name + ".amr";
            String targetPath = ROOT_PATH + "/" + "mp3" + "/" + device_id + "/" + open_id + "/" +
                    audio_name + ".mp3";
            ChangeAmrToMp3.changeToMp3(sourcePath, targetPath);
            logger.info("转换为mp3完成");
            //更新数据库状态
            MessageBean messageBean = new MessageBean();
            messageBean.setDevice_id(device_id);
            messageBean.setOpen_id(open_id);
            messageBean.setAudio_status(0);
            messageBean.setAudio_name(audio_name);
            messageBean.setIs_admin(admin);
            messageBean.setAudio_time(Tools.getDateByISOString(audio_name));
            int insertFlag = messageService.insertAudio(messageBean);
            logger.info("音频插入状态：{}", insertFlag);
            //推送消息
            String request_url = AUDIO_URL + "?device_id=" + device_id + "&open_id=" + open_id +
                    "&audio_name=" + audio_name + "&admin=" + admin;
            ChatbotPush.testSendPushWithCustomConfig(device_id, request_url, 7);
            logger.info("消息已推送：{}", request_url);
        }
        return null;
    }

    @RequestMapping("/rob/confirm_message")
    public void downloadConfirmMessage(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "device_id", required = true) String device_id,
                                       @RequestParam(value = "open_id", required = true) String open_id,
                                       @RequestParam(value = "audio_name", required = true) String audio_name,
                                       @RequestParam(value = "admin", required = false) Integer admin)
            throws IOException, ServletException {
        messageService.updateAudio(device_id, open_id, audio_name);
        String voiceResult = "语音消息已确认";
        //返回文字内容
        ActionTool.responseToJSON(response, voiceResult, VoiceAction.ACCESS);
    }


    @RequestMapping("/rob/get_unread_message")
    public void downloadWechatAudio(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "device_id", required = true) String device_id)
            throws IOException, ServletException {
        List<MessageBean> fileNames = messageService.getMessageBeanByDeviceId(device_id);
        JSONObject jsonObject = new JSONObject();
        // 0代表未有，1代表1条，2代表两条以上
        //未查询到未读消息
        if (fileNames.size() == 0) {
            jsonObject.put("length", 0);
            jsonObject.put("description", "您没有未读消息");
            jsonObject.put("result", new JSONArray());
            //有未读消息
        } else {
            //存放长度
            jsonObject.put("length", fileNames.size());
            JSONArray jsonArray = new JSONArray();
            for (MessageBean messageBean : fileNames) {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("device_id", messageBean.getDevice_id());
                jsonMessage.put("open_id", messageBean.getOpen_id());
                jsonMessage.put("audio_name", messageBean.getAudio_name());
                jsonMessage.put("time", Tools.getStringWithChineseByISODate(messageBean.getAudio_time()));
                jsonMessage.put("url", AUDIO_URL + "?device_id=" + messageBean.getDevice_id() + "&open_id=" + messageBean.getOpen_id() +
                        "&audio_name=" + messageBean.getAudio_name());
                if (messageBean.getIs_admin() == 1) {
                    jsonMessage.put("description", "支部消息留言");
                } else {
                    jsonMessage.put("description", "子女消息留言");
                }
                jsonArray.add(jsonMessage);
            }
            jsonObject.put("result", jsonArray);
        }
        Tools.responseToJSON(response, jsonObject.toString());
        logger.info("已请求未读消息：{}", jsonObject.toString());
    }

    @RequestMapping("/rob/get_audio")
    public void downloadGetAudio(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "device_id", required = true) String device_id,
                                 @RequestParam(value = "open_id", required = true) String open_id,
                                 @RequestParam(value = "audio_name", required = true) String audio_name,
                                 @RequestParam(value = "admin", required = false) Integer admin
    )
            throws IOException, ServletException {
        String filePath = ROOT_PATH + "/mp3/" + device_id + "/" + open_id + "/" + audio_name + ".mp3";
        InputStream inputStream = new FileInputStream(filePath);
        Tools.downloadAudioFile(inputStream, response);
        logger.info("下载完成。device_id:{}\topen_id:{}", device_id, open_id);
    }


}
