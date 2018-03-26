public class Test {


    public void print()
    {
        String settings_path = this.getClass().getClassLoader().getResource("").getPath();

        System.out.println(settings_path);
    }

    public static void main(String[] args) {

        System.out.println("在播放一回".matches("(.?)*(刚才|刚刚)*没听清楚||(再|在)(播|放|来|说|播放)一(遍|次|回|下)"));
    }

}
