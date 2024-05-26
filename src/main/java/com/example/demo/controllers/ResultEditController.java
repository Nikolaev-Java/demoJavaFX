package com.example.demo.controllers;

import com.example.demo.model.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResultEditController {
    @FXML
    public Label studentName;

    @FXML
    public Label studentLastName;

    @FXML
    public TextField studentScore;

    private Result result;

    private ResultTabController resultTabController;

    @FXML
    public void resultEditOkBtnClicked(ActionEvent actionEvent) {
        if (studentScore.getText().isEmpty()) {
            return;
        }
        result.setScore(Integer.parseInt(studentScore.getText()));
        resultTabController.setResult(result);
        resultTabController.setEditFlag(true);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void resultEditCancelBtnClicked(ActionEvent actionEvent) {
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void setResultTabController(ResultTabController resultTabController) {
        this.resultTabController = resultTabController;
    }

    public void setInitialData(Result result) {
        this.result = result;
        studentName.setText(result.getStudent().getName());
        studentLastName.setText(result.getStudent().getLastname());
        studentScore.setText(String.valueOf(result.getScore()));
    }
}
