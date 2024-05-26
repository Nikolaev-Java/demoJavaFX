package com.example.demo;

import com.example.demo.service.DependencyInjection;
import com.example.demo.service.InDataBaseCourseService;
import com.example.demo.service.InDataBaseResultService;
import com.example.demo.service.InDataBaseStudentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DemoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("tabpane.fxml"));
        DependencyInjection dependencyInjection = new DependencyInjection(new InDataBaseStudentService(),
                new InDataBaseCourseService(),
                new InDataBaseResultService());
        fxmlLoader.setControllerFactory(dependencyInjection);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Demo Application!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}