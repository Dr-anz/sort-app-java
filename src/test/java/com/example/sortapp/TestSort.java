package com.example.sortapp;

import com.example.sortapp.io.CustomList;
import com.example.sortapp.model.Student;
import com.example.sortapp.strategy.BubbleSortStrategy;
import com.example.sortapp.strategy.StudentComparators;

public class TestSort {
    public static void main(String[] args) {
        System.out.println("=== Тест сортировки (ручной тест) ===");

        // 1. Создаём кастомную коллекцию и заполняем через стрим
        CustomList list = new CustomList(5);
        java.util.stream.Stream.of(
            new Student.Builder().id("S3").age(23).gpa(3.1).build(),
            new Student.Builder().id("S1").age(20).gpa(3.5).build(),
            new Student.Builder().id("S4").age(24).gpa(2.9).build(),
            new Student.Builder().id("S2").age(21).gpa(3.8).build(),
            new Student.Builder().id("S5").age(25).gpa(3.0).build()
        ).forEach(list::add);

        Student[] arr = list.toArray();

        // 2. Сортируем по возрасту (BubbleSort)
        new BubbleSortStrategy<>().sort(arr, StudentComparators.byAge());

        // 3. Проверяем, что отсортировано по возрастанию возраста
        boolean ok = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getAge() > arr[i + 1].getAge()) {
                ok = false;
                System.out.println("FAIL: порядок нарушен между " + arr[i] + " и " + arr[i+1]);
            }
        }

        if (ok) {
            System.out.println("PASS: сортировка по возрасту работает корректно.");
        } else {
            System.out.println("FAIL: сортировка не работает.");
        }
    }
}
