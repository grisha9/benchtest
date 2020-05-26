package ru.rzn.gr.myasoedov.stm;


import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountManager {
    private AccountManager() {
    }

    public static void transfer(Account from, Account to, int amount) {
        Lock firstLock = from.id < to.id ? from.lock.writeLock() : to.lock.writeLock();
        Lock secondLock = from.id >= to.id ? from.lock.writeLock() : to.lock.writeLock();
        firstLock.lock();
        try {
            secondLock.lock();
            try {
                if (from.balance >= amount) {
                    from.balance -= amount;
                    to.balance += amount;
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public static int sum(Account[] accounts) {
        List<Account> sortedAccounts = Stream.of(accounts)
                .sorted(Comparator.comparingInt(a -> a.id))
                .collect(Collectors.toList());
        for (Account sortedAccount : sortedAccounts) {
            sortedAccount.lock.readLock().lock();
        }

        int sum;
        try {
            sum = sortedAccounts.stream().mapToInt(a -> a.balance).sum();
        } finally {
            for (Account sortedAccount : sortedAccounts) {
                sortedAccount.lock.readLock().unlock();
            }
        }
        return sum;
    }

    public static String toString(Account[] accounts) {
        List<Account> sortedAccounts = Stream.of(accounts)
                .sorted(Comparator.comparingInt(a -> a.id))
                .collect(Collectors.toList());
        for (Account sortedAccount : sortedAccounts) {
            sortedAccount.lock.readLock().lock();
        }
        String result;
        try {
            result = sortedAccounts.stream().map(Account::toString).collect(Collectors.joining(" "));
        } finally {
            for (Account sortedAccount : sortedAccounts) {
                sortedAccount.lock.readLock().unlock();
            }
        }
        return result;
    }
}
