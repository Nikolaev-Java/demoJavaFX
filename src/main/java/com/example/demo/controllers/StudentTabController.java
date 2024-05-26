package com.example.demo.controllers;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentTabController implements Initializable {

    @FXML
    public AnchorPane studentAnchor;

    private StudentService studentService;

    @FXML
    private TableColumn<Student, Integer> studentCourse;

    @FXML
    private TableColumn<Student, Integer> studentId;

    @FXML
    private TableColumn<Student, String> studentLastname;

    @FXML
    private TableColumn<Student, String> studentName;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private ResultTabController resultTabController;

    private Student student;

    private boolean editFlag;

    public StudentTabController(StudentService studentService) {
        this.studentService = studentService;
    }

    private final ObservableList<Student> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getStudents();
        studentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        studentCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentTable.setItems(data);
    }

    @FXML
    private void onAddStudent(ActionEvent event) {
        newWindow(new Student());
        if (!editFlag) {
            return;
        }
        student.setId(studentService.addStudent(student));
        data.add(student);
    }


    public void getStudents() {
        data.setAll(studentService.getStudents());
    }

    @FXML
    private void onEditStudent(ActionEvent event) {
        TableView.TableViewSelectionModel<Student> sm = studentTable.getSelectionModel();
        if (sm.getSelectedItem() == null) {
            System.out.println("No student selected");
            return;
        }
        Student selectedItem = sm.getSelectedItem();
        newWindow(selectedItem);
        if (!editFlag) {
            return;
        }
        studentService.updateStudent(student);
        getStudents();
    }

    @FXML
    private void onDeleteStudent(ActionEvent event) {
        TableView.TableViewSelectionModel<Student> sm = studentTable.getSelectionModel();
        Student selectedItem = sm.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        studentService.deleteStudent(selectedItem.getId());
        data.remove(selectedItem);
    }

    private void newWindow(Student student) {
        editFlag = false;
        Stage window = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/demo/studentEdit.fxml"));
        window.initModality(Modality.APPLICATION_MODAL);
        try {
            Parent root = fxmlLoader.load();
            StudentEditController studentEditController = fxmlLoader.getController();
            studentEditController.setStudentTabController(this);
            studentEditController.setInitialData(student);
            window.setScene(new Scene(root));
            window.setTitle("Student Edit");
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setStudent(Student student) {
        this.student = student;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }

    @FXML
    private void onAddScoreStudent(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Student> sm = studentTable.getSelectionModel();
        Student selectedItem = sm.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        resultTabController.addResult(selectedItem);
    }
}
