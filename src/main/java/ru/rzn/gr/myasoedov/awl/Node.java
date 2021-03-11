package ru.rzn.gr.myasoedov.awl;

//created asc ordered linked list and return head
public class Node {
    int value;
    Node next;

    public Node(int v, Node n) {
        value = v;
        next = n;
    }

    public Node concat(Node n) {
        next = n;
        return this;
    }

    public static Node fun(Node n, int x) {
        if (n == null || x < n.value) {
            return new Node(x, n);
        } else {
            return n.concat(fun(n.next, x));
        }
    }

    public static int kv(int n) {
        return ((n -1) / 7) + 2;
    }

    /**
     * 1  1-7
     * 2  9-14
     * 3  15-21
     * 4  22-28
     * 5  29-35
     * 6  36-42
     * 7  43-49
     * 8  50-56
     * 9  57-63
     * 10 64-70
     * 11 71-77
     * 12 78-84
     * @param args
     */
    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 1_000; i++) {
            for (int j = 0; j < i; j++) {
                count++;
            }
        }
        int kv = kv(1); //2
        kv = kv(7);//2
        kv = kv(8);//3
        kv = kv(9);//3
        kv = kv(14);//3
        kv = kv(77);//12
        kv = kv(84);
        kv = kv(85);
        Node fun = fun(null, 10);
        Node fun1 = fun(fun, 5);
        Node fun11 = fun(fun1, 15);
        Node fun111 = fun(fun11, 7);

        Node fun2 = fun(null, 20);
        Node fun22 = fun(fun2, 50);
        Node fun222 = fun(fun22, 30);

        Node original = fun222;
        Node iteration = fun(fun111, 100);
        Node merge2 = merge2(original, iteration);
        Node merge = merge(original, iteration);

        System.out.println(1);
    }

    public static Node merge(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        }
        while (n2 != null) {
            n1 = fun(n1, n2.value);
            n2 = n2.next;
        }
        return n1;
    }

    public static Node merge2(Node n1, Node n2) {
        Node result = mergeInner(n1, null);
        result = mergeInner(n2, result);
        return result;
    }

    private static Node mergeInner(Node head, Node result) {
        while (head != null) {
            result = fun(result, head.value);
            head = head.next;
        }
        return result;
    }
}
