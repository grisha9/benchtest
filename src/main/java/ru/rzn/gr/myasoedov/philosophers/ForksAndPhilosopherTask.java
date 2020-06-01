package ru.rzn.gr.myasoedov.philosophers;

import java.util.stream.Stream;

public class ForksAndPhilosopherTask {

    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        Fork[] forks = new Fork[n];
        Philosopher[] philosophers = new Philosopher[n];

        for (int i = 0; i < n; i++) {
            forks[i] = new Fork(i);
        }
        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i, forks[i % n], forks[(i + 1) % n]);
        }
        for (int i = 0; i < n; i++) {
            new Thread(philosophers[i]::think).start();
        }


        Thread.sleep(10_000);
        for (Philosopher philosopher : philosophers) {
            philosopher.setReady(false);
        }
        Thread.sleep(1_000);
        long sum = Stream.of(philosophers).mapToLong(Philosopher::getEaten).sum();
        System.out.println();
        for (Philosopher philosopher : philosophers) {
            System.out.print(philosopher.getEaten() * 1.0 / sum + " ");
        }
    }
}
