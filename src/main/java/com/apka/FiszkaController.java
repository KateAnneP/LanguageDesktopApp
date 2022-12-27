package com.apka;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FiszkaController implements Initializable {
    @FXML public TextField pole_fiszka;
    @FXML public Button przycisk_wstecz;
    @FXML public Button przycisk_dalej;
    @FXML public Button przycisk_zmien_jezyk;
    @FXML public Button przycisk_zmien_zestaw;
    @FXML public Button przycisk_powrot;

    Mysql baza = MainApplication.getInstance().getSql();

    public int i, k=1, id_jezyka;

    ObservableList<Fiszka> slowka = FXCollections.observableArrayList();

    @FXML
    public void zamknijAction(ActionEvent actionEvent) {Platform.exit();}

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
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu_zestawu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void zmienFiszkeAction(MouseEvent mouseEvent) {
        if(k==1) {
            pole_fiszka.setText(slowka.get(i).getTlumaczenie());
            k=2;
        }
        else {
            pole_fiszka.setText(slowka.get(i).getSlowko());
            k=1;
        }
    }

    @FXML
    public void wsteczAction(ActionEvent actionEvent) {
        if(i>0)
        {
            i--;
            pole_fiszka.setText(slowka.get(i).getSlowko());
        }
    }

    @FXML
    public void dalejAction(ActionEvent actionEvent) {
        try{
            i++;
            pole_fiszka.setText(slowka.get(i).getSlowko());
        } catch (IndexOutOfBoundsException e)
        {}

    }

    public void wczytajWszystkie()
    {
        String slowko, tlumaczenie;
        try {
            ResultSet wynik = baza.getResult("SELECT slowko,tlumaczenie,jezyki.jezyk FROM slowka JOIN jezyki ON slowka.jezyk = jezyki.id WHERE jezyki.jezyk='" + WyborJezykaController.jezyk + "';");
            while (wynik.next()) {
                slowko = wynik.getString(1);
                tlumaczenie = wynik.getString(2);
                slowka.add(new Fiszka(slowko, tlumaczenie));
            }
        } catch (SQLException e) {}
    }

    public void wczytajKategorie()
    {
        String slowko, tlumaczenie;

        try {
            ResultSet wynik = baza.getResult("SELECT slowko,tlumaczenie,kategorie.nazwa,jezyk FROM slowka JOIN kategorie ON slowka.kategoria=kategorie.id WHERE kategorie.nazwa='" + WyborKategoriiController.kategoria + "' AND jezyk='"+ id_jezyka +"';");
            while (wynik.next()) {
                slowko = wynik.getString(1);
                tlumaczenie = wynik.getString(2);
                slowka.add(new Fiszka(slowko, tlumaczenie));
            }
        } catch (SQLException e) {

        }
    }

    public void wczytajZestaw()
    {
        String slowko, tlumaczenie;

        try {
            ResultSet wynik = baza.getResult("SELECT slowko,tlumaczenie,zestawy.nazwa,zestawy.jezyk FROM slowka JOIN zestawy ON slowka.zestaw=zestawy.id WHERE zestawy.nazwa='" + WyborZestawuController.zestaw + "' AND zestawy.jezyk='"+ id_jezyka +"';");
            while (wynik.next()) {
                slowko = wynik.getString(1);
                tlumaczenie = wynik.getString(2);
                slowka.add(new Fiszka(slowko, tlumaczenie));
            }
        } catch (SQLException e) {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet wynik = baza.getResult("SELECT id FROM jezyki WHERE jezyk='" + WyborJezykaController.jezyk + "';");
            wynik.next();
            id_jezyka = wynik.getInt(1);
        }
        catch (SQLException e) {
        }

        if(WyborZestawuController.wybrane2 == 2) {
            wczytajZestaw();
        }
        else if(MenuController.wszystkie == true)
        {
            wczytajWszystkie();
        }
        else {
            wczytajKategorie();
        }

        i=0;
        pole_fiszka.setText(slowka.get(i).getSlowko());
    }
}
