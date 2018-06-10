import java.util.*;

/**
 * java VMStackOOM -Xss 2M
 */
public class VMStackOOM {
    private void keepRunning() {
        while(true) {
        }
    }

    private void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    keepRunning();
                }
                
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        VMStackOOM oom = new VMStackOOM();
        oom.stackLeakByThread();
    }
}

