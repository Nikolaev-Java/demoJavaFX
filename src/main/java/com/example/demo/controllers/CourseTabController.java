package com.example.demo.controllers;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseTabController implements Initializable {

    @FXML
    private TableColumn<Course, String> courseDescription;

    @FXML
    private TableColumn<Course, Integer> courseId;

    @FXML
    private TableColumn<Course, String> courseName;

    @FXML
    private TableView<Course> courseTable;

    private final ObservableList<Course> data = FXCollections.observableArrayList();

    private final CourseService courseService;

    private boolean editFlag;

    private Course course;

    public CourseTabController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCourses();
        courseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        courseDescription.setCellFactory(tc -> {
            TableCell<Course, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(courseDescription.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        courseTable.setItems(data);

    }

    @FXML
    private void onEditCourse(ActionEvent event) {
        TableView.TableViewSelectionModel<Course> selectionModel = courseTable.getSelectionModel();
        Course selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        newWindow(selectedItem);
        if (!editFlag) {
            return;
        }
        courseService.updateCourse(course);
        getCourses();
    }

    @FXML
    private void onDeleteCourse(ActionEvent event) {
        TableView.TableViewSelectionModel<Course> selectionModel = courseTable.getSelectionModel();
        Course selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        courseService.deleteCourse(selectedItem.getId());
        data.remove(selectedItem);
    }

    @FXML
    private void onAddCourse(ActionEvent event) {
        newWindow(new Course());
        if (!editFlag) {
            return;
        }
        course.setId(courseService.addCourse(course));
        data.add(course);
    }

    public void getCourses() {
        data.setAll(courseService.getCourses());
    }

    private void newWindow(Course course) {
        editFlag = false;
        Stage window = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/demo/courseEdit.fxml"));
        window.initModality(Modality.APPLICATION_MODAL);
        try {
            Parent root = fxmlLoader.load();
            CourseEditController courseEditController = fxmlLoader.getController();
            courseEditController.setCourseTabController(this);
            courseEditController.setInitialData(course);
            window.setScene(new Scene(root));
            window.setTitle("Course Edit");
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
}
