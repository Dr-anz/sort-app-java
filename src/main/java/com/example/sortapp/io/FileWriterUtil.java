package com.example.sortapp.io;

import com.example.sortapp.model.Student;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtil {
    public static void appendToFile(String path, Student[] students) throws IOException {
        try (FileWriter fw = new FileWriter(path, true)) { // true = append
            for (Student s : students) {
                fw.write(s.toString() + System.lineSeparator());
            }
        }
    }
}
