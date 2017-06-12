import java.util.*;

public class Main {
    public static void main(String args[]) {
        List<Integer> numbers = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int n;
        while (in.hasNext()) {
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                numbers.add(in.nextInt());
            }
            System.out.println(getRes(numbers));
            numbers.clear();
        }
    }

    public static int getRes(List<Integer> numbers) {
        Collections.sort(numbers);
        int sum = 0;
        int start, end;
        int startPos, endPos;
        int k;
        for (int i = 0; i < numbers.size(); i++) {
            k = 1;
            while (true) {
                start = (int) Math.pow(numbers.get(i), k);
                end = (int) Math.pow(numbers.get(i), k + 1);

                if(start == 1) {
                    sum += numbers.size() - i;
                    break;
                }

                if (start > numbers.get(numbers.size() - 1)) {
                    break;
                }

                startPos = findGreaterOrEquanlPos(numbers, start);
                endPos = findLessPos(numbers, end);

                sum += (endPos - startPos + 1) * k;
                k++;
            }
        }
        return sum;
    }

    //找到第一个大于等于x的位置
    public static int findGreaterOrEquanlPos(List<Integer> numbers, int x) {
        int low = 0;
        int high = numbers.size() - 1;
        int mid;
        while (low <= high) {
            mid = (high + low) >>> 1;
            if (numbers.get(mid) >= x) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    //找到第一个小于x的位置
    public static int findLessPos(List<Integer> numbers, int x) {
        int high = numbers.size() - 1;
        int low = 0;
        int mid;
        while (low <= high) {
            mid = (high + low) >>> 1;
            if (numbers.get(mid) >= x) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }
}
