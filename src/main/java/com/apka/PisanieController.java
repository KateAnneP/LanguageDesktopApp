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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class PisanieController implements Initializable {

    @FXML public Button przycisk_zatwierdz;
    @FXML public Label etykieta_jezyk2;
    @FXML public TextField pole_jezyk2;
    @FXML public TextField pole_jezyk1;
    @FXML public Label etykieta_jezyk1;
    @FXML public Button przycisk_zmien_jezyk;
    @FXML public Button przycisk_zmien_zestaw;
    @FXML public Button przycisk_zamknij;
    @FXML public Button przycisk_odp;
    @FXML public Button przycisk_nastepny;
    @FXML public Label etykieta_odliczanie;

    ObservableList<Fiszka> slowka = FXCollections.observableArrayList();

    Mysql baza = MainApplication.getInstance().getSql();

    public int i, id_kategorii, id_jezyka;
    public int fool_count = 0;
    public Optional<ButtonType> result;

    @FXML
    private void zamknijAction(ActionEvent event) {
        Platform.exit();
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
    public void zatwierdzAction(ActionEvent actionEvent) throws IOException {
        zatwierdzAction();
    }
    @FXML
    public void zatwierdzAction() {
        boolean dobrze = sprawdzOdpowiedz(i);
        if(dobrze)
        {
            i++;
            fool_count = 0;
            if(i < slowka.size()) {

                pole_jezyk2.clear();
                pole_jezyk1.setText(slowka.get(i).getTlumaczenie());
                etykieta_odliczanie.setText((slowka.indexOf(slowka.get(i))+1) + "/" + slowka.size());
                if (przycisk_odp.isVisible()) {
                    przycisk_odp.visibleProperty().setValue(false);
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Congrats!!!");
                alert.setHeaderText("Ukończono powtarzanie zestawu!!!");
                //alert.setContentText("Czy chcesz przejść do menu zestawów?");

                result = alert.showAndWait();
                przycisk_nastepny.setVisible(true);
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!!!");
            alert.setHeaderText(null);
            alert.setContentText("Nieprawidłowa odpowiedź!");
            alert.show();
            fool_count++;

            if(fool_count >= 2)
            {
                //przycisk_odp.visibleProperty().setValue(true);
                przycisk_odp.setVisible(true);
            }
        }

    }

    public void pokazOdpowiedzAction()
    {
        pole_jezyk2.setText(slowka.get(i).getSlowko());
        slowka.add(slowka.get(i));
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
        Collections.shuffle(slowka);
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
        Collections.shuffle(slowka);
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
        Collections.shuffle(slowka);
//        for (int i = 0; i < slowka.toArray().length; i++)
//        {
//            System.out.println(slowka.get(i));
//        }
    }

    public void wczytajNastepnyZestaw()
    {
        id_kategorii++;
        String nazwa, slowko, tlumaczenie;

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
        Collections.shuffle(slowka);
    }


    public void wczytajNastepnyAction(ActionEvent actionEvent) throws IOException {
        slowka.clear();
        pole_jezyk2.clear();
        wczytajNastepnyZestaw();
        i=0;
        pole_jezyk1.setText(slowka.get(i).getTlumaczenie());
        etykieta_odliczanie.setText(slowka.indexOf(slowka.get(i+1))+ "/" + slowka.size());
        przycisk_nastepny.setVisible(false);
    }

    public boolean sprawdzOdpowiedz(int i)
    {
        boolean dobrze = false;
        String odpowiedz;

        odpowiedz = pole_jezyk2.getText();

        if(odpowiedz.equals(slowka.get(i).getSlowko()))
        {
            dobrze = true;
        }
        else {
            dobrze = false;
        }

        return dobrze;
    }

    public void onEnter(KeyEvent keyEvent)
    {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            zatwierdzAction();
        }
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
        pole_jezyk1.setText(slowka.get(i).getTlumaczenie());
        etykieta_odliczanie.setText(slowka.indexOf(slowka.get(i+1))+ "/" + slowka.size());

        etykieta_jezyk1.setText("polski: ");
        etykieta_jezyk2.setText(WyborJezykaController.jezyk+": ");
    }

}