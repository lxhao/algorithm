import java.util.*;

public class VMStackOF {
    private int stackDepth = 1;
    private void stackLeak() {
        stackDepth++;
        stackLeak();
    }
   public static void main(String args[]) {
       VMStackOF vMStackOF = new VMStackOF();
       try {
           vMStackOF.stackLeak();
       } catch (StackOverflowError e) {
           System.out.println("stackDepth : " + vMStackOF.stackDepth);
           System.out.println("stack over flow error");
       }
   }
}


