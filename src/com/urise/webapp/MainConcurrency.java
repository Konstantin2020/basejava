package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrantReadWriteLock.writeLock();
    private static final Lock READ_LOCK = reentrantReadWriteLock.readLock();
//  private static final SimpleDateFormat sdf = new SimpleDateFormat();
// 1) объявление ThreadLocal<SimpleDateFormat> классически
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        }
    };
// 2) либо через лямбду private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =ThreadLocal.withInitial(()-> new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss"));
// 3) либо через потоки private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

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
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();

//        CompletionService completionService = new ExecutorCompletionService(executorService);

//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
//            Thread thread = new Thread(new Runnable() {
//               @Override
//                public void run() {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//  недопустимо     System.out.println(sdf.format(new Date()));
                    System.out.println(DATE_FORMAT.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
//            completionService.poll();

//            System.out.println(future.isDone());
//            System.out.println(future.get());

//            thread.start();
//            threads.add(thread);
        }
/*        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        Thread.sleep(500);
//      System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());

        final String lock1 = "lock1";
        final String lock2 = "lock2";

//        deadLock(lock1, lock2);
//        deadLock(lock2, lock1);
    }

    public static void deadLock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private
        // synchronized
    void inc() {
//      synchronized (this) на экземпляр класса в куче
//      synchronized (MainConcurrency.class) если статик метод то по умолчанию на объект класса в метаспэйс
//      double a = Math.sin(13.);
//        synchronized (this) {
        //            System.out.println(this);
//        lock.lock();
//        try {
        atomicCounter.incrementAndGet();
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//              wait();
//              readFile
//        }
    }
}




