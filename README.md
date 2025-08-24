Taming the Threads Demo
This is maven java project to demonstrate the Taming the Threads concept from the linkedin newsletter 'Beyond the Stack'

VirtualThreadLockDemo - Demonstrates the use of virtual threads in Java to handle multiple tasks concurrently. This also demonstrate the VirtualThread behavious in case of Resource Locking.
To run the demo, execute the main method in the VirtualThreadLockDemo class. You can adjust the number of tasks and the sleep duration to see how virtual threads handle concurrency and resource locking.

StructuredConcurrencyDemo - Demonstrates the use of structured concurrency in Java to manage multiple tasks in a more organized manner. This example uses the StructuredTaskScope to run tasks concurrently and handle their results in a structured way.
To run the demo, execute the main method in the StructuredConcurrencyDemo class. You can adjust the number of tasks and the sleep duration to see how structured concurrency helps in managing concurrent tasks.

CompletableFutureDemo - Demonstrates the use of CompletableFuture in Java to handle asynchronous tasks. This example shows how to create and combine multiple CompletableFutures to perform tasks concurrently and handle their results.
To run the demo, execute the main method in the CompletableFutureDemo class. You can adjust the number of tasks and the sleep duration to see how CompletableFuture helps in managing asynchronous tasks.

To run the demos, ensure you have Java 19 or later installed, as virtual threads and structured concurrency are features introduced in recent versions of Java. You can compile and run the project using Maven.
--enable-preview is required to run the code as it uses preview features of Java.



