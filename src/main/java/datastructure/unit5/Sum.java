package datastructure.unit5;

public class Sum {
    public static int sum(int[] arr) {
        return sum(arr, 0);
    }

    // 计算 arr[1...n]这个 区间内所有数字的和
    private static int sum(int[] arr, int l) {
        if (l == arr.length) {
            return 0;
        } else {
            return arr[l] + sum(arr, l + 1);
        }
    }

    public static void main(String[] args) {
        int[] nums={1,2,3,4,5};
        System.out.println(sum(nums));
    }
}

/*
  arr[0] + arr[1]+arr[2]+sum(arr,3)
 */
