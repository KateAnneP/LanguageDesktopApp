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

public class MenuZestawuController implements Initializable {

    @FXML public Button przycisk_fiszki;
    @FXML public Button przycisk_pisanie;
    @FXML public Button przycisk_lista;
    @FXML public Button przycisk_zamknij;
    @FXML public Button przycisk_zmien_jezyk;
    @FXML public Button przycisk_zmien_zestaw;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;
    @FXML public Label label_wybrany_jezyk;
    @FXML public Label label_wybrany_zestaw;

    @FXML
    public void wyswietlFiszkiAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fiszka.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void wyswietlPisanieAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("pisanie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void wyswietlListeAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("lista.fxml"));
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
    public void zmienJezykAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("wyborJezyka.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void zmienZestawAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
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
        if(WyborZestawuController.wybrane2 == 2) {
            label_wybrany_zestaw.setText("Wybrany zestaw: " + WyborZestawuController.zestaw);
        }
        else if(MenuController.wszystkie == true)
        {
            label_wybrany_zestaw.setText("Wybrany zestaw: wszystkie słówka");
        }
        else {
            label_wybrany_zestaw.setText("Wybrana kategoria: " + WyborKategoriiController.kategoria);
        }

    }
}
