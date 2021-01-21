package ru.rzn.gr.myasoedov;

import java.util.concurrent.atomic.AtomicReference;

public class StackAtomic {
    static class Stack<T> {
        AtomicReference<Node<T>> head = new AtomicReference<>(null);

        public void push(T element) {
            Node<T> node = new Node<>(element);

            boolean result = false;
            while (!result) {
                Node<T> currentHead = head.get();
                node.next = currentHead;
                result = head.compareAndSet(currentHead, node);
            }
        }

        public T pop() {
            boolean result = false;
            Node<T> currentHead = null;
            while (!result) {
                currentHead = head.get();
                if (head.get() == null) {
                    throw new IllegalStateException();
                }
                Node<T> headNext = currentHead.next;
                result = head.compareAndSet(currentHead, headNext);
            }
            return currentHead.node;
        }

        public boolean isEmpty() {
            return head.get() == null;
        }

        static class Node<T> {
            T node;
            Node<T> next;

            public Node(T node) {
                this.node = node;
            }
        }

    }
}
