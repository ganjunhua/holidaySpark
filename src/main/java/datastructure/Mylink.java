package datastructure;

public class Mylink {

    Node head = null; // 头节点

    class Node {
        Node next = null; // 节点的引用， 指向下一个节点
        int data; //  数据域

        public Node(int data) {
            this.data = data;
        }
    }

    public void addNode(int d) {
        Node newNode = new Node(d);
        if (head == null) {
            head = newNode;
            return;
        }
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = newNode;
    }

    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }

    public void remove(Integer i) {
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        Node tmp = head;
        Node tm1;
        int n = 1;
        // 找到要删除的上一个节点
        while (tmp.next != null && n < i - 1) {
            tmp = tmp.next;
            n++;
        }
        // 保存要删除的节点
        tm1 = tmp.next;
        tmp.next = tm1.next;
        tm1 = null;
        System.out.println(tmp.data);

    }

    public void searchList(Integer n) {
        Node tmp = head;
        int i = 0;
        while (tmp.next != null && i < n - 1) {
            tmp = tmp.next;
            i++;
        }
        System.out.println(tmp.data);

    }

    public static void main(String[] args) {
        Mylink list = new Mylink();
        list.addNode(1);
        list.addNode(2);
        list.addNode(3);
        list.addNode(4);
        list.remove(2);
        //list.printList();

    }
}
