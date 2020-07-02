package datastructure.unit2;

public class ArrayTest2<E> {
    private E[] data;
    private int size;

    //构造函数，传入数组的容量 capactiy
    public ArrayTest2(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
        System.out.println("ArrayTest2 capacity");
    }

    public ArrayTest2() {
        this(10);
        System.out.println("ArrayTest2");
    }

    // 获取元素的个数
    public int getSize() {
        return this.size;
    }

    // 获取 数组的容量
    public int getCapasity() {
        return data.length;
    }

    // 返回数组是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //向所有元数后添加 一个新元素
    public void addLast(E e) {
        if (size == data.length) {
            throw new IllegalArgumentException("addlast failed Array is full");
        }
        data[size] = e;
        size++;
    }

    //在第index个位置插入一个新元素e
    public void add(int index, E e) {

        if (index < 0 || index > size) {
            throw new IllegalArgumentException("xxx1");
        }
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    public void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public static void main(String[] args) {
        new ArrayTest2();
    }

    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("get");
        return data[index];
    }

    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    public E removeFirest() {
        return remove(0);
    }

    public E removelast() {
        return remove(size - 1);
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("xx");
        }
        E a = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        if (size == data.length / 2 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return a;
    }

    public E getFirst() {
        return get(0);
    }
    public E getLast(){
        return  get(size-1);
    }


    public void removeElement(int e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    public int find(int e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    void set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("set");
        data[index] = e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array:size = %d,capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(",");
            }
        }
        res.append(']');
        return res.toString();
    }

}
