package com.ictwsn.utils.recipe;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.tools.Mongodb;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


//爬取下厨房APP网，若本地资料库没有该菜谱内容，则添加至本地库
public class Xiachufang {

    public static Logger logger = LoggerFactory.getLogger(Xiachufang.class);

    public static String RECIPE_SEARCH_URL = "http://m.xiachufang.com/search/";

    public static String getRecipe(String text) {
        //获取一个mongodb链接
        MongoDatabase mongoDatabase = Mongodb.getMongoDatabase();
        MongoCollection<org.bson.Document> collection = mongoDatabase.getCollection("recipe");
        //先检查本地菜谱库中是否已存在
        String result = selectFromLocalRecipe(collection, text);
        //若存在则返回已有数据
        if (result != null) {
            return result;
        }
        //不存在则爬取后存入本地并返回数据
        //获取一个HTTP链接
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        //访问搜寻主页
        String searchUrl = RECIPE_SEARCH_URL + "?keyword=" + text;
        HttpRequest httpGet = new HttpGet(searchUrl);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute((HttpGet) httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String HTMLContent = EntityUtils.toString(entity, "UTF-8");
                String recipeUrl = getRecipeUrl(HTMLContent, searchUrl, text);
                //为空返回
                if (recipeUrl == null) return null;
                //分析菜谱页面并获得数据
                JSONObject jsonObject = analysisRecipe(recipeUrl);
                //存入并返回数据
                result = insertToLocalRecipe(collection, jsonObject);
                return result;
            } else {
                logger.info("请求错误，返回码:" + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String insertToLocalRecipe(MongoCollection<org.bson.Document> collection, JSONObject jsonObject) {
        org.bson.Document document = org.bson.Document.parse(jsonObject.toString());
        collection.insertOne(document);
        return jsonObject.getString("name") + "。用料：" + jsonObject.getJSONArray("ingredient").toString()
                + "做法：" + jsonObject.getJSONArray("steps").toString();
    }

    private static String selectFromLocalRecipe(MongoCollection<org.bson.Document> collection, String name) {
        FindIterable<org.bson.Document> findIterable = collection.find(new org.bson.Document("name", name));
        MongoCursor<org.bson.Document> mongoCursor = findIterable.iterator();
        if (mongoCursor.hasNext()) {
            JSONObject jsonObject = JSONObject.fromObject(mongoCursor.next().toJson());
            return jsonObject.getString("name") + "。用料：" + jsonObject.getJSONArray("ingredient").toString()
                    + "做法：" + jsonObject.getJSONArray("steps").toString();
        } else return null;
    }

    private static String getRecipeUrl(String HTMLContent, String url, String text) {
        Document document = Jsoup.parse(HTMLContent, url);
        Elements recipesElements = document.select("article");
        for (Element element : recipesElements) {
            if (text.equals(element.select("header").first().text())) {
                return element.select("a").first().attr("abs:href");
            }
        }
        return null;
    }

    private static JSONObject analysisRecipe(String url) {
        JSONObject recipeObject = new JSONObject();
        //获取菜谱内容
        String recipeContent = HttpUtil.getContent(url, "utf-8");
        Document document = Jsoup.parse(recipeContent, url);
        String title = document.select("title").first().text().replace("_下厨房", "");
        String name = document.select("div#name").first().text();
        String description = document.select("section#description").first().text();
        Elements ingredientElements = document.select("section#ings").first().select("li");
        JSONArray ingredientJsonArray = new JSONArray();
        for (Element element : ingredientElements) {
            ingredientJsonArray.add(element.text());
        }
        Elements stepElements = document.select("section#steps").first().select("li");
        JSONArray stepsJsonArray = new JSONArray();
        for (Element element : stepElements) {
            stepsJsonArray.add(element.text());
        }
        recipeObject.put("title", title);
        recipeObject.put("name", name);
        recipeObject.put("description", description);
        recipeObject.put("ingredient", ingredientJsonArray.toString());
        recipeObject.put("steps", stepsJsonArray.toString());
        recipeObject.put("dateTime", System.currentTimeMillis());
        return recipeObject;
    }

    public static void main(String[] args) {
        System.out.println(getRecipe("糖醋小龙虾"));
    }

}
