package com.mrle.hello.simple;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        PredicateTest predicateTest = new PredicateTest();
        List<Integer> result = predicateTest.conditionFilter(list, integer -> integer > 5);
        result.forEach(System.out::println);
        System.out.println("----------");

        result = predicateTest.conditionFilter(list, integer -> integer < 8);
        result.forEach(System.out::println);
        System.out.println("----------");

        result = predicateTest.conditionFilter(list, integer -> true);
        result.forEach(System.out::println);
        System.out.println("----------");
    }

    public List<Integer> conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

}
