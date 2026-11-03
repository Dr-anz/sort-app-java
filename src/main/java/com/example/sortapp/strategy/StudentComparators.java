package com.example.sortapp.strategy;

import com.example.sortapp.model.Student;
import java.util.Comparator;

public class StudentComparators {
    public static Comparator<Student> byId() { return Comparator.comparing(Student::getId); }
    public static Comparator<Student> byAge() { return Comparator.comparingInt(Student::getAge); }
    public static Comparator<Student> byGpa() { return Comparator.comparingDouble(Student::getGpa); }
}
