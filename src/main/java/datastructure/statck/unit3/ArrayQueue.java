package datastructure.statck.unit3;

import datastructure.unit2.ArrayTest2;

public class ArrayQueue<E> implements Queue<E> {
    private ArrayTest2<E> array;

    public ArrayQueue(int capacity) {
        array = new ArrayTest2<E>(capacity);

    }

    public ArrayQueue() {
        array = new ArrayTest2<E>();
    }

    public int getSize() {
        return array.getSize();
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity() {
        return array.getCapasity();
    }


    public void enqueue(E e) {
        array.addLast(e);
    }

    public E dequeue() {
        return array.removeFirest();
    }

    public E getFront() {
        return array.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("queue:size = %d,capacity = %d\n", array.getSize(), array.getCapasity()));
        res.append("top [");
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1) {
                res.append(",");
            }
        }
        res.append("] tail");
        return res.toString();
    }
    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        System.out.println(9 % 3 );
        System.out.println(2 % 3 );
        System.out.println(3 % 3 );
        System.out.println(4 % 3 );
        System.out.println(7 % 3 );
        for (int i = 0; i<10; i++){
            queue.enqueue(i);
        System.out.println(queue);

            if (i%3 ==2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

}
