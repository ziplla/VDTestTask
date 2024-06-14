package ru.ziplla.algo;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsoleApp2 {
    private static final String FILE_NAME = "out.txt";
    private static final Object lock = new Object();

    public static void main(String[] args) {
        int n = 10;
        run(n);
    }

    public static void run(int n) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println(0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 2; i++) {
            int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < n / 2; j++) {
                    incrementValue(threadId);
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String finalValue = reader.readLine();
            System.out.println("Final value in file: " + finalValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void incrementValue(int threadId) {
        synchronized (lock) {
            try {
                int currentValue;
                try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                    currentValue = Integer.parseInt(reader.readLine());
                }

                int newValue = currentValue + 1;

                System.out.println("Thread " + threadId + ": " + currentValue + " -> " + newValue);

                try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
                    writer.println(newValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
