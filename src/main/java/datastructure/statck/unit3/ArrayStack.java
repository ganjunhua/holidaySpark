package datastructure.statck.unit3;

import datastructure.unit2.ArrayTest2;

public class ArrayStack<E> implements Stack<E> {
    ArrayTest2<E> array;

    public ArrayStack(int capacity) {
        array = new ArrayTest2<E>(capacity);
    }

    public ArrayStack() {
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

    public void push(E e) {
        array.addLast(e);

    }

    public E pop() {
        return array.removelast();
    }

    public E peek() {
        return array.getLast();
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack:");
        res.append("[");
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1)
                res.append(",");
        }
        res.append("] top");
        return  res.toString();
    }
}
