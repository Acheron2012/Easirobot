package com.ictwsn.utils.speech;

import com.ictwsn.utils.tools.HttpUtil;
import com.ictwsn.utils.turing.TuringAPI;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;


import java.io.*;

/**
 * 语音合成
 */

public class TextToSpeech {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(TextToSpeech.class);


    public static void main(String[] args) {
//        String body = Speech.getToken();
//        JSONObject jsonObject = JSONObject.fromObject(body);
//        Speech.TOKEN = jsonObject.getString("access_token");
//        System.out.println(Speech.TOKEN);

        String tex = "原始山林茂密，林中阴气很重，毒虫出没，兽吼沉闷如雷，有凶彪、蛇虺等留下的阵阵腥气扑鼻而来，令人皮骨发寒。\n" +
                "　　\n" +
                "　　“嗷吼……”\n" +
                "　　\n" +
                "　　山脉深处传来雷鸣般的吼声，令山石滚落，回音隆隆，林木剧烈摇摆，乱叶狂飞，远处有大型凶兽出没。\n" +
                "　　\n" +
                "　　一群孩子脸色发白，他们离开石村已经有段距离了，是瞒着大人们出来的，进入了老林子中，还好不曾进入凶兽真正的栖居地。\n" +
                "　　\n" +
                "　　“大壮哥，山林太危险了，我们年龄还小，不能再前进了。”一个孩子颤声道。\n" +
                "　　\n" +
                "　　他们守着原始山林长大，自然知道其中的危险，有各种凶物，连他们的父辈进入山林都需要谨慎小心，否则会丧命。\n" +
                "　　\n" +
                "　　这群孩子年龄都不大，共有十几人，为首的正是曾经举起过千斤铜鼎的石大壮，浓眉大眼，手脚粗大，人如其名，身高都快追上成年人了，他看向另一人，道：“皮猴，还有多远？”\n" +
                "　　\n" +
                "　　皮猴名为石中侯，长的干巴巴，但力气极大，也很机灵，道：“我听林虎叔他们说，那山崖离村子不是很远，就是这个方向，应该快到了。”\n" +
                "　　\n" +
                "　　“石昊你有什么意见？”石大壮问道。\n" +
                "　　\n" +
                "　　过去，石昊只是一群大孩子后面的跟屁虫，自从他举起铜鼎后，就连大人们都已视他为小怪物，就更不要说孩子们了，一下子成为了他们中的“骨干分子”之一。\n" +
                "　　\n" +
                "　　“再走下去会很危险。”小石昊声音清脆，黑白分明的大眼乌溜溜，如实说道。\n" +
                "　　\n" +
                "　　“可是离那里真的不远了。”石大壮道。\n" +
                "　　\n" +
                "　　半数以上的孩子意动，想继续走下去。\n" +
                "　　\n" +
                "　　“如果你们要去，我也跟着。”小石昊稚声稚气的说道。\n" +
                "　　\n" +
                "　　就这样一群孩子又上路了，又走出去一里多远，大树渐稀，植被越来越少，巨石逐渐多了起来，且有阵阵凶气弥漫。\n" +
                "　　\n" +
                "　　山石嶙峋，这是一片很大的石林，寂静无声，地上散落着一些巨兽的遗骨，雪白而惊人。\n" +
                "　　\n" +
                "　　皮猴四顾，小声道：“就是这里，我听林虎叔他们说，它的巢穴筑在石林深处的崖壁上。”\n" +
                "　　\n" +
                "　　石大壮也压低声音，提醒所有孩子，道：“这些兽骨可能是它吃猛兽时留下的，虽然说这个时间段它应该不在巢内，但我们还是得小心点，千万别被发现，不然可能会没命！”\n" +
                "　　\n" +
                "　　十几个孩子都是在大荒中长大的，警觉性非常高，跟小山兽似的，迅疾而又敏捷的躲进石林的缝隙间，掩护己身。稍微观察片刻，又迎着风嗅了嗅气味，而后相互点了点头，他们如同猿猴般，矫健的冲向石林最深处。\n" +
                "　　\n" +
                "　　这一路上他们见到了很多骸骨，雪白而巨大，有五六米长的禽骨，更有磨盘大的兽头骨，都是山林中的猛兽与凶禽被生生撕食后所致，此地死气沉沉。\n" +
                "　　\n" +
                "　　“它果然要在这里栖居下去，时间长了，若是繁衍出一些后代，我们石村的人进出山脉时将会受到致命的威胁！”\n" +
                "　　\n" +
                "　　“林虎叔他们商量几天了，早就观察好了它的习性。”\n" +
                "　　\n" +
                "　　这些孩子一边低语一边疾驰，速度极快，如十几道小旋风般冲进了石林深处。\n" +
                "　　\n" +
                "　　一座石崖横亘前方，这里更加寂静了，寸草不生，在崖壁最上方有一个巨大的巢，以一根根黑梧木筑成，给人很压抑的感觉。\n" +
                "　　\n" +
                "　　孩子们隔着很远，躲在山石缝间小心地观看，黑色的巢直径足有十米长，很巨大，不用想就知道是异种凶禽的巢穴。\n" +
                "　　\n" +
                "　　“果真在这里！”\n" +
                "　　\n" +
                "　　“这头青鳞鹰在此徘徊良久了，现在筑出了巨巢，难道真如林虎叔所说的已经产卵了？”\n" +
                "　　\n" +
                "　　一群孩子皆双眼放光，这是他们来此的最主要目的！\n" +
                "　　\n" +
                "　　这是一种很凶悍与强大的异禽，体内有传承自太古魔禽的血液，很难对付，一般的猛兽与凶物被它盯上都得死，难逃活命。\n" +
                "　　\n" +
                "　　“据林虎叔他们观察，那只雄性的青鳞鹰数日来都不曾现身，可能在山脉深处发生意外死掉了，每日午时那只雌鸟都会自己出去捕杀食物，想要接近，机会就在眼前。”皮猴说道。\n" +
                "　　\n" +
                "　　一群孩子握紧了拳头，显然很紧张，同时眼中也有一种期待与兴奋，在山林中长大的孩子个个都很胆大，不然也不会自作主张地跑到这种危险的地方来。\n" +
                "　　\n" +
                "　　“大家藏进石缝中，我投块石头试试看！”一个皮肤黝黑的孩子开口，他名为石猛，村人都叫他二猛。在石村演武时，他曾直接撂倒一头大莽牛，更是差点举起千斤重的青铜鼎，在这群孩子中仅次于石昊与石大壮。\n" +
                "　　\n" +
                "　　“呼”的一声，一块大石飞起，冲向远处，最后咚的一声落在了石崖前的乱石堆上，发出一声巨响。\n" +
                "　　\n" +
                "　　众人都吓了一大跳，还好山崖上没有什么动静。\n" +
                "　　\n" +
                "　　“二猛别这么鲁莽，小心谨慎一点。”\n" +
                "　　\n" +
                "　　“我试试它在不在巢中，现在看来没事，我们赶紧上！”二猛说道，就要冲过去。\n" +
                "　　\n" +
                "　　“二猛哥先等一等。”小石昊开口，抓起一块不小的石头，用力掷出，石块嗖的一声飞上石崖，落在梧巢近前，发出一声大响。\n" +
                "　　\n" +
                "　　过了片刻，山崖上很安静，青鳞鹰并没有出现。\n" +
                "　　\n" +
                "　　“走！”\n" +
                "　　\n" +
                "　　一群孩子如兽群般，嗷嗷叫着，飞快冲向石崖。到了近前后他们分工有序，一部分人站在巨石上，注视天空，站岗瞭望，以防那只凶禽突然出现，另有几个人则准备攀上石崖。\n" +
                "　　\n" +
                "　　“大壮哥你们都等着，我先上去看一看。”石昊说道。\n" +
                "　　\n" +
                "　　“你一个还没断的娃，在边上看着就行了，我们上。”石大壮道，一群孩子都笑了起来，小不点到现在还在吃兽奶，常被他们取笑。\n" +
                "　　\n" +
                "　　“我早就吃肉了，只是偶尔拿它当水喝！”小不点气呼呼，皱着鼻子，瞪着黑宝石般的大眼进行辩解。\n" +
                "　　\n" +
                "　　当然，小家伙很聪明，知道大孩子们此时并不是真的在笑话他，而是在照顾与保护他，不想他第一个上去而冒险。\n" +
                "　　\n" +
                "　　“我比你们速度都快，有危险也逃的快。”小不点不等他们开口，像是一只小猴子般，嗖的一声，飞快攀爬向石崖，矫捷而灵敏。\n" +
                "　　\n" +
                "　　“别让他犯险，我们也上！”石大壮与二猛还有皮猴全都紧随在后，同样如同猿猴般追了上去。\n" +
                "　　\n" +
                "　　崖壁上有不少缝隙，令几个孩子得以借力，快速向上攀。生长在大山中，守着原始密林，他们攀跃的本领自然很强，比起山脉中的恶魔猿都不会逊色多少。\n" +
                "　　\n" +
                "　　“呼……终于上来了！”\n" +
                "　　\n" +
                "　　石崖能有三百米高，小不点上来后，等待另外三人片刻，直到他们都冒出头来才一起向那巨巢走去。\n" +
                "　　\n" +
                "　　“好大的鸟窝啊！”皮猴惊叹。\n" +
                "　　\n" +
                "　　站在近前观看，格外有震撼感，巢穴足有十米长，以黑色的梧木筑成，占据了大半的崖顶，比石村的房屋都巨大。\n" +
                "　　\n" +
                "　　舍此之外，崖上还有一些粘着血丝的大骨头，每一根都比成年人还粗长，这令人毛骨悚然。\n" +
                "　　\n" +
                "　　尤其是那磨盘大的兽骨头上，还有几个可怖的爪洞，残留着血迹，显得非常狰狞。\n" +
                "　　\n" +
                "　　“这是龙角象的骸骨，真是可怕，一头凶禽居然动辄就要吃巨象！”二猛惊憾。\n" +
                "　　\n" +
                "　　“先别管这些了。”石大壮道，向着那黑色的鸟巢上攀去。\n" +
                "　　\n" +
                "　　来到巢上，顿时感觉到阵阵森然气息，且有血腥味。巢穴边缘呈暗红色，显然青鳞鹰常在巢穴的边沿进食，经过各种兽血的长期浸染，这个地方煞气很浓。\n" +
                "　　\n" +
                "　　“那头凶鸟不在！”\n" +
                "　　\n" +
                "　　“快看，有好几枚禽蛋！”\n" +
                "　　\n" +
                "　　几个孩子惊呼，他们胆大包天，在村中偷听到大人的谈话后，自作主张，就是冲着凶禽的卵而来的。\n" +
                "　　\n" +
                "　　“太好了，我们赶紧搬走，带回村子去孵化，日后将会有强大的凶禽为我们捕杀猛兽，带回猎物！”皮猴兴奋的直叫。\n" +
                "　　\n" +
                "　　梧巢内铺着柔软的金丝草，看起来很舒适，三枚晶莹如碧玉般的蛋静静在呈现在那里，上面带着一些纹络与斑点，光泽闪动。\n" +
                "　　\n" +
                "　　这种凶禽产下的蛋每一个都足有水盆大，剔透闪亮，碧绿如玛瑙，而上面的斑纹在日光下则熠熠生辉。手机用户请浏览w阅读，更优质的阅读体验。";
        InputStream inputStream = returnCombineSpeechInputStream(tex);
        if (inputStream == null) System.out.println("我是空");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\Audio_new.mp3");
            byte[] b = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            tex = java.net.URLEncoder.encode(tex, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        try {
//            System.out.println(tex.getBytes("UTF-8").length);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        //拼接请求的语音地址
//        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + tex + "&lan=zh&cuid=" + Speech.CUID +
//                "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
//        String downloadPath = "C:\\Users\\Administrator\\Desktop\\" + Speech.AUDIOFILENAME + ".mp3";
//        try {
//            getFile(voiceURL, downloadPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    //调用语音合成
    public static boolean textToSpeech(String context) {

        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + context + "&lan=zh&cuid=" + Speech.CUID +
                "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        String downloadPath = "C:\\Users\\Administrator\\Desktop\\" + Speech.AUDIOFILENAME + ".mp3";
        try {
            getFile(voiceURL, downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean downloadAudio(String audioPath, String audioText) {
        String body = Speech.getToken();
        JSONObject jsonObject = JSONObject.fromObject(body);
        Speech.TOKEN = jsonObject.getString("access_token");
        try {
            //URL编码
            audioText = java.net.URLEncoder.encode(audioText, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接请求的语音地址
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + audioText + "&lan=zh&cuid=" +
                Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        try {
            getFile(voiceURL, audioPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //下载语音合成文件
    private static void getFile(String url, String destFileName)
            throws ClientProtocolException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet(url);

        HttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();

        InputStream in = entity.getContent();

        File file = new File(destFileName);

        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
            }
            fout.flush();
            fout.close();
        } finally {
            in.close();
        }
        httpclient.close();
    }

    //当长度大于1024字节时（一个中文占两个字节），创建序列流来合并两个流
    public static InputStream returnCombineSpeechInputStream(String context) {
        //获取TOKEN
        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        int count = (context.length() / 512) + 1;
        logger.info("合并长度为：" + count);
        InputStream inputStream = null;
        String tempContent;
        int tempLength = 0;
        for (int i = 0; i < count; i++) {
            logger.info("已进入第{}次合并",i+1);
            if (i == count - 1) {
                tempContent = context.substring(tempLength, context.length());
                tempLength = context.length();
            } else {
                tempContent = context.substring(tempLength, tempLength + 512);
                tempLength += 512;
            }
            try {
                //URL编码
                tempContent = java.net.URLEncoder.encode(tempContent, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //拼接请求字符串
            String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + tempContent + "&lan=zh&cuid=" +
                    Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
            //发送请求返回结果
            HttpEntity entity = HttpUtil.httpGet(voiceURL, null);
            if (entity != null) {
                try {
                    InputStream tempInputSream = entity.getContent();
                    if (inputStream == null) {
                        inputStream = tempInputSream;
                    } else {
                        //创建一个序列流，合并两个字节流
                        inputStream = new SequenceInputStream(inputStream, tempInputSream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info("合成语音entity为空");
                return null;
            }
        }
        return inputStream;
    }


    //返回语音流文件
    public static InputStream returnSpeechInputStream(String context) {
        //获取TOKEN
        Speech.TOKEN = JSONObject.fromObject(Speech.getToken()).getString("access_token");
        try {
            //URL编码
            context = java.net.URLEncoder.encode(context, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接请求字符串
        String voiceURL = "http://tsn.baidu.com/text2audio?tex=" + context + "&lan=zh&cuid=" +
                Speech.CUID + "&ctp=1&tok=" + Speech.TOKEN + "&spd=" + Speech.SPD + "&per=" + Speech.PER;
        HttpEntity entity = HttpUtil.httpGet(voiceURL, null);
        if (entity != null) {
            try {
                InputStream inputStream = entity.getContent();
                return inputStream;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("合成语音entity为空");
        }
        return null;
    }


}
