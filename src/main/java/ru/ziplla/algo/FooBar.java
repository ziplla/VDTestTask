package ru.ziplla.algo;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FooBar {
    public static void main(String[] args) {
        fooBarVar1(7);
        fooBarVar2(6);
        fooBarVar3(15);
    }

    public static void fooBarVar1(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
        }
        if ((n % 3 == 0) && (n % 5 == 0)) {
            System.out.println("FooBar");
        } else if (n % 5 == 0) {
            System.out.println("Bar");
        } else if (n % 3 == 0) {
            System.out.println("Foo");
        } else {
            System.out.println(n);
        }
    }

    public static void fooBarVar2(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
        }
        String result = (n % 3 == 0 ? "Foo" : "") + (n % 5 == 0 ? "Bar" : "");
        System.out.println(result.isEmpty() ? n : result);
    }

    public static void fooBarVar3(int n) {
        IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList()).forEach(System.out::println);
        String result = (n % 3 == 0 ? "Foo" : "") + (n % 5 == 0 ? "Bar" : "");
        System.out.println(result.isEmpty() ? n : result);
    }
}
