package ru.rzn.gr.myasoedov.philosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by grisha on 01.06.20.
 */
public class Fork {
    final int id;
    final Lock lock = new ReentrantLock(true);//true - false

    public Fork(int id) {
        this.id = id;
    }
}
