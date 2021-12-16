package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();
    private static final int THREADS_NUMBER = 100;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread0.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        mainConcurrency.inc();
                    }

                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        Thread.sleep(500);
        System.out.println(mainConcurrency.counter);
    }

    private synchronized void inc() {
//      synchronized (this) на экземпляр класса в куче
//      synchronized (MainConcurrency.class) если статик метод то по умолчанию на объект класса в метаспэйс
//      double a = Math.sin(13.);
//        synchronized (this) {
        //            System.out.println(this);
            counter++;
//              wait();
//              readFile
//        }
    }
}


