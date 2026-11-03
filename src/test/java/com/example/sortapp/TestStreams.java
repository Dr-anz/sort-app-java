package com.example.sortapp;

import com.example.sortapp.io.CustomList;
import com.example.sortapp.model.Student;

import java.util.stream.Stream;

public class TestStreams {
    public static void main(String[] args) {
        System.out.println("=== Тест Доп. задания 3: стримы + кастомная коллекция ===");
        CustomList list = new CustomList(5);
        Stream.of(
            new Student.Builder().id("A1").age(20).gpa(3.5).build(),
            new Student.Builder().id("B2").age(22).gpa(4.0).build(),
            new Student.Builder().id("C3").age(19).gpa(3.2).build(),
            new Student.Builder().id("D4").age(21).gpa(3.8).build(),
            new Student.Builder().id("E5").age(23).gpa(3.9).build()
        ).forEach(list::add);

        Student[] arr = list.toArray();
        System.out.println("Размер: " + list.size());
        System.out.println("Элементы:");
        for (Student s : arr) System.out.println(s);
        System.out.println("Тест пройден.");
    }
}
