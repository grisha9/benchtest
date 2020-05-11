package ru.rzn.gr.myasoedov;

/**
 * Created by grisha on 19.05.20.
 */
public class LinkedListRevers {
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

    public static void main(String[] args) {
        Node head = new Node(5, new Node(4, new Node(3, new Node(2, new Node(1, new Node(0, null))))));
        //Node revers = revers(head, null);
        Node revers = revers2(head);
        System.out.println(revers);
    }

    private static Node revers(Node current, Node prev) {
        if (current.next != null) {
            Node next = current.next;
            Node result = revers(next, current);
            next.next = current;
            current.next = null;
            return result;
        } else {
            return current;
        }
    }

    private static Node revers2(Node current) {
        if (current.next == null) return current;

        Node next1 = current.next;
        Node next2 = null;
        if (next1.next != null) {
            next2 = next1.next;
        }

        current.next = null;
        next1.next = current;
        while (next2 != null) {
            Node tmpNext2 = next2.next;
            next2.next = next1;

            next1 = next2;
            next2 = tmpNext2;
        }

        return next1;
    }
}


