package datastructure.unit2;

import java.util.ArrayList;

public class ArrayMain {
    public static void main(String[] args) {
        ArrayTest2<Integer> arr = new ArrayTest2<Integer>();
        for (int i = 0; i < 10; i++) {
            arr.addLast(i);
        }
        System.out.println(arr);
        arr.add(1,100);
        arr.add(2,100);
        arr.remove(1);
        arr.remove(2);
        arr.remove(0);
        arr.remove(3);
        arr.remove(4);
        arr.remove(5);
        System.out.println(arr);
        arr.remove(5);
        System.out.println(arr);
    }
}
