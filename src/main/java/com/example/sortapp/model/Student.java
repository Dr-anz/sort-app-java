package com.example.sortapp.model;

import java.util.Objects;

public class Student {
    private final String id;
    private final int age;
    private final double gpa;

    private Student(Builder builder) {
        this.id = builder.id;
        this.age = builder.age;
        this.gpa = builder.gpa;
    }

    public String getId() { return id; }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return age == s.age && Double.compare(s.gpa, gpa) == 0 && Objects.equals(id, s.id);
    }
    @Override public int hashCode() { return Objects.hash(id, age, gpa); }
    @Override public String toString() {
        return "Student{id='" + id + "', age=" + age + ", gpa=" + gpa + '}';
    }

    public static class Builder {
        private String id;
        private int age;
        private double gpa;

        public Builder id(String id) {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("ID не может быть пустым");
            this.id = id;
            return this;
        }
        public Builder age(int age) {
            if (age < 0 || age > 120) throw new IllegalArgumentException("Возраст должен быть от 0 до 120");
            this.age = age;
            return this;
        }
        public Builder gpa(double gpa) {
            if (gpa < 0.0 || gpa > 4.0) throw new IllegalArgumentException("GPA должен быть от 0.0 до 4.0");
            this.gpa = gpa;
            return this;
        }
        public Student build() { return new Student(this); }
    }
}
