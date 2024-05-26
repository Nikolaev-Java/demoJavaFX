package com.example.demo.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TabPaneController implements Initializable {
    @FXML
    public TabPane tabPane;

    @FXML
    private StudentTabController tabStudentController;

    @FXML
    private CourseTabController tabCourseController;

    @FXML
    private ResultTabController tabResultController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabStudentController.setResultTabController(tabResultController);
    }

    @FXML
    public void updateStudent(Event event) {
        tabStudentController.getStudents();
    }

    public void updateCourses(Event event) {
        tabCourseController.getCourses();
    }

    public void updateResults(Event event) {
        tabResultController.getResults();
    }
}
