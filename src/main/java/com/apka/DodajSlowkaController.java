package com.apka;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DodajSlowkaController implements Initializable {
    @FXML public Button przycisk_dodaj;
    @FXML public Button przycisk_powrot;
    @FXML public TextField pole_slowko;
    @FXML public TextField pole_tlumaczenie;
    @FXML public Label etykieta_sukces;

    Mysql baza = MainApplication.getInstance().getSql();

    public int jezyk, kategoria, zestaw;

    @FXML
    public void dodajAction(ActionEvent actionEvent) {
        String slowko = pole_slowko.getText();
        String tlumaczenie = pole_tlumaczenie.getText();
        pobierzDane();

        baza.update("INSERT INTO slowka (`slowko`,`tlumaczenie`,`jezyk`,`zestaw`,`kategoria`) VALUES ('"+slowko+"','"+tlumaczenie+"','"+jezyk+"','"+zestaw+"','"+kategoria+"');");

        etykieta_sukces.setVisible(true);
        pole_slowko.clear();
        pole_tlumaczenie.clear();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        etykieta_sukces.setVisible(false);

    }

    public void pobierzDane()
    {
        try {
            ResultSet wynik = baza.getResult("SELECT id FROM jezyki WHERE jezyk='"+WyborJezykaController.jezyk+ "';");
            wynik.next();
            jezyk = wynik.getInt(1);
        }
        catch(SQLException e) {}

        try {
            ResultSet wynik = baza.getResult("SELECT id FROM kategorie WHERE nazwa='"+MenuDodawanieController.wybranaKategoria+ "';");
            wynik.next();
            kategoria = wynik.getInt(1);
        }
        catch(SQLException e) {}

        try {
            ResultSet wynik = baza.getResult("SELECT id FROM zestawy WHERE nazwa='"+MenuDodawanieController.wybranyZestaw+ "';");
            wynik.next();
            zestaw = wynik.getInt(1);
        }
        catch(SQLException e) {}
    }

    @FXML
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu_dodawanie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
