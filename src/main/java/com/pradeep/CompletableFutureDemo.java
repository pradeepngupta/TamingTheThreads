package com.pradeep;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("Java {}", System.getProperty("java.version"));

        Instant start = Instant.now();

        // Kick off async tasks
        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(CompletableFutureDemo::fetchUserFromDB);
        CompletableFuture<String> ordersFuture =
                CompletableFuture.supplyAsync(CompletableFutureDemo::fetchOrdersFromService);
        CompletableFuture<String> paymentsFuture =
                CompletableFuture.supplyAsync(CompletableFutureDemo::fetchPaymentsFromGateway);

        // Combine all
        CompletableFuture<Void> allFutures =
                CompletableFuture.allOf(userFuture, ordersFuture, paymentsFuture);

        // When all complete, gather results
        CompletableFuture<String> combined = allFutures.thenApply(_ -> {
            try {
                return STR."\{userFuture.get()}, \{ordersFuture.get()}, \{paymentsFuture.get()}";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Results combined: {}", combined.get());
        log.info("Completed in: {} ms", Duration.between(start, Instant.now()).toMillis());
    }

    private static String fetchUserFromDB() {
        sleep(200); // Simulate DB call
        return "User[Pradeep]";
    }

    private static String fetchOrdersFromService() {
        sleep(300); // Simulate API call
        return "Orders[5 items]";
    }

    private static String fetchPaymentsFromGateway() {
        sleep(400); // Simulate payment call
        return "Payments[Success]";
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
