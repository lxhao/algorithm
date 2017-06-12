import java.util.*;

public class Main {
    public static void main(String args[]) {
        //任务数量
        int taskAmount = 2;
        //每个任务运行时占用空间
        int[] runningSpace = {13, 8};
        //每个任务的结果保存占用空间
        int[] outcomeSpace = {6, 6};
        //最大运行空间
        int sumSpace =  14;
        System.out.println(isRunnable(runningSpace, outcomeSpace, sumSpace));
    }

    public static boolean isRunnable(int[] runningSpace, int[] outcomeSpace, int sumSpace) {
        if (runningSpace == null || runningSpace.length == 0) {
            return false;
        }
        if (outcomeSpace == null || outcomeSpace.length == 0) {
            return false;
        }
        if (outcomeSpace.length != runningSpace.length) {
            return false;
        }
        ArrayList<Task> tasks = new ArrayList<>(runningSpace.length);
        for (int i = 0; i < runningSpace.length; i++) {
            Task task = new Task(runningSpace[i], outcomeSpace[i]);
            tasks.add(task);
        }
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return ((t1.runningSpace - t1.outcomeSpace) - (t2.runningSpace - t2.outcomeSpace));
            }
        });
        Collections.reverse(tasks);

        int occupation = 0;
        for (Task task : tasks) {
            if (occupation + task.runningSpace > sumSpace ) {
                return false;
            }
            occupation += task.outcomeSpace;
        }
        return true;
    }
}

class Task {
    Task(int runningSpace, int outcomeSpace) {
        this.runningSpace = runningSpace;
        this.outcomeSpace = outcomeSpace;
    }
    int runningSpace;
    int outcomeSpace;
}

