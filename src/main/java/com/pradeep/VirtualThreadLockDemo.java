package com.pradeep;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualThreadLockDemo {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        log.info("Java {}", System.getProperty("java.version"));
        log.info("Available processors: {}", Runtime.getRuntime().availableProcessors());

        int tasks = 10_000; // Try increasing to 100_000 or 1_000_000
        log.info("Running with Platform Threads...");
        runWithExecutor(Executors.newFixedThreadPool(200), tasks);

        log.info("Running with Virtual Threads (no lock)...");
        runWithExecutor(Executors.newVirtualThreadPerTaskExecutor(), tasks);

        log.info("Running with Virtual Threads (with synchronized lock)...");
        runWithExecutorWithLock(Executors.newVirtualThreadPerTaskExecutor(), tasks);
    }

    // Simulate blocking work without locking
    private static void runWithExecutor(java.util.concurrent.ExecutorService executor, int tasks) throws InterruptedException {
        Instant start = Instant.now();
        for (int i = 0; i < tasks; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(100); // Simulate I/O wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
        boolean isTerminated = executor.awaitTermination(1, java.util.concurrent.TimeUnit.MINUTES);
        logCompletion(isTerminated, start);
    }

    private static void logCompletion(boolean isTerminated, Instant start) {
        log.info("All tasks completed: {}", isTerminated);
        log.info("Completed in: {} ms", Duration.between(start, Instant.now()).toMillis());
    }

    // Simulate blocking work with locking
    private static void runWithExecutorWithLock(java.util.concurrent.ExecutorService executor, int tasks) throws InterruptedException {
        Instant start = Instant.now();
        for (int i = 0; i < tasks; i++) {
            executor.submit(() -> {
                synchronized (LOCK) {
                    try {
                        Thread.sleep(0, 100000); // Simulate work while holding the lock
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        executor.shutdown();
        boolean isTerminated = executor.awaitTermination(5, java.util.concurrent.TimeUnit.MINUTES);
        logCompletion(isTerminated, start);
    }
}
