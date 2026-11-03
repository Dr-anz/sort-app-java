package com.example.sortapp.io;

import com.example.sortapp.model.Student;

public class CustomList {
    private Student[] data;
    private int size;

    public CustomList(int capacity) {
        data = new Student[capacity];
        size = 0;
    }

    public void add(Student s) {
        if (size >= data.length) {
            Student[] newData = new Student[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        data[size++] = s;
    }

    public Student[] toArray() {
        Student[] result = new Student[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    public int size() { return size; }
}
