package mapreduce;

import java.lang.reflect.Array;
import java.util.HashMap;

public class test {
    public static void main(String[] args) {


        int[] arr = {2, 71, 11, 7};
        int target = 9;
        int[] a = towSum(arr, target);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

    }

    public static int[] towSum(int[] nums, int targer) {
        int a = 0;


        HashMap<Integer, Integer> h2 = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {

            a = targer - nums[i];

            if (h2.containsKey(a)) {
                int currentValue = i;
                Long lastValue = h2.get(a).longValue();
                return new int[]{currentValue, Integer.parseInt(String.valueOf(lastValue))};
            }
            h2.put(nums[i], i);

        }
        return new int[]{-1, -1};

    }

    public static int subtractProductAndSum(int n) {
        int add = 0, mul = 1;
        while (n > 0) {
            int digit = n % 10;
            n /= 10;
            add += digit;
            mul *= digit;
        }
        return mul - add;
    }

}
