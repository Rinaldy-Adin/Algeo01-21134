package tubes.util;

public class Util {
    public static int indexOfVal(int[] arr, int val) {
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }

    public static int indexOfVal(float[] arr, float val) {
        for (int i = 0;i < arr.length;i++) {
            if (arr[i] == val)
                return i;
        }

        return -1;
    }
}
