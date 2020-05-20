package ru.rzn.gr.myasoedov;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class GReentrantLock {
    private final AtomicReference<Thread> lock = new AtomicReference<>(null);
    private int reentrantCount = 0;

    public void lock() {
        Thread currentThread = currentThread();
        if (lock.get() == currentThread) {
            System.out.println(Thread.currentThread() + "- start" + " " + LocalDateTime.now());
            reentrantCount++;
            return;
        }
        boolean b = lock.compareAndSet(null, currentThread);
        while (!b) {
            b = lock.compareAndSet(null, currentThread);
        }
        System.out.println(Thread.currentThread() + "- start" + " " + LocalDateTime.now());
        reentrantCount++;
    }

    public void unlock() {
        if (reentrantCount <= 0 || lock.get() != currentThread())
            throw new IllegalStateException("lock not acquire");

        System.out.println(Thread.currentThread() + "- end" + " " + LocalDateTime.now());
        if (--reentrantCount == 0) {
            lock.compareAndSet(currentThread(), null);
        }
    }

    public static void main(String[] args) {
        GReentrantLock lock = new GReentrantLock();
        Runnable run = () -> {
            lock.lock();
            try {
                sleep(1000);
                lock.lock();
                sleep(3000);
                lock.unlock();
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        };
        new Thread(run).start();
        new Thread(run).start();
    }
}
