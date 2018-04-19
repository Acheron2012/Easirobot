import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {


    public void print()
    {
        String settings_path = this.getClass().getClassLoader().getResource("").getPath();

        System.out.println(settings_path);
    }

    public static void main(String[] args) {

//        System.out.println("在播放一回".matches("(.?)*(刚才|刚刚)*没听清楚||(再|在)(播|放|来|说|播放)一(遍|次|回|下)"));
        String strTest = "htttp://adf/?=abc?中%1&2<3,4>";
        strTest = strTest.toString().replaceAll("\\s","%20");
        System.out.println(strTest);
        try {
            strTest = URLEncoder.encode(strTest, "UTF-8");
            System.out.println(strTest);
            strTest = URLDecoder.decode(strTest,"UTF-8");
            System.out.println(strTest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
