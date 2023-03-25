package com.apka;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogowanieController implements Initializable {

    @FXML public Button przycisk_powrot;
    @FXML public Button przycisk_zaloguj;
    @FXML public TextField pole_login;
    @FXML public PasswordField pole_haslo;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;

    public static String login;
    public String haslo;
    public static int rola;
    public static int id;

    Mysql baza = MainApplication.getInstance().getSql();

    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    public void zalogujAction(ActionEvent actionEvent) throws IOException {
        haslo = pole_haslo.getText();
        login = pole_login.getText();
        id = 0;

        try {
            ResultSet wynik = baza.getResult("SELECT id, rola FROM users WHERE login='" + login + "' AND haslo='" + haslo + "';");
            wynik.next();
            id = wynik.getInt(1);
            System.out.println("ID użytkownika: " + id);
            rola = wynik.getInt(2);
            if (id != 0)
            {
                Parent root = FXMLLoader.load(getClass().getResource("wyborJezyka.fxml"));
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Apka do nauki języków");
                scene.getStylesheets().add("style.css");
                stage.show();
            }

        } catch (SQLException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!!!");
            alert.setHeaderText(null);
            alert.setContentText("Nieprawidłowa nazwa użytkownika lub hasło!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.getStyleClass().add("anchorPane");
        pane.getStyleClass().add("pane");
    }

}
