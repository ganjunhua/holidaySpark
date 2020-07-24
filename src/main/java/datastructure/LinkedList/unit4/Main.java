package datastructure.LinkedList.unit4;

public class Main {
    public static void main(String[] args) {
 /*       LinkedList<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            l.add(i,i);
            System.out.println(l);
        }
        l.add(2, 777);
        System.out.println(l);
        l.remove(2);
        System.out.println(l);*/
   /*     LinkListStack<Integer> l = new LinkListStack<Integer>();
        for (int i = 0; i < 5; i++) {
            l.push(i);
            System.out.println(l);
        }
        l.pop();
        System.out.println(l);
        l.pop();
        System.out.println(l);*/
        LinkedListQueue<Integer> queue = new LinkedListQueue<Integer>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            System.out.println(queue);

        }
        System.out.println(queue.dequeue());
        System.out.println(queue);

    }
}
