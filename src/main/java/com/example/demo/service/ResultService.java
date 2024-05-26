package com.example.demo.service;

import com.example.demo.model.Result;
import com.example.demo.model.Student;

import java.util.List;

public interface ResultService {
    void addScore(Student student, int score);

    void deleteScore(Student student);

    void updateScore(Student student, int score);

    List<Result> getResults();

    Result getResultByStudent(Student student);
}
