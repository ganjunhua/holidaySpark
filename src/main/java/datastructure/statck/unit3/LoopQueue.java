package datastructure.statck.unit3;


public class LoopQueue<E> implements Queue<E> {
    private E[] data;
    private int front, tail;
    private int size;

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = 0; //  记录栈顶
        tail = 0;// 记录当前最后数据
        size = 0; // 记录当前共有多少个元素
    }

    public LoopQueue() {
        this(10);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    public boolean isEmpty() {
        return front == tail;
    }

    public int getSize() {
        return size;
    }

    public void enqueue(E e) {
        if ((tail + 1) % data.length == front)
            resize(getCapacity() * 2);
    }

    private void resize(int newCapacity) {

    }

    public E dequeue() {
        return null;
    }

    public E getFront() {
        return null;
    }
}
