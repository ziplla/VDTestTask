package ru.ziplla.algo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class ConversionSystemTest {

    @Test
    public void testSuccessfulConversions() {
        String input = "1024 byte = 1 kilobyte\n" +
                "2 bar = 12 ring\n" +
                "16.8 ring = 2 pyramid\n" +
                "4 hare = 1 cat\n" +
                "5 cat = 0.5 giraffe\n" +
                "1 byte = 8 bit\n" +
                "15 ring = 2.5 bar\n" +
                "1 pyramid = ? bar\n" +
                "1 giraffe = ? hare\n" +
                "0.5 byte = ? cat\n" +
                "2 kilobyte = ? bit";

        String expectedOutput = "1.0 pyramid = 1.4 bar\n" +
                "1.0 giraffe = 40.0 hare\n" +
                "Conversion not possible.\n" +
                "2.0 kilobyte = 16384.0 bit";

        assertConversionSystem(input, expectedOutput);
    }

    private void assertConversionSystem(String input, String expectedOutput) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));

        ConversionSystem.main(new String[]{});

        String actualOutput = outputStream.toString().trim();
        String[] expectedLines = expectedOutput.split("\\r?\\n");
        String[] actualLines = actualOutput.split("\\r?\\n");

        assertEquals(expectedLines.length, actualLines.length);

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i], actualLines[i]);
        }
    }

    @Test
    public void testImpossibleConversions() {
        String input = "1 meter = 1000 millimeters\n" +
                "1 millimeter = 0.001 meters\n" +
                "1 kilogram = 1000 grams\n" +
                "1 gram = 0.001 kilograms\n" +
                "1 meter = ? gram\n" + // Trying to convert between incompatible units
                "1 gram = ? meter";    // Trying to convert between incompatible units

        String expectedOutput = "Conversion not possible.\n" +
                "Conversion not possible.";

        assertConversionSystem(input, expectedOutput);
    }

}
