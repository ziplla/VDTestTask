package ru.ziplla.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FooBarTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testFooBarVar1() {
        FooBar.fooBarVar1(15);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\nFooBar\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar2() {
        FooBar.fooBarVar2(15);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\nFooBar\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar3() {
        FooBar.fooBarVar3(15);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\nFooBar\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar1_nonMultiple() {
        FooBar.fooBarVar1(7);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n7\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar2_nonMultiple() {
        FooBar.fooBarVar2(7);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n7\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar3_nonMultiple() {
        FooBar.fooBarVar3(7);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n7\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar1_multipleOf3() {
        FooBar.fooBarVar1(9);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\nFoo\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar2_multipleOf3() {
        FooBar.fooBarVar2(9);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\nFoo\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }

    @Test
    public void testFooBarVar3_multipleOf3() {
        FooBar.fooBarVar3(9);
        String expectedOutput = "1\n2\n3\n4\n5\n6\n7\n8\n9\nFoo\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString().replace("\r", ""));
    }
}
