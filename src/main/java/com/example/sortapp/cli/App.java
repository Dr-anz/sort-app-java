package com.example.sortapp.cli;

import com.example.sortapp.io.CustomList;
import com.example.sortapp.io.FileWriterUtil;
import com.example.sortapp.model.Student;
import com.example.sortapp.strategy.*;

import java.io.*;
import java.util.*;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Student[] data = null;

        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Заполнить из файла (формат: id,age,gpa)");
            System.out.println("2. Заполнить рандомно");
            System.out.println("3. Заполнить вручную");
            System.out.println("4. Сортировать по ID");
            System.out.println("5. Сортировать по возрасту");
            System.out.println("6. Сортировать по GPA");
            System.out.println("7. Сортировать только чётные по возрасту");
            System.out.println("8. Записать в файл (добавить)");
            System.out.println("9. Подсчитать вхождения (многопоточно)");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": data = loadFromFile(sc); break;
                case "2": data = generateRandom(sc); break;
                case "3": data = enterManually(sc); break;
                case "4": sort(data, StudentComparators.byId(), new BubbleSortStrategy<>()); break;
                case "5": sort(data, StudentComparators.byAge(), new BubbleSortStrategy<>()); break;
                case "6": sort(data, StudentComparators.byGpa(), new BubbleSortStrategy<>()); break;
                case "7":
                    EvenOnlySortStrategy<Student> evenSort = new EvenOnlySortStrategy<>(Student::getAge);
                    evenSort.sort(data, StudentComparators.byAge());
                    System.out.println("Отсортированы только чётные по возрасту.");
                    break;
                case "8":
                    if (data != null) {
                        System.out.print("Путь к файлу: ");
                        String path = sc.nextLine();
                        FileWriterUtil.appendToFile(path, data);
                        System.out.println("Данные добавлены в файл.");
                    } else {
                        System.out.println("Сначала заполните массив
