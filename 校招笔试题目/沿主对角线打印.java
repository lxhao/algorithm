/**
 * 沿主对角线方向打印
 */
public class 沿主对角线打印 {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        int[] res = new 沿主对角线打印().arrayPrint(arr, 4);
        for (int e : res) {
            System.out.print(e + " ");
        }
        System.out.println("");
    }

    public int[] arrayPrint(int[][] arr, int n) {
        // write code here
        int len = arr.length;
        int[] res = new int[len * len];
        int index = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                res[index++] = arr[j][len - i - 1 + j];
            }
        }

        for (int i = len - 2; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                res[index++] = arr[len - 1 - j][i - j];
            }
        }
        return res;
    }
}