package com.ictwsn.utils.turing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.PatternSyntaxException;

public class TuringUtils {

    public static Logger logger = LoggerFactory.getLogger(TuringUtils.class);

    public static String filterSpeech(String context)
    {
        String text = context.replaceAll("°","度").replaceAll(":","，").replaceAll(";","").replaceAll("-","至").replaceAll("\\s*", "")
                .replaceAll("“|”","").replaceAll("：","").replaceAll("　","").replaceAll("？","").replaceAll("\n","").replaceAll("！","。");
        return text;
    }

    //提取中文
    public static String StringFilter(String str) throws PatternSyntaxException {
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, "");
        return str;
    }

    public static void main(String[] args)
    {

        String t = "[\\\"讲个故事，\\\"]";
        System.out.println(StringFilter(t));
    }


}
