import java.util.*;

public class Main {
    private static boolean haveNextNextGaussian = false;
    public static void main(String args[]) {
        int max = 95000;
        int min = 89000;
        int sd = 3000;

        double mu = 0.5;
        double  sigma = (max - min) / sd;

        List<Double> gauseNumgers = getGause(mu, sigma, max - min);
        List<Integer> testNumbers = getRandomNumber(gauseNumgers, min, max);

        // System.out.println(gauseNumgers);
        System.out.println("平均值：" + getMean(gauseNumgers));

    }

    /**
     * 生成随机数列
     */
    private static List<Integer> getRandomNumber(List<Double> gause,int min, int max ) {
        List<Integer> numbers = new ArrayList<>();
        int temp;
        for(Double e : gause) {
           temp = (int)(min + (max - min) * e);
           numbers.add(temp);
        }
        return numbers;
    }

    /**
     * 计算平均值
     */
    public static double getMean(List<Double> list) {
        double sum = 0;
        for(Double e : list) {
            sum += e;
        }
        return sum / list.size();
    }

    /**
     * 计算平均值
     */
    // public static double getMean(List<Integer> list) {
        // double sum = 0;
        // for(Integer e : list) {
            // sum += e;
        // }
        // return sum / list.size();
    // }


    /**
     * 正态分布
     */
    public static List<Double> getGause(double mean, double sd, int n) {
        List<Double> res = new ArrayList<>();
        double temp;
        double distance = 1 * 1.0 / n;
        for(double i = 1; i <=  n; i++ ) {
            temp = sd * Math.sqrt(-2 * Math.log(Math.random() * sd *
                Math.sqrt(2 * Math.PI)) / Math.E) + mean;
            res.add(temp);
        }
        return res;
    }
}

