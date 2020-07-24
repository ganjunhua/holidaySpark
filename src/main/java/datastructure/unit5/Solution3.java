package datastructure.unit5;


public class Solution3 {

    public ListNode1 remove3(ListNode1 head, int val) {
        if (head == null) {
            return null;
        }
        ListNode1 res = remove3(head.next, val);
        if (head.val == val) {
            return res;
        } else {
            head.next = res;
            return head;
        }
    }

    public class ListNode1 {
        private int val;
        ListNode1 next;

        public ListNode1(int x) {
            val = x;
        }
        public ListNode1(int[] arr) {
            this.val = arr[0];
            ListNode1 cur = this; // 将当前对象 作为首元节点
            for (int i = 1; i < arr.length; i++) {
                cur.next = new ListNode1(arr[i]);
                cur = cur.next;

            }
        }

        public String toString() {
            StringBuilder res = new StringBuilder();
            ListNode1 cur = this;
            while (cur != null) {
                res.append(cur.val + "->");
                cur = cur.next;
            }
            res.append("null");
            return res.toString();
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 6};
        ListNode1 head = new Solution3().new ListNode1(nums);
        System.out.println(head);
         new Solution3().remove3(head,6);
        System.out.println(head);
    }
}
