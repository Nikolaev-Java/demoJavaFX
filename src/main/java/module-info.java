module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires com.h2database;
    requires jdk.httpserver;

    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controllers to javafx.fxml;
    opens com.example.demo.model to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.controllers;
    exports com.example.demo.service;
    exports com.example.demo.repository;
    exports com.example.demo.model;
}