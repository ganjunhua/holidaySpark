package datastructure;

public class Solution {

    public class ListNode {
        private int val;
        ListNode next;

        public ListNode(int x) {
            val = x;
        }

        public ListNode(int[] arr) {
            this.val = arr[0];
            ListNode cur = this; // 将当前对象 作为首元节点
            for (int i = 1; i < arr.length; i++) {
                cur.next = new ListNode(arr[i]);
                cur = cur.next;

            }
        }

        public String toString() {
            StringBuilder res = new StringBuilder();
            ListNode cur = this;
            while (cur != null) {
                res.append(cur.val + "->");
                cur = cur.next;
            }
            res.append("null");
            return res.toString();
        }
    }

    public ListNode removeElements(ListNode head, int val) {
        while (head != null && head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }
        if (head == null) {
            return null;
        }
        ListNode prev = head;
        while (prev.next != null) {
            if (prev.next.val == val) {
                ListNode delNode = prev.next;
                prev.next = delNode.next;
                delNode = null;
            } else {
                prev = prev.next;
            }
        }
        return head;
    }

    public ListNode removeE2(ListNode head, int val) {
        ListNode dummyHead = new ListNode(-1);
        dummyHead.next = head;
        ListNode prev = dummyHead;

        while (prev.next != null) {
            if (prev.next.val == val) {
                prev.next = prev.next.next;
            } else {
                prev = prev.next;
            }
        }
        return dummyHead;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 6, 5, 6};
        ListNode head = new Solution().new ListNode(nums);
        // System.out.println(head);
        System.out.println(new Solution().removeE2(head,6));
    }
}

