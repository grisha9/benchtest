package ru.rzn.gr.myasoedov.tree;

public class Remove {
    public static void main(String[] args) {
        Node head = new Node(5, new Node(4, new Node(3, new Node(2, new Node(1, new Node(0, null))))));
        Node revers = revers(head);
        System.out.println(revers);
    }

    private static Node revers(Node head) {
        if (head.next != null) {
            Node result = revers(head.next);
            head.next.next = head;
            head.next = null;
            return result;
        } else {
            return head;
        }
    }

    static class Node {
        final int value;
        Node next;

        Node(int value, Node nex) {
            this.value = value;
            this.next = nex;
        }

        @Override
        public String toString() {
            return "{" +
                    "value=" + value +
                    //(next != null ? ", next=" + next : "") +
                    (next != null ? ", next=" + next.value : "") +
                    '}';
        }
    }
}

