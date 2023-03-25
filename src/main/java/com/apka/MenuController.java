package com.apka;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML public Button przycisk_zmiana_jezyka;
    @FXML public Button przycisk_kategorie;
    @FXML public Button przycisk_zestawy;
    @FXML public Button przycisk_wszystkie_slowka;
    @FXML public Button przycisk_zamknij;
    @FXML public Button przycisk_dodaj_slowka;
    @FXML public Label label_wybrany_jezyk;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;
    public static boolean wszystkie=false;


    @FXML
    public void zmienJezykAction(ActionEvent actionEvent) throws IOException {
        wszystkie = false;
        Parent root = FXMLLoader.load(getClass().getResource("wyborJezyka.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void wybierzKategorieAction(ActionEvent actionEvent) throws IOException {
        wszystkie = false;
        Parent root = FXMLLoader.load(getClass().getResource("wyborKategorii.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void wybierzZestawAction(ActionEvent actionEvent) throws IOException {
        wszystkie = false;
        Parent root = FXMLLoader.load(getClass().getResource("wyborZestawu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void wyswietlWszystkieSlowkaAction(ActionEvent actionEvent) throws IOException {
        wszystkie = true;
        Parent root = FXMLLoader.load(getClass().getResource("menu_zestawu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void zamknijAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void dodajSlowkaAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu_dodawanie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.getStyleClass().add("anchorPane");
        pane.getStyleClass().add("pane");
        label_wybrany_jezyk.setText("Wybrany język: " + WyborJezykaController.jezyk);
    }
}
