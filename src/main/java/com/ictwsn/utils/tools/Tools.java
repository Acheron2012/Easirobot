package com.ictwsn.utils.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@SuppressWarnings("restriction")
public class Tools {

    private static Logger logger = LoggerFactory.getLogger(Tools.class);

    //MD5加密
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    //SHA加密
    public static String EncrypSHA(String info) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        sha.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = sha.digest();
        return resultBytes.toString();
    }

    //用于消除Mongodb中8个小时的时间误差
    public static Date changeToLocalChina(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.add(Calendar.HOUR, 8);
        datetime = calendar.getTime();
        return datetime;
    }

    //减去8个小时误差
    public static Date changeMongoToLocal(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.add(Calendar.HOUR, -8);
        datetime = calendar.getTime();
        return datetime;
    }

    //Date转为String
    public static String dateToString(Date datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(datetime);
    }

    //String转为Date
    public static Date stringToDate(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //数字时间转为汉字时间
    public static String stringToChineseTime(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时");
            return formatter.format(sdf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //数字日期转为汉字日期
    public static String stringToChineseDate(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            return formatter.format(sdf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //把字符串转化为东8区世家
    public static Date changeToDate8(String timestamp) {
        if (timestamp == null || timestamp.equals("") || timestamp.equals("null")) return null;
        timestamp = timestamp.replace("T", " ");//将里面的T替换成空格
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(timestamp);
            date = changeToLocalChina(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //得到当前的东8区时间
    public static Date getNowDate8() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return changeToLocalChina(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //得到当天的日期
    public static Date getNowDay7() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(sdf.format(new Date())));
            calendar.add(Calendar.HOUR, 7);
            Date datetime = calendar.getTime();
            return datetime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 得到15天以前的日期
     负数为前多少天，正数为后多少天*/
    public static Date getDayByNumber(Date date, int number) {
        //剔除掉时间，保留日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        Date datetime = calendar.getTime();
        return datetime;
    }

    //得到一天以后的时间
    public static Date getDayAfter1Day() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    //仅获得日期
    public static String getOnlyDayByDate(Date date) {
        //剔除掉时间，保留日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    //Properties操作
    public static String getConfigureValue(String key) {
        Properties property = new Properties();
        InputStream in = Tools.class.getResourceAsStream("/system.properties");
        if (in == null) {
            logger.info("未读取到配置文件");
        }
        try {
            property.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property.getProperty(key);
    }

    //创建文件夹
    public static void createdDirectory(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
            logger.info("{}不存在，已创建", path);
        }
    }


    //返回下载文件流
    public static long downloadAudioFile(InputStream inputStream, HttpServletResponse response) {
        response.setContentType("application/x-msdownload");
//		统计当前流量
        long flow = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(inputStream);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                flow += i;
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//		返回本次下载流量
        return flow;
    }

    // 将汉字转换为全拼
    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            // System.out.println(t4);
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    //写入文件到客户端
    public static void writeToClient(HttpServletResponse response, String filePath) {
        response.setContentType("application/x-msdownload");
        FileInputStream fileInputStream = null;
        try {
            OutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            try {
                fileInputStream = new FileInputStream(filePath);
                int len;
                while ((len = fileInputStream.read(b)) != -1) {
                    outputStream.write(b, 0, len);
                }
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }


    public static String getDateSx() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "早上好";
        } else if (hour >= 8 && hour < 11) {
            return "上午好";
        } else if (hour >= 11 && hour < 13) {
            return "中午好";
        } else if (hour >= 13 && hour < 18) {
            return "下午好";
        } else {
            return "晚上好";
        }
    }

    // 返回JSON内容
    public static void responseToJSON(HttpServletResponse response, String jsonContent) {
        //Convert to JSON format
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write(jsonContent);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //以90%高概率获得1，低概率获得0
    public static int getNumberByHighProbability() {
        Random random = new Random();
        int number = random.nextInt(100);
        if (number < 90) {
            return 1;
        } else {
            return 0;
        }
    }


    public static void main(String[] args) {

//        Date date = getDayAfter1Day();
//        System.out.println(getOnlyDayByDate(date));
//        System.out.println(getNowDayBefore15Days(date));
        System.out.println(getNumberByHighProbability());
        /*try {
            System.out.println(EncrypSHA("easicloudjljajdfio324jll38hl3"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}*/


//		System.out.println(getNowDate8());
    }

}
