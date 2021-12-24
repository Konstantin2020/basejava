package com.urise.webapp;

import com.urise.webapp.model.Resume;

public class MainDeadLock {
    private static final Resume RESUME_0 = ResumeTestData.fillResume("0", "resume0");
    private static final Resume RESUME_1 = ResumeTestData.fillResume("1", "resume1");

    public static void main(String[] args) {
        new Thread(() -> lockResume(RESUME_0, RESUME_1)).start();
        new Thread(() -> lockResume(RESUME_1, RESUME_0)).start();
    }

    public static void lockResume(Resume r0, Resume r1) {
        String threadName = Thread.currentThread().getName();
        synchronized (r0) {
            System.out.println("Thread " + threadName + " locked " + r0.getFullName());
            try {
                Thread.sleep(300);
//                r0.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + threadName + " is waiting for the resume to be unlocked by another thread " + r1.getFullName());
  //         r0.notifyAll();
            synchronized (r1) {
                System.out.println("Thread " + threadName + " locked " + r1.getFullName());
            }
        }
    }
}

