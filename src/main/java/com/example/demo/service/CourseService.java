package com.example.demo.service;

import com.example.demo.model.Course;

import java.util.List;

public interface CourseService {
    int addCourse(Course course);

    List<Course> getCourses();

    Course getCourse(int id);

    void updateCourse(Course course);

    void deleteCourse(int id);

}
