package com.ictwsn.rob.beacon.tool;

import com.ictwsn.utils.tools.Tools;
import com.ictwsn.utils.speech.TextToSpeech;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Random;

public class BeaconTool {

    public static Logger logger = LoggerFactory.getLogger(BeaconTool.class);
    public static String audioFile;

    static {
        audioFile = Tools.getConfigureValue("audio.path");
    }


    public static String parsingBeaconXML(String settings_path, String scenario) {
        //音频文件路径
        String audioPath = "";
        try {
            Document document = read(settings_path);
            Element rootElement = getRootElement(document);

            //获取module节点
            for (Iterator module_element = rootElement.elementIterator("module"); module_element.hasNext(); ) {
                Element moduleElement = (Element) module_element.next();
                //判断module的属性为beacon
                for (Iterator module_attribute = moduleElement.attributeIterator(); module_attribute.hasNext(); ) {

                    Attribute moduleAttribute = (Attribute) module_attribute.next();
                    if (moduleAttribute.getName().equals("name") && moduleAttribute.getValue().equals("beacon")) {
                        //获取scenario节点
                        for (Iterator scenario_element = moduleElement.elementIterator("scenario"); scenario_element.hasNext(); ) {
                            Element scenarioElement = (Element) scenario_element.next();
                            for (Iterator scenario_attribute = scenarioElement.attributeIterator(); scenario_attribute.hasNext(); ) {
                                Attribute scenarioAttribute = (Attribute) scenario_attribute.next();
                                if (scenarioAttribute.getName().equals("name") && scenarioAttribute.getValue().equals(scenario)) {
                                    //获取随机音频数据
                                    String random = randomId(scenarioElement.elements().size());
                                    //获取message
                                    for (Iterator message_element = scenarioElement.elementIterator("message"); message_element.hasNext(); ) {
                                        Element messageElement = (Element) message_element.next();
                                        for (Iterator message_attribute = messageElement.attributeIterator(); message_attribute.hasNext(); ) {
                                            Attribute messageAttribute = (Attribute) message_attribute.next();
                                            if (messageAttribute.getName().equals("id") && messageAttribute.getValue().equals(random)) {
                                                //判断语音文件夹是否存在，若不存在，则创建
                                                Tools.createdDirectory(audioFile + "/" + moduleAttribute.getValue() + "/" + scenarioAttribute.getValue());
                                                //判断该音频文件是否存在
                                                audioPath = audioFile + "/" + moduleAttribute.getValue() + "/" + scenarioAttribute.getValue() + "/" + messageAttribute.getValue() + ".mp3";
                                                //音频文件不存在，语音合成并下载到本地
                                                if (!fileExists(new File(audioPath))) {
                                                    logger.info("音频文件:" + scenarioAttribute.getValue() + "-" + random + ".mp3不存在，正在合成语音...");
                                                    TextToSpeech.downloadAudio(audioPath, messageElement.getText());
                                                    logger.info("语音合成完毕");
                                                } else {
                                                    logger.info("音频文件已存在");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return audioPath;

    }

    // 从文件读取XML，输入文件名，返回XML文档
    public static Document read(String fileName) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(fileName));
        return document;
    }

    //取得根节点
    public static Element getRootElement(Document doc) {
        return doc.getRootElement();
    }

    //判断文件是否存在
    public static boolean fileExists(File file) {
        return file.exists();
    }

    //读取文件流
    public static InputStream readInputStram(String downLoadPath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(downLoadPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    //文件下载
    public static String downloadFile(HttpServletRequest request,
                                      HttpServletResponse response, String downLoadPath) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        /*
         * 远程下载路径（服务器的路径）
		 */
//        System.out.print("====" + request.getContextPath());

        try {
            File downloadFile = new File(downLoadPath);
            long fileLength = downloadFile.length();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(downloadFile.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            /*
			 * 可能出现内存太小问题
			 */
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("下载错误");
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }

    //读取文件音频流


    //生成随机id
    public static String randomId(int max) {
        int min = 1;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min + "";
    }

}
