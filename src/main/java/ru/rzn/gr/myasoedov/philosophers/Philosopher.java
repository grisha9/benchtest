package ru.rzn.gr.myasoedov.philosophers;

public class Philosopher {
    private final int id;
    private final Fork fork1;
    private final Fork fork2;

    private long eaten;
    private boolean ready;

    public Philosopher(int id, Fork fork1, Fork fork2) {
        this.id = id;
        if (fork1.id < fork2.id) {
            this.fork1 = fork1;
            this.fork2 = fork2;
        } else {
            this.fork1 = fork2;
            this.fork2 = fork1;
        }
        this.ready = true;
    }

    public void think() {
        try {
            while (ready) {
                Thread.yield();//dummy
                try {
                    fork1.lock.lock();
                    try {
                        fork2.lock.lock();
                        eaten++;
                        Thread.yield();//eat
                    } finally {
                        fork2.lock.unlock();
                    }
                } finally {
                    fork1.lock.unlock();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public long getEaten() {
        return eaten;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                ", eaten=" + eaten +
                '}';
    }
}
