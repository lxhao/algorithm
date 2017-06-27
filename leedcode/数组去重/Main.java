public class Main {
    public static void main(String args[]) {
        int[] A = {1, 3, 3, 3};
        int len = new Main().removeDuplicates(A);
        for (int i = 0; i < len; i++) {
            System.out.printf("%d ", A[i]);
        }
        System.out.printf("\n");
    }

    public int removeDuplicates(int[] A) {
        if (A.length <= 2) {
            return A.length;
        }
        int index = 2;
        for (int i = 2; i < A.length; i++) {
            if (A[i] != A[index - 2]) {
                A[index++] = A[i];
            }
        }
        return index;
    }
}
