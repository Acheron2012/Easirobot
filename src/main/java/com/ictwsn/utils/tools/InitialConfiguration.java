package com.ictwsn.utils.tools;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Iterator;

public class InitialConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	// ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用
	
	public static Logger logger = LoggerFactory.getLogger(InitialConfiguration.class);
	
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		//获取目录创建语音池
		logger.info("初始化语音文件夹===");
		String rootPath = Tools.getConfigureValue("audio.path");
		//获取XML中的配置信息
		String settings_path = InitialConfiguration.class.getClassLoader().getResource("").getPath()+"/"+"settings.xml";
		Document document = null;
		try {
			document = read(settings_path);
			Element rootElement = getRootElement(document);
			//获取module节点
			for (Iterator module_element = rootElement.elementIterator("module"); module_element.hasNext(); ) {
				Element moduleElement = (Element) module_element.next();
				String path = rootPath+"/"+moduleElement.attribute("name").getValue();
				Tools.createdDirectory(path);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		logger.info("语音文件夹创建完毕！");

		logger.info("spring容器初始化完毕================================================");
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



}
