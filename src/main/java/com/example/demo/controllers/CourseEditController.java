package com.example.demo.controllers;

import com.example.demo.model.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CourseEditController {
    @FXML
    public TextField courseEditName;
    @FXML
    public TextArea courseEditDescription;

    private CourseTabController courseTabController;

    private Course course;

    @FXML
    public void courseEditOkBtnClicked(ActionEvent actionEvent) {
        if (courseEditName.getText() == null && courseEditDescription.getText() == null) {
            return;
        }
        course.setName(courseEditName.getText());
        course.setDescription(courseEditDescription.getText());
        courseTabController.setCourse(course);
        courseTabController.setEditFlag(true);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void courseEditCancelBtnClicked(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void setCourseTabController(CourseTabController courseTabController) {
        this.courseTabController = courseTabController;
    }

    public void setInitialData(Course course) {
        this.course = course;
        courseEditName.setText(course.getName());
        courseEditDescription.setText(course.getDescription());
    }
}
