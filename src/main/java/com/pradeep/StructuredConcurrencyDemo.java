package com.pradeep;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

@Slf4j
public class StructuredConcurrencyDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        log.info("Java {}", System.getProperty("java.version"));

        Instant start = Instant.now();
        for(int i=0; i<100; i++)
            runTask();

        log.info("Completed in: {} ms", Duration.between(start, Instant.now()).toMillis());
    }

    private static void runTask() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Launch multiple tasks concurrently
            var userTask = scope.fork(StructuredConcurrencyDemo::fetchUserFromDB);
            var orderTask = scope.fork(StructuredConcurrencyDemo::fetchOrdersFromService);
            var paymentTask = scope.fork(StructuredConcurrencyDemo::fetchPaymentsFromGateway);

            // Wait for all tasks (or shutdown on failure)
            scope.join();
            scope.throwIfFailed();

            // Combine results
            var user = userTask.get();
            var orders = orderTask.get();
            var payments = paymentTask.get();

            log.info("Results combined: {}, {}, {}", user, orders, payments);
        }
    }

    private static String fetchUserFromDB() throws InterruptedException {
        Thread.sleep(200); // Simulate DB call
        return "User[Pradeep]";
    }

    private static String fetchOrdersFromService() throws InterruptedException {
        Thread.sleep(300); // Simulate API call
        return "Orders[5 items]";
    }

    private static String fetchPaymentsFromGateway() throws InterruptedException {
        Thread.sleep(400); // Simulate payment system call
        return "Payments[Success]";
    }
}
