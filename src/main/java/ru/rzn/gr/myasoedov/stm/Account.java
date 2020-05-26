package ru.rzn.gr.myasoedov.stm;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by grisha on 26.05.20.
 */
public class Account {
    final int id = IdGenerator.id.incrementAndGet();
    final ReadWriteLock lock = new ReentrantReadWriteLock();
    int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Acc{" +
                balance +
                '}';
    }
}
