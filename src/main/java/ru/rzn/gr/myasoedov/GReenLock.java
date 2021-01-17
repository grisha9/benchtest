package ru.rzn.gr.myasoedov;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class GReenLock {
    private final AtomicReference<Thread> thread = new AtomicReference<>(null);
    private volatile int reentrantCount = 0;

    public void lock() {
        Thread currentThread = Thread.currentThread();
        Thread prev = thread.get();
        if (prev != currentThread) {
            boolean locked = false;
            while (!locked) {
                locked = thread.compareAndSet(null, currentThread);
            }

        }
        reentrantCount++;
    }

    public void unlock() {
        Thread currentThread = Thread.currentThread();
        Thread prev = this.thread.get();
        if (prev != currentThread || reentrantCount <= 0) {
            throw new IllegalStateException("thread has not lock!!!");
        }
        reentrantCount--;
        if (reentrantCount == 0) {
            thread.compareAndSet(currentThread, null);
        }
    }


    public static void main(String[] args) {
        GReentrantLock lock = new GReentrantLock();
        for (int i = 0; i < 200; i++) {
            Logic logic = new Logic(i, lock);
            new Thread(logic).start();
        }
    }

    private static class Logic implements Runnable {
        private final int number;
        private final GReentrantLock lock;

        private Logic(int number, GReentrantLock lock) {
            this.number = number;
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                lock.lock();
                System.out.println(number + " " + "start");
                try {
                    Thread.sleep(10);
                    boolean b = ThreadLocalRandom.current().nextBoolean();
                    if (b) {
                        lock.lock();
                        System.out.println(number + " " + "start");
                        try {
                            Thread.sleep(10);
                            b = ThreadLocalRandom.current().nextBoolean();
                            if (b) {
                                lock.lock();
                                System.out.println(number + " " + "start");
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {
                                    System.out.println(number + " " + "finish");
                                    lock.unlock();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            System.out.println(number + " " + "finish");
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(number + " " + "finish");
                    lock.unlock();
                }
            }
        }
    }
}
