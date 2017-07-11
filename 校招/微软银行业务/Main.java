
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        int arrive_time[] = new int[]{10, 12, 15, 17, 18, 19, 19, 20, 25};
        int process_time[] = new int[]{1, 18, 10, 19, 16, 8, 6, 7, 3};
        System.out.println(solution());
        System.out.println(solution(arrive_time, process_time));
    }

    /**
     * o(n)的解决方法，经测试和标准答案的结果是一样的
     * 模拟银行业务场景：
     * 用一个优先队列保存四个元素，四个元素为正在进行的任务执行完成的时间
     * (有限队列会按照任务结束的时间排序, 从队列取到的第一个任务总是最先完成的那个)
     * 用一个变量保存总的等待时间
     *
     * 先往优先队列放入4个任务，这４个任务的等待时间为０，所以优先队列的４个值为4个任务对应的到达时间和执行时间之和，
     * 再把剩下的任务一个个加入优先队列，对于这个要加入的任务，他的等待时间为优先队列的第一个元素（四个任务中最先完成的任务）
     * 的值减去当前任务的开始时间，所以这个要加入的任务的完成时间为“他的等待时间 + 任务到达时间 +　任务执行时间”
     * 依次遍历完所有的任务，得到最终等待时间
     * @param arrive_time
     * @param process_time
     * @return
     */
    private static double solution(int[] arrive_time, int[] process_time) {
        PriorityQueue<Integer> processing = new PriorityQueue<>();
        for (int i = 0; i < 4; i++) {
            processing.add(arrive_time[i] + process_time[i]);
        }

        int totalWaitTime = 0;
        int waitTime;
        for (int i = 4; i < arrive_time.length; i++) {
            waitTime = processing.peek() - arrive_time[i];
            waitTime = Math.max(waitTime, 0);
            totalWaitTime += waitTime;
            processing.poll();
            processing.add(arrive_time[i] + process_time[i] + waitTime);
        }
        return totalWaitTime * 1.0 / arrive_time.length;
    }

    private static double solution() {
        int arrive_time[] = new int[]{10, 12, 15, 17, 18, 19, 19, 20, 25};
        int process_time[] = new int[]{1, 18, 10, 19, 16, 8, 6, 7, 3};
        int total = 0, lastTime = 0;
        int atms[] = new int[]{0, 0, 0, 0};
        for (int i = 0; i < process_time.length; i++) {
            int time = arrive_time[i] - lastTime;
            for (int j = 0; j < atms.length; j++) {
                atms[j] -= time;
            }
            lastTime = arrive_time[i];
            boolean wait = true;
            for (int j = 0; j < atms.length; j++) {
                if (atms[j] <= 0) {
                    atms[j] = process_time[i];
                    wait = false;
                    break;
                }
            }
            if (wait) {
                int temp = atms[0];
                for (int j = 0; j < atms.length; j++) {
                    for (int j2 = j + 1; j2 < atms.length; j2++) {
                        if (atms[j2] < atms[j]) {
                            temp = atms[j2];
                            atms[j2] = atms[j];
                            atms[j] = temp;
                        }
                    }
                }
                total += atms[0];
                atms[0] += process_time[i];
            }
        }
        return (total * 1.0 / arrive_time.length);
    }
}
