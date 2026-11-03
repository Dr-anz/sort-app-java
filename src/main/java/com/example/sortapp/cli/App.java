package com.example.sortapp.cli;

import com.example.sortapp.io.CustomList;
import com.example.sortapp.io.FileWriterUtil;
import com.example.sortapp.model.Student;
import com.example.sortapp.strategy.BubbleSortStrategy;
import com.example.sortapp.strategy.EvenOnlySortStrategy;
import com.example.sortapp.strategy.StudentComparators;
import com.example.sortapp.concurrent.ConcurrentCounter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {
    private static Student[] data = null;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": data = loadFromFile(sc); break;
                case "2": data = generateRandom(sc); break;
                case "3": data = enterManually(sc); break;
                case "4": sort(StudentComparators.byId()); break;
                case "5": sort(StudentComparators.byAge()); break;
                case "6": sort(StudentComparators.byGpa()); break;
                case "7": sortEvenOnly(); break;
                case "8": writeToFile(sc); break;
                case "9": countThreads(sc); break;
                case "0": System.out.println("Выход"); return;
                default: System.out.println("Неверный выбор");
            }
            printArray();
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Из файла | 2. Рандом (стримы) | 3. Вручную");
        System.out.println("4. По ID | 5. По возрасту | 6. По GPA");
        System.out.println("7. Чётные (сортировка) | 8. В файл | 9. Потоки | 0. Выход");
        System.out.print("Выбор: ");
    }

    private static void printArray() {
        if (data == null || data.length == 0) {
            System.out.println("[Массив пуст]");
            return;
        }
        System.out.println("Текущий список:");
        for (Student s : data) System.out.println(s);
    }

    // --- Логика заполнения ---

    private static Student[] loadFromFile(Scanner sc) {
        System.out.print("Путь к файлу: ");
        String path = sc.nextLine();
        CustomList list = new CustomList(10);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;
                Student s = new Student.Builder()
                        .id(parts[0].trim()).age(Integer.parseInt(parts[1].trim()))
                        .gpa(Double.parseDouble(parts[2].trim())).build();
                list.add(s);
            }
        } catch (Exception e) { System.out.println("Ошибка файла: " + e.getMessage()); }
        return list.toArray();
    }

    private static Student[] generateRandom(Scanner sc) {
        System.out.print("Сколько студентов: ");
        int len = Integer.parseInt(sc.nextLine());
        CustomList list = new CustomList(len);
        Random r = new Random();
        // ГЛАВНОЕ ДЛЯ ЗАДАНИЯ: заполнение через стрим
        Stream.generate(() -> new Student.Builder()
                .id("S" + r.nextInt(100))
                .age(18 + r.nextInt(20))
                .gpa(2.0 + r.nextDouble() * 2.0).build())
                .limit(len)
                .forEach(list::add);
        return list.toArray();
    }

    private static Student[] enterManually(Scanner sc) {
        System.out.print("Сколько студентов: ");
        int count = Integer.parseInt(sc.nextLine());
        CustomList list = new CustomList(count);
        for (int i = 0; i < count; i++) {
            System.out.printf("Студент #%d (id,age,gpa через пробел): ", i+1);
            String[] inp = sc.nextLine().split(" ");
            Student s = new Student.Builder().id(inp[0]).age(Integer.parseInt(inp[1]))
                    .gpa(Double.parseDouble(inp[2])).build();
            list.add(s);
        }
        return list.toArray();
    }

    // --- Операции ---

    private static void sort(java.util.Comparator<Student> comp) {
        if (data == null) { System.out.println("Сначала заполните массив!"); return; }
        new BubbleSortStrategy<>().sort(data, comp);
        System.out.println("Отсортировано.");
    }

    private static void sortEvenOnly() {
        if (data == null) { System.out.println("Сначала заполните массив!"); return; }
        EvenOnlySortStrategy<Student> strat = new EvenOnlySortStrategy<>(Student::getAge);
        strat.sort(data, StudentComparators.byAge());
        System.out.println("Чётные отсортированы.");
    }

    private static void writeToFile(Scanner sc) throws IOException {
        if (data == null) return;
        System.out.print("Имя файла (например, out.txt): ");
        FileWriterUtil.appendToFile(sc.nextLine(), data);
        System.out.println("Записано.");
    }

    private static void countThreads(Scanner sc) throws InterruptedException {
        if (data == null || data.length == 0) return;
        System.out.print("ID студента для поиска: ");
        String targetId = sc.nextLine();
        Student target = data[0]; // упрощение: берем первого, если не нашли
        for (Student s : data) if (s.getId().equals(targetId)) target = s;

        System.out.print("Число потоков: ");
        int threads = Integer.parseInt(sc.nextLine());
        int res = ConcurrentCounter.countOccurrences(data, target, threads);
        System.out.println("Найдено вхождений: " + res);
    }
}
