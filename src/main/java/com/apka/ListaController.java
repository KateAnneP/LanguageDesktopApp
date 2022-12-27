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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
    @FXML public Button przycisk_usun;
    @FXML public TextArea podpowiedz;

    Mysql baza = MainApplication.getInstance().getSql();
    ObservableList<Fiszka> slowka = FXCollections.observableArrayList();

    public int id_kategorii, id_jezyka;
    public static String slowko1, tlumaczenie1; //przesyłane do kontrolera od edycji

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

    @FXML
    public void usunWybraneSlowkoAction(ActionEvent actionEvent) {

        if(!lista_slowka.getSelectionModel().isEmpty())
        {
            String slowko = kolumna_slowko.getCellData(lista_slowka.getSelectionModel().getSelectedItem()).toString();
            String tlumaczenie = kolumna_tlumaczenie.getCellData(lista_slowka.getSelectionModel().getSelectedItem()).toString();
            int indeks = lista_slowka.getSelectionModel().getSelectedIndex();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!!!");
            alert.setHeaderText(null);
            alert.setContentText("Czy na pewno chcesz usunąć ten element?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent()) {}
            else if(result.get() == ButtonType.OK)
            {
                System.out.println("Usunięto: " + slowko + ", pod indeksem: " + indeks);
                //baza.update("DELETE FROM slowka WHERE slowko='"+ slowko +"' AND tlumaczenie='"+ tlumaczenie +"';");
                slowka.remove(indeks);

                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Warning!!!");
                alert1.setHeaderText(null);
                alert1.setContentText("Usunięto wybrany element: " + slowko);
                alert1.showAndWait();
            }
            else if(result.get() == ButtonType.CANCEL) {
                System.out.println("Zaniechano usuwania xD");
            }
        }
        else
        {
            System.out.println("Nie wybrano nic do usunięcia");
            podpowiedz.setWrapText(true);
            podpowiedz.setText("Nie wybrano nic do usunięcia");
            podpowiedz.setVisible(true);
        }

        lista_slowka.refresh();
    }


    @FXML
    public void otworzEdytorAction(ActionEvent actionEvent) throws IOException {
        if(!lista_slowka.getSelectionModel().isEmpty()) {
            slowko1 = kolumna_slowko.getCellData(lista_slowka.getSelectionModel().getSelectedItem()).toString();
            tlumaczenie1 = kolumna_tlumaczenie.getCellData(lista_slowka.getSelectionModel().getSelectedItem()).toString();
            Parent root = FXMLLoader.load(getClass().getResource("edytujSlowko.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Apka do nauki języków");
            scene.getStylesheets().add("style.css");
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!!!");
            alert.setHeaderText(null);
            alert.setContentText("Nie wybrano słówka do edycji");
            alert.showAndWait();
        }
    }

    @FXML
    public void wyswietlPodpowiedzUsuwanieAction(MouseEvent mouseEvent) {
        podpowiedz.setWrapText(true);
        podpowiedz.setText("Aby usunąć słówko z zestawu, kliknij pozycję na liście i zatwierdź wybór przyciskiem");
        podpowiedz.setVisible(true);
    }

    @FXML
    public void wyswietlPodpowiedzEdytowanieAction(MouseEvent mouseEvent) {
        podpowiedz.setWrapText(true);
        podpowiedz.setText("Aby edytować słówko z zestawu, kliknij pozycję na liście i zatwierdź wybór przyciskiem");
        podpowiedz.setVisible(true);
    }

    @FXML
    public void schowajPodpowiedzAction(MouseEvent mouseEvent) {
        podpowiedz.setVisible(false);
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
            ResultSet wynik = baza.getResult("SELECT slowko,tlumaczenie,zestawy.nazwa,zestawy.jezyk FROM slowka JOIN zestawy ON slowka.zestaw=zestawy.id WHERE zestawy.nazwa='" + WyborZestawuController.zestaw + "' AND zestawy.jezyk='"+ id_jezyka +"';");
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
