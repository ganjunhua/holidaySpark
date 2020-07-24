package datastructure.LinkedList.unit4;

public class LinkedList<E> {
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }


    }

    private Node dummyhead; //  指向链表的第一个节点
    private int size; // 记录当前链表有多少个元素

    public LinkedList() {
        dummyhead = new Node(null, null);
        size = 0;
    }

    // 获取链表中的元素个数
    public int getSize() {
        return size;
    }

    // 返回链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 在链表头添加新的元素e
    public void addFirst(E e) {
        add(0, e);
    }

    // 在链表的index位置添加新的元素e
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("addaddaddadd");

        Node prev1 = dummyhead;
        for (int i = 0; i < index; i++) {
            prev1 = prev1.next;
        }
        Node node = new Node(e);
        node.next = prev1.next;
        prev1.next = node;
        size++;

    }

    //在链表未尾添加新的元素e
    public void addlast(E e) {
        add(size, e);
    }

    //获得链表的第index 个位置的元素
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("addaddaddadd");
        Node cur = dummyhead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    // 获得链表第一个元素
    public E getFirst() {
        return get(0);
    }

    // 获得链表的最后 一个元素
    public E getlast() {
        return get(size - 1);
    }

    //修改链表的第index个位置的元素为e
    public void set(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("set");
        Node cur = dummyhead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    //查找链表中是否有元素e
    public boolean contains(E e) {
        Node cur = dummyhead.next;
        while (cur != null) {
            if (cur.e.equals(e))
                return true;
            cur = cur.next;
        }
        return false;
    }

    public E remove(int index) {
        Node prev = dummyhead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;

        }
        Node retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;
        return retNode.e;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Node cur = dummyhead.next; cur != null; cur = cur.next)
            res.append(cur.e + "->");
        res.append("null");
        return res.toString();
    }
}

