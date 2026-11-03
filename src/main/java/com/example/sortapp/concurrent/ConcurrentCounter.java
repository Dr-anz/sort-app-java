package com.example.sortapp.concurrent;

import com.example.sortapp.model.Student;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter {
    public static int countOccurrences(Student[] array, Student target, int threadsCount) throws InterruptedException {
        AtomicInteger total = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(threadsCount);
        int chunk = (array.length + threadsCount - 1) / threadsCount;

        for (int t = 0; t < threadsCount; t++) {
            final int start = t * chunk;
            final int end = Math.min(start + chunk, array.length);

            new Thread(() -> {
                int local = 0;
                for (int i = start; i < end; i++) {
                    if (array[i] != null && array[i].equals(target)) local++;
                }
                total.addAndGet(local);
                latch.countDown();
            }).start();
        }

        latch.await();
        return total.get();
    }
}
