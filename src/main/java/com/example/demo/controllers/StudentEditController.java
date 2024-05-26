package com.example.demo.controllers;

import com.example.demo.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentEditController {

    @FXML
    private TextField studentEditCourse;

    @FXML
    private TextField studentEditLastname;

    @FXML
    private TextField studentEditName;

    private StudentTabController studentTabController;

    private Student student;

    public void setStudentTabController(StudentTabController studentTabController) {
        this.studentTabController = studentTabController;
    }

    public void setInitialData(Student student) {
        this.student = student;
        studentEditName.setText(student.getName());
        studentEditLastname.setText(student.getLastname());
        studentEditCourse.setText(student.getCourseName());
    }

    @FXML
    private void studentEditCancelBtnClicked(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void studentEditOkBtnClicked(ActionEvent event) {
        if (studentEditName.getText() == null
                && studentEditLastname.getText() == null
                && studentEditCourse.getText() == null) {
            return;
        }
        student.setName(studentEditName.getText());
        student.setLastname(studentEditLastname.getText());
        student.setCourseName(studentEditCourse.getText());
        studentTabController.setStudent(student);
        studentTabController.setEditFlag(true);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
