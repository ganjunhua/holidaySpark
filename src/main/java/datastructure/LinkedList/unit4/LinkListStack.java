package datastructure.LinkedList.unit4;

import datastructure.statck.unit3.Stack;

public class LinkListStack<E> implements Stack<E> {
    private LinkedList<E> list;

    public LinkListStack() {
        list = new LinkedList<E>();
    }

    public int getSize() {
        return list.getSize();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E e) {
        list.addFirst(e);
    }

    public E pop() {
        return list.removeFirst();
    }

    public E peek() {
        return list.getFirst();
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(" stack: top");
        res.append(list);
        return res.toString();
    }
}
