package ru.rzn.gr.myasoedov;

import java.util.concurrent.atomic.AtomicInteger;

public class Step40 {
    public static final int totalStep = 40;
    private static AtomicInteger currentStep = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < totalStep; i++) {
            OneStep step = new OneStep(i);
            new Thread(step).start();
        }
        Thread.sleep(60_000);
    }

    private static class OneStep implements Runnable {
        private final int numberStep;

        private OneStep(int numberStep) {
            this.numberStep = numberStep;
        }

        @Override
        public void run() {
            while (true) {
                int currentNumber = currentStep.get();
                if (currentNumber == numberStep) {
                    System.out.println("step " + numberStep);
                    boolean b = currentStep.compareAndSet(numberStep, (numberStep + 1) % 40);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!b) {
                        throw new IllegalStateException("step " + numberStep);
                    }
                }
            }
        }
    }
}
