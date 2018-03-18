

class Hello {

    public static int binarySearch(int[] a, int fromIndex, int toIndex, int value) {
        int low = fromIndex;
        int high = toIndex;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midVal = a[mid];
            if (midVal < value) {
                low = mid + 1;
            } else if (midVal > value) {
                high = mid - 1;
            } else  {
                return mid;
            }
        }
        return -1;
    }
}