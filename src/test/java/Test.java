import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {


    public void print()
    {
        String settings_path = this.getClass().getClassLoader().getResource("").getPath();

        System.out.println(settings_path);
    }

    public static void main(String[] args) {

        System.out.println(new Date());

        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = sdf.format(new Date(Long.parseLong(String.valueOf("1489642886642"))));
        System.out.println(d);
//        new Test().print();
    }

}
