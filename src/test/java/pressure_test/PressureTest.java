package pressure_test;

import com.ictwsn.utils.tools.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018-03-07.
 */
public class PressureTest {

    public static void RunTest() {
        Random random = new Random();

        String[] sRandom = new String[]{"你好", "hello", "今天北京天气如何", "来个娱乐新闻", "马云是谁", "周杰伦的国籍", "孩子怎么样", "泰山旅游",
                "天空为什么是蓝的", "鱼香肉丝菜谱", "一加十五再加上六等于多少", "帮我查查10月21号的黄历", "历史上的今天", "我要查鲁迅的格言", "放一首轻音乐", "来一段草船借箭"};
        String url = "http://119.23.238.240:8080/Easirobot/rob/voice?text=" + sRandom[random.nextInt(sRandom.length)] + "&deviceID=gh_655b593ac7b9_9897297a665e1d3b";
        HttpEntity httpEntity = HttpUtil.httpGet(url, null);

        if (httpEntity != null) {
            try {
                String line = EntityUtils.toString(httpEntity);
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 500; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        try {
                            Thread.sleep(index * 100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(index);
                        RunTest();
                    }

                }
            });
        }
    }
}
