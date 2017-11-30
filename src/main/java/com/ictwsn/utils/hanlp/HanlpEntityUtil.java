package com.ictwsn.utils.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2017-11-27.
 */
public class HanlpEntityUtil {

    public static Logger logger = LoggerFactory.getLogger(HanlpEntityUtil.class);


    public static boolean keywordMatching(String text, String[] keywords) {
        for(String k: keywords)
            if(text.contains(k)) return true;
            else return false;
        return false;
    }

    public static String wordClassMatching(String word, String partOfSpeech) {
        //Splite word and wordClass
        String[] wordClass = word.split("/");
        if(wordClass.length>1)
            if(wordClass[1].equals(partOfSpeech)) return wordClass[0];
        return null;
    }

    public static void main(String[] args) {
        String[] testCase = new String[]{
                "武胜县新学乡政府大楼门前锣鼓喧天",
                "蓝翔给宁夏固原市彭阳县红河镇黑牛沟村捐赠了挖掘机",
        };
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        for (String sentence : testCase)
        {
            List<Term> termList = segment.seg(sentence);
            System.out.println(termList);
        }
    }


}
