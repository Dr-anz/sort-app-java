package com.example.sortapp;

import com.example.sortapp.io.CustomList;
import com.example.sortapp.model.Student;
import com.example.sortapp.strategy.BubbleSortStrategy;
import com.example.sortapp.strategy.EvenOnlySortStrategy; // <-- Важно: нужен этот импорт для доп. задания
import com.example.sortapp.strategy.StudentComparators;

import java.util.function.ToIntFunction; // <-- Нужен для лямбды Student::getAge
import java.util.stream.Stream;

public class TestSort {
    public static void main(String[] args) {
        System.out.println("=== Тест сортировки по всем 3 полям + Доп. задание ===");

        // 1. Создаём исходные данные (один раз)
        CustomList list = new CustomList(5);
        Stream.of(
            new Student.Builder().id("S3").age(23).gpa(3.1).build(), // нечётный возраст
            new Student.Builder().id("S1").age(20).gpa(3.5).build(), // чётный
            new Student.Builder().id("S4").age(24).gpa(2.9).build(), // чётный
            new Student.Builder().id("S2").age(21).gpa(3.8).build(), // нечётный
            new Student.Builder().id("S5").age(25).gpa(3.0).build()  // нечётный
        ).forEach(list::add);

        Student[] originalArr = list.toArray();

        // --- ТЕСТ 1: Сортировка по возрасту (основное) ---
        System.out.println("\n[Тест 1] Сортировка по возрасту:");
        testSortByAge(originalArr);

        // --- ТЕСТ 2: Сортировка по GPA (основное) ---
        System.out.println("\n[Тест 2] Сортировка по GPA:");
        testSortByGpa(originalArr);

        // --- ТЕСТ 3: Сортировка по ID (основное) ---
        System.out.println("\n[Тест 3] Сортировка по ID:");
        testSortById(originalArr);

        // --- ТЕСТ 4: Доп. задание — сортировка только ЧЁТНЫХ значений ---
        // Чётные по возрасту сортируются, нечётные остаются строго на своих позициях
        System.out.println("\n[Тест 4] Доп. задание: сортировка только чётных (по возрасту):");
        testEvenOnlySort(originalArr);

        System.out.println("\n=== Все тесты завершены ===");
    }

    // Вспомогательный метод: тест по возрасту
    private static void testSortByAge(Student[] originalArr) {
        Student[] arr = originalArr.clone();
        new BubbleSortStrategy<>().sort(arr, StudentComparators.byAge());

        boolean ok = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getAge() > arr[i + 1].getAge()) {
                ok = false;
                System.out.println("  FAIL: порядок нарушен между " + arr[i] + " и " + arr[i+1]);
            }
        }
        System.out.println(ok ? "  PASS: сортировка по возрасту работает." : "  FAIL: сортировка по возрасту не работает.");
    }

    // Вспомогательный метод: тест по GPA
    private static void testSortByGpa(Student[] originalArr) {
        Student[] arr = originalArr.clone();
        new BubbleSortStrategy<>().sort(arr, StudentComparators.byGpa());

        boolean ok = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getGpa() > arr[i + 1].getGpa()) {
                ok = false;
                System.out.println("  FAIL: порядок нарушен между " + arr[i] + " и " + arr[i+1]);
            }
        }
        System.out.println(ok ? "  PASS: сортировка по GPA работает." : "  FAIL: сортировка по GPA не работает.");
    }

    // Вспомогательный метод: тест по ID
    private static void testSortById(Student[] originalArr) {
        Student[] arr = originalArr.clone();
        new BubbleSortStrategy<>().sort(arr, StudentComparators.byId());

        boolean ok = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].getId().compareTo(arr[i + 1].getId()) > 0) {
                ok = false;
                System.out.println("  FAIL: порядок нарушен между " + arr[i] + " и " + arr[i+1]);
            }
        }
        System.out.println(ok ? "  PASS: сортировка по ID работает." : "  FAIL: сортировка по ID не работает.");
    }

    // --- НОВЫЙ МЕТОД: Тест для Доп. задания №1 (только чётные) ---
    private static void testEvenOnlySort(Student[] originalArr) {
        Student[] arr = originalArr.clone();

        // Создаём стратегию: берём возраст, сортируем по нему, но ТОЛЬКО чётные элементы
        new EvenOnlySortStrategy<>(Student::getAge).sort(arr, StudentComparators.byAge());

        // Вывод для наглядности (чтобы ты видел в консоли, что происходит)
        System.out.println("Результат (чётные отсортированы, нечётные на своих местах):");
        for (Student s : arr) {
            String status = (s.getAge() % 2 == 0) ? "[чётн]" : "[нечётн]";
            System.out.println("  " + s.getId() + " (age=" + s.getAge() + ") -> " + status);
        }

        // Проверка 1: все ЧЁТНЫЕ должны идти по возрастанию
        boolean evenOk = true;
        int lastEvenAge = -1;
        for (Student s : arr) {
            if (s.getAge() % 2 == 0) {
                if (s.getAge() < lastEvenAge) {
                    evenOk = false;
                    System.out.println("  FAIL: чётные не отсортированы по возрастанию!");
                }
                lastEvenAge = s.getAge();
            }
        }

        // Проверка 2: все НЕЧЁТНЫЕ должны быть на ТОЙ ЖЕ позиции, что и в оригинале
        boolean oddOk = true;
        for (int i = 0; i < arr.length; i++) {
            // Если в оригинале был нечётный возраст...
            if (originalArr[i].getAge() % 2 != 0) {
                // ...то в результате на этой позиции должен быть ТОТ ЖЕ самый объект
                if (!arr[i].equals(originalArr[i])) {
                    oddOk = false;
                    System.out.println("  FAIL: нечётный элемент сдвинулся с позиции " + i);
                }
            }
        }

        if (evenOk && oddOk) {
            System.out.println("  PASS: чётные отсортированы, нечётные остались на своих местах.");
        } else {
            System.out.println("  FAIL: условие доп. задания не выполнено.");
        }
    }
}
