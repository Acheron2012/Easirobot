
public class Test {


    public void print()
    {
        String settings_path = this.getClass().getClassLoader().getResource("").getPath();

        System.out.println(settings_path);
    }

    public static void main(String[] args) {

    new Test().print();
    }

}
