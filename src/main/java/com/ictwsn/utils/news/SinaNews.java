package com.ictwsn.utils.news;

import com.ictwsn.utils.tools.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017-04-10.
 */
public class SinaNews {

    public static Logger logger = LoggerFactory.getLogger(SinaNews.class);
    public static String URL = "http://news.sina.com.cn/";

    public static String getCurrentNews() {
        String HTMLContent = HttpUtil.getContent(URL, "utf-8");
        List<String> list = new ArrayList<String>();
        Document document = Jsoup.parse(HTMLContent, URL);
        Element mainElement = document.select("div#syncad_1")
                .first();
        Elements newsElements = mainElement.select("a");
        for (Element ne : newsElements) {
            list.add(ne.attr("abs:href"));
        }
        Random random = new Random();
        int number = random.nextInt(list.size());
        String newsContent = HttpUtil.getContent(list.get(number), "utf-8");
        return analysisNews(newsContent);
    }

    private static String analysisNews(String newsContent) {
        Document document = Jsoup.parse(newsContent);
        Element articleContentElement = document.select("div#artibody").first();
        articleContentElement.select("div[class=img_wrapper]").remove();
        try {
            return articleContentElement.text().replace("　","").replace(" ","");
        } catch (Exception e) {
            return null;
        }
    }

   /* public static void main(String[] args) {
        SinaNews sinaNews = new SinaNews();
        String HTMLContent = HttpUtil.getContent(URL, "utf-8");
        System.out.println(sinaNews.getCurrentNews(HTMLContent));
    }*/

}
