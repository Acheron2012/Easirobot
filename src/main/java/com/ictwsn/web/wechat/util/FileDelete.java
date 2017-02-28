package com.ictwsn.web.wechat.util;

import java.io.File;

public class FileDelete {
	public static  boolean deleteFile(String filepath){
		boolean flag = false;
		File file =  new File("filepath");
		if(file.isFile()&&file.exists()){
			file.delete();
			flag = true;
		}
		return flag;
	}
}
