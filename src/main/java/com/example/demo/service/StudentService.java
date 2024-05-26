package com.example.demo.service;

import com.example.demo.model.Student;

import java.util.List;

public interface StudentService {
    int addStudent(Student student);

    List<Student> getStudents();

    Student getStudent(int id);

    void updateStudent(Student student);

    void deleteStudent(int id);
}
