package com.example.demo.model;

public class Result {
    private String course;
    private Student student;
    private int score = 0;

    public Result(Student student) {
        this.student = student;
    }

    public Result(String course, Student student, int score) {
        this.course = course;
        this.student = student;
        this.score = score;
    }

    public String getCourse() {
        return course;
    }

    public Student getStudent() {
        return student;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
