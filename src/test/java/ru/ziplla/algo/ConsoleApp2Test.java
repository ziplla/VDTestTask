package ru.ziplla.algo;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleApp2Test {
    private static final String FILE_NAME = "out.txt";

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_NAME));
    }

    @Test
    public void testRun() throws IOException {
        int n = 10;
        ConsoleApp2.run(n);

        Awaitility.await().atMost(5, TimeUnit.SECONDS).until(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String finalValue = reader.readLine();
                return Integer.parseInt(finalValue) == n;
            }
        });

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String finalValue = reader.readLine();
            assertEquals(String.valueOf(n), finalValue);
        }
    }
}
