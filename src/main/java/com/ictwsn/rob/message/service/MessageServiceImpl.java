package com.ictwsn.rob.message.service;

import com.ictwsn.rob.message.bean.MessageBean;
import com.ictwsn.rob.message.dao.MessageDao;
import com.ictwsn.utils.tools.BaseDao;
import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Tools;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-04-28.
 */
@Service
public class MessageServiceImpl extends BaseDao implements MessageService {

    public static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * 获取音频文件目录
     */
    public static final String ROOT_PATH = Tools.getConfigureValue("audio.path");

    @Override
    public String getDeviceIdByChildOpenId(String open_id) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).getDeviceIdByChildOpenId(open_id);
    }

    /**
     * 通过open_id 和 device_id创建音频文件夹
     *
     * @param open_id
     * @param device_id
     */
    @Override
    public void createDirByDeviceIdAndOpenId(String device_id, String open_id) {

        //创建amr目录
        File amrFile = new File(ROOT_PATH + "/" + "amr" + "/" + device_id + "/" + open_id);
        if (!amrFile.exists()) {
            amrFile.mkdirs();
        }
        //创建mp3目录
        File mp3File = new File(ROOT_PATH + "/" + "mp3" + "/" + device_id + "/" + open_id);
        if (!mp3File.exists()) {
            mp3File.mkdirs();
        }

    }

    @Override
    public boolean downloadAmrByDeviceAndOpenId(String device_id, String open_id, String fileName, String url) {

        File amrFile = new File(ROOT_PATH + "/" + "amr" + "/" + device_id + "/" + open_id + "/" +
                fileName + ".amr");

        InputStream is = null;
        try {

            //设置请求头
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("Host", "baike.sogou.com");
            HttpEntity httpEntity = HttpUtil.httpGet(url, headers);

            is = httpEntity.getContent();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int r = 0;
            while ((r = is.read(buffer)) > 0) {
                output.write(buffer, 0, r);
            }
            FileOutputStream fos = new FileOutputStream(amrFile);
            output.writeTo(fos);
            output.flush();
            output.close();
            is.close();
            fos.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int insertAudio(MessageBean messageBean) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).insertAudio(messageBean);
    }

    @Override
    public int updateAudio(String device_id, String open_id, String audio_name) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).updateAudio(device_id, open_id, audio_name);
    }

    @Override
    public List<MessageBean> getMessageBeanByDeviceId(String device_id) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).getMessageBeanByDeviceId(device_id);
    }

    @Override
    public List<String> getDeviceIdsByAdminOpenId(String open_id) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).getDeviceIdsByAdminOpenId(open_id);
    }

    @Override
    public List<String> getDeviceIdsByAdminOpenIdAndOldName(String open_id, String older_name) {
        return this.sqlSessionTemplate.getMapper(MessageDao.class).getDeviceIdsByAdminOpenIdAndOldName(open_id,older_name);
    }
}
