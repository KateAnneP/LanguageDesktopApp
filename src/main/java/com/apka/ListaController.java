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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListaController implements Initializable {

    @FXML public Button przycisk_zamknij;
    @FXML public Button przycisk_zmien_zestaw;
    @FXML public Button przycisk_zmien_jezyk;
    @FXML public Button przycisk_nastepny;
    @FXML public Button przycisk_poprzedni;
    @FXML public Button przycisk_powrot;
    @FXML public TableColumn kolumna_slowko;
    @FXML public TableColumn kolumna_tlumaczenie;
    @FXML public TableView lista_slowka;

    Mysql baza = MainApplication.getInstance().getSql();
    ObservableList<Fiszka> slowka = FXCollections.observableArrayList();

    public int id_kategorii, id_jezyka;

    @FXML
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("wyborKategorii.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        stage.show();
    }

    @FXML
    public void zmienJezykAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("wyborJezyka.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        stage.show();
    }

    @FXML
    public void zmienZestawAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        stage.show();
    }

    public void zamknijAction() {
        Platform.exit();
    }

    @FXML
    public void wczytajPoprzedniAction(ActionEvent actionEvent) throws IOException {
        wczytajPoprzedniZestaw();
        lista_slowka.refresh(); //albo lista_slowka.itemsProperty().setValue(slowka);
    }

    @FXML
    public void wczytajNastepnyAction(ActionEvent actionEvent) throws IOException {
        wczytajNastepnyZestaw();
        lista_slowka.itemsProperty().setValue(slowka); //albo lista_slowka.refresh();
    }

    public void wczytajNastepnyZestaw()
    {
        id_kategorii++;
        String nazwa, slowko, tlumaczenie;
        slowka.clear();
        try {
            ResultSet wynik = baza.getResult("SELECT nazwa FROM kategorie WHERE id="+id_kategorii+";");
            wynik.next();
            nazwa = wynik.getString(1);
            try {
                ResultSet wynik2 = baza.getResult("SELECT slowko,tlumaczenie,kategorie.nazwa,jezyk FROM slowka JOIN kategorie ON slowka.kategoria=kategorie.id WHERE kategorie.nazwa='" + nazwa + "' AND jezyk='"+ id_jezyka +"';");
                while (wynik2.next()) {
                    slowko = wynik2.getString(1);
                    tlumaczenie = wynik2.getString(2);
                    slowka.add(new Fiszka(slowko, tlumaczenie));
                }
            } catch (SQLException e) {}
        }
        catch (SQLException e){}
    }

    public void wczytajPoprzedniZestaw()
    {
        id_kategorii--;
        String nazwa, slowko, tlumaczenie;
        slowka.clear();
        try {
            ResultSet wynik = baza.getResult("SELECT nazwa FROM kategorie WHERE id="+id_kategorii+";");
            wynik.next();
            nazwa = wynik.getString(1);
            try {
                ResultSet wynik2 = baza.getResult("SELECT slowko,tlumaczenie,kategorie.nazwa,jezyk FROM slowka JOIN kategorie ON slowka.kategoria=kategorie.id WHERE kategorie.nazwa='" + nazwa + "' AND jezyk='"+ id_jezyka +"';");
                while (wynik2.next()) {
                    slowko = wynik2.getString(1);
                    tlumaczenie = wynik2.getString(2);
                    slowka.add(new Fiszka(slowko, tlumaczenie));
                }
            } catch (SQLException e) {}
        }
        catch (SQLException e){}
    }

    public void wczytajZestaw()
    {
        String slowko, tlumaczenie;
        try {
            ResultSet wynik = baza.getResult("SELECT slowko,tlumaczenie,zestawy.nazwa,jezyk FROM slowka JOIN zestawy ON slowka.zestaw=zestawy.id WHERE zestawy.nazwa='" + WyborZestawuController.zestaw + "' AND jezyk='"+ id_jezyka +"';");
            while (wynik.next()) {
                slowko = wynik.getString(1);
                tlumaczenie = wynik.getString(2);
                slowka.add(new Fiszka(slowko, tlumaczenie));
            }
        } catch (SQLException e) {}
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
        } catch (SQLException e) {}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet wynik = baza.getResult("SELECT id FROM kategorie WHERE kategorie.nazwa='" + WyborKategoriiController.kategoria + "';");
            wynik.next();
            id_kategorii = wynik.getInt(1);
        }
        catch (SQLException e) {
        }

        try {
            ResultSet wynik = baza.getResult("SELECT id FROM jezyki WHERE jezyk='" + WyborJezykaController.jezyk + "';");
            wynik.next();
            id_jezyka = wynik.getInt(1);
        }
        catch (SQLException e) {
        }

        przycisk_nastepny.setVisible(true);
        przycisk_poprzedni.setVisible(true);

        if(WyborZestawuController.wybrane2 == 2) {
            wczytajZestaw();
        }
        else if(MenuController.wszystkie == true)
        {
            wczytajWszystkie();
            przycisk_nastepny.setVisible(false);
            przycisk_poprzedni.setVisible(false);
        }
        else {
            wczytajKategorie();
        }

        lista_slowka.itemsProperty().setValue(slowka);
        kolumna_slowko.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("slowko"));
        kolumna_tlumaczenie.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("tlumaczenie"));
    }
}
