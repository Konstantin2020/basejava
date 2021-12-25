package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class MainStreams {
    public static void main(String[] args) {

// вызов minValue через Streams
        System.out.println("Результат: " + minValue(new int[]{9, 2, 3, 7, 7, 9, 3}));
        System.out.println(" ");
// вызов oddOrEven через через Streams
        System.out.println("Результат: " + oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 1, 1, 1))));
        System.out.println(" ");
// вызов minValueSimple через for
        System.out.println("Результат: " + minValueSimple(new int[]{9, 2, 3, 7, 7, 9, 3}));
        System.out.println(" ");
// вызов oddOrEvenSimple через for и if
        System.out.println("Результат: " + oddOrEvenSimple(Arrays.asList(2, 2, 3, 6, 8, 9, 5, 3, 1)));
    }

    // реализация minValue через Streams
    private static int minValue(int[] values) {
        System.out.println("Реализация minValue через Streams");
        System.out.print("Набор элементов: ");
        for (int i : values) {
            System.out.print(i + " ");
        }
        System.out.println(" ");
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (result, element) -> result * 10 + element);
    }

    // реализация oddOrEven через через Streams
    private static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println("Реализация oddOrEven через Streams");
        System.out.println("Набор элементов: " + integers.toString());
        int totalSum = integers.stream().mapToInt(a -> a).sum();
        System.out.println("Сумма элементов: " + totalSum);
        return integers
                .stream()
// до оптимизации Idea .filter(x -> ((totalSum % 2 != 0) ? (x % 2 == 0) : (x % 2 != 0)))
// после оптимизации Idea .filter(x -> ((totalSum % 2 != 0) == (x % 2 == 0)))
                .filter(x -> ((totalSum % 2 != x % 2)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // реализация minValue через for
    private static int minValueSimple(int[] values) {
        System.out.println("Реализация minValue через for");
        System.out.print("Набор элементов: ");
        for (int i : values) {
            System.out.print(i + " ");
        }
        System.out.println(" ");
        int[] sortVal = Arrays.stream(values).distinct().sorted().toArray();
        int totalVal = 0;
        for (int i = 0; i < sortVal.length; i++) {
            int acc = (int) pow(10, sortVal.length - 1 - i);
            totalVal += sortVal[i] * acc;
        }
        return totalVal;
    }

    // реализация oddOrEven через for и if
    private static List<Integer> oddOrEvenSimple(List<Integer> integers) {
        System.out.println("Реализация oddOrEven через for и if");
        System.out.println("Набор элементов: " + integers.toString());
        int totalSum = 0;
        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();
        for (Integer integer : integers) {
            totalSum += integer;
            if (integer % 2 != 0) {
                oddList.add(integer);
            } else {
                evenList.add(integer);
            }
        }
        System.out.println("Сумма элементов: " + totalSum);
        if (totalSum % 2 == 0) {
            integers = oddList;
        } else {
            integers = evenList;
        }
        return integers;
    }
}