package com.example.demo.controllers;

import com.example.demo.model.Result;
import com.example.demo.model.Student;
import com.example.demo.service.ResultService;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultTabController implements Initializable {

    @FXML
    public TableView<Result> resultTable;

    @FXML
    private TableColumn<Result, String> resultCourse;

    @FXML
    private TableColumn<Result, Integer> resultScore;

    @FXML
    private TableColumn<Result, String> resultStudent;

    private final ResultService resultService;

    private final ObservableList<Result> data = FXCollections.observableArrayList();

    private boolean editFlag;

    private Result result;

    public ResultTabController(ResultService resultService) {
        this.resultService = resultService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getResults();
        resultStudent.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getStudent().getName() +
                        " " + cell.getValue().getStudent().getLastname()));
        resultScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        resultCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        resultTable.setItems(data);
    }


    public void addResult(Student student) {
        newWindow(new Result(student));
        if (!editFlag) {
            return;
        }
        resultService.addScore(result.getStudent(), result.getScore());
        data.add(result);
    }

    @FXML
    private void onDeleteResult(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Result> selectionModel = resultTable.getSelectionModel();
        Result selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        resultService.deleteScore(selectedItem.getStudent());
        data.remove(selectedItem);
    }

    @FXML
    private void onEditResult(ActionEvent actionEvent) {
        TableView.TableViewSelectionModel<Result> selectionModel = resultTable.getSelectionModel();
        Result selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        newWindow(selectedItem);
        if (!editFlag) {
            return;
        }
        resultService.updateScore(result.getStudent(), result.getScore());
        getResults();
    }

    public void getResults() {
        data.setAll(resultService.getResults());
    }

    public void newWindow(Result result) {
        editFlag = false;
        Stage window = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/demo/resultEdit.fxml"));
        window.initModality(Modality.APPLICATION_MODAL);
        try {
            Parent root = fxmlLoader.load();
            ResultEditController resultEditController = fxmlLoader.getController();
            resultEditController.setResultTabController(this);
            resultEditController.setInitialData(result);
            window.setScene(new Scene(root));
            window.setTitle("Result Edit");
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
