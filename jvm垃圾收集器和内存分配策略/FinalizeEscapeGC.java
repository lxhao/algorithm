import java.util.*;

public class FinalizeEscapeGC {
    private static FinalizeEscapeGC SAVE_HOOK;

    public void isAlive() {
        System.out.println("yes, I am still alive.");
    }

    @Override
    protected void finalize() throws Throwable {
        long startTime = System.currentTimeMillis();
        super.finalize();
        System.out.println("finalize method exectued");
        SAVE_HOOK = this;
        //在finalize方法时执行耗时操作会别终止
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
        }
        //这段代码不会被执行,直接被kill了
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("it spent time %ld to execute finalize method.", endTime - startTime));
    }

    public static void main(String args[]) {
        FinalizeEscapeGC.SAVE_HOOK = new FinalizeEscapeGC();
        SAVE_HOOK = null;
        //第一次gc会调用finalize方法, 对象会自救
        System.gc();
        //finalize线程的优先级很低,睡眠500ms等待
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {

        }
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, I am dead :(");
        }

        SAVE_HOOK = null;
        //第二次gc不会调用finalize方法, 对象被直接回收
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {

        }
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, I am dead :(");
        }

    }
}
