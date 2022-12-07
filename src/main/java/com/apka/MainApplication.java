package com.apka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private Mysql sql;
    private static MainApplication instance;


    private void setupSql() {

        String host = "localhost";
        String username = "root";
        String password = "";
        String port =  "3306";
        String database = "lang";

        boolean ssl = false;
        this.sql = new Mysql(host, username, password, database, port, ssl);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setupSql();
        instance = this;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("logowanie.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Aplikacja do nauki języków");
        stage.setScene(scene);
        stage.show();
    }

    public Mysql getSql() {
        return sql;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch();
    }
}