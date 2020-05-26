package ru.rzn.gr.myasoedov.stm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by grisha on 26.05.20.
 */
public class Bank {

    private final Account[] accounts;

    public Bank() {
        accounts = new Account[]{
                new Account(100), new Account(0),
                new Account(100), new Account(0),
                new Account(100), new Account(0),
                new Account(100), new Account(0)};
    }

    public void work() throws InterruptedException {
        int parallelism = Runtime.getRuntime().availableProcessors() * 4;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < parallelism; i++) {
            executorService.submit(() -> {
                while (true) {
                    int from = ThreadLocalRandom.current().nextInt(8);
                    int to = ThreadLocalRandom.current().nextInt(8);
                    if (from != to) {
                        int amount = ThreadLocalRandom.current().nextInt(6) * 10;
                        AccountManager.transfer(accounts[from], accounts[to], amount);
                    }
                    Thread.yield();
                }
            });
        }
        Thread.sleep(200);
        System.out.println(AccountManager.sum(accounts) + " - " + AccountManager.toString(accounts));

        Thread.sleep(200);
        System.out.println(AccountManager.sum(accounts) + " - " + AccountManager.toString(accounts));

        Thread.sleep(200);
        System.out.println(AccountManager.sum(accounts) + " - " + AccountManager.toString(accounts));

        Thread.sleep(200);
        System.out.println(AccountManager.sum(accounts) + " - " + AccountManager.toString(accounts));

        Thread.sleep(200);
        System.out.println(AccountManager.sum(accounts) + " - " + AccountManager.toString(accounts));

        executorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        bank.work();
    }
}
