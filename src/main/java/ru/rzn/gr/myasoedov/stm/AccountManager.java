package ru.rzn.gr.myasoedov.stm;


import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountManager {
    private AccountManager() {
    }

    public int getBalance(Account account) {
        account.lock.readLock().lock();
        try {
            return account.balance;
        } finally {
            account.lock.readLock().unlock();
        }
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
        return lockFunc(
                accounts,
                accountList -> accountList.stream().mapToInt(a -> a.balance).sum()
        );
    }

    public static String toString(Account[] accounts) {
        return lockFunc(
                accounts,
                accountList -> accountList.stream().map(Account::toString).collect(Collectors.joining(" "))
        );
    }

    private static <T> T lockFunc(Account[] accounts, Function<List<Account>, T> function) {
        List<Account> sortedAccounts = Stream.of(accounts)
                .sorted(Comparator.comparingInt(a -> a.id))
                .collect(Collectors.toList());
        Stack<Lock> locks = new Stack<>();
        for (Account sortedAccount : sortedAccounts) {
            sortedAccount.lock.readLock().lock();
            locks.push(sortedAccount.lock.readLock());
        }
        T result;
        try {
            result = function.apply(sortedAccounts);
        } finally {
            while (!locks.isEmpty()) {
                Lock lock = locks.pop();
                lock.unlock();
            }
        }
        return result;
    }
}
