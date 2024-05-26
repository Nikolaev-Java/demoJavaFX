package com.example.demo.service;

import javafx.util.Callback;

import java.lang.reflect.Constructor;

public class DependencyInjection implements Callback<Class<?>, Object> {
    private StudentService studentService;
    private CourseService courseService;
    private ResultService resultService;

    public DependencyInjection(StudentService studentService, CourseService courseService, ResultService resultService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.resultService = resultService;
    }


    @Override
    public Object call(Class<?> aClass) {
        try {
            for (Constructor<?> constructor : aClass.getConstructors()) {
                if (constructor.getParameterCount() == 1) {
                    if (constructor.getParameterTypes()[0] == StudentService.class) {
                        return constructor.newInstance(studentService);
                    } else if (constructor.getParameterTypes()[0] == CourseService.class) {
                        return constructor.newInstance(courseService);
                    } else if (constructor.getParameterTypes()[0] == ResultService.class) {
                        return constructor.newInstance(resultService);
                    }
                }
            }
            return aClass.newInstance();
        } catch (Exception e) {

        }
        return null;
    }
}
