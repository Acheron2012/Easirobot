/**
 * Created by Administrator on 2018-04-19.
 */
class ThreadExample extends Thread {
    private Test mv;

    public ThreadExample(Test mv) {
        this.mv = mv;
    }

    public void run() {
        synchronized (mv) {
            mv.m();
        }
    }
}
