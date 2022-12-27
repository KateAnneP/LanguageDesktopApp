package com.apka;

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

public class MenuDodawanieController implements Initializable {
    @FXML public TableView tabela_kategorie;
    @FXML public TableColumn kolumna_kategoria;
    @FXML public TableView tabela_zestawy;
    @FXML public TableColumn kolumna_zestawy;
    @FXML public Button przycisk_powrot;
    @FXML public Button przycisk_zatwierdz;
    @FXML public Button przycisk_nowa_kategoria;

    public static String wybranyZestaw, wybranaKategoria;
    Mysql baza = MainApplication.getInstance().getSql();

    @FXML
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void zatwierdzAction(ActionEvent actionEvent) throws IOException {
        wybranaKategoria = kolumna_kategoria.getCellData(tabela_kategorie.getSelectionModel().getSelectedItem()).toString();
        wybranyZestaw = kolumna_zestawy.getCellData(tabela_zestawy.getSelectionModel().getSelectedItem()).toString();
        System.out.println("Wybrana kategoria: "+ wybranaKategoria);
        System.out.println("Wybrany zestaw: "+ wybranyZestaw);

        Parent root = FXMLLoader.load(getClass().getResource("dodajSlowka.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void dodajNowaKategorieAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dodajKategorie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    public void wczytajKategorie()
    {
        ObservableList<Kategoria> kategorie = FXCollections.observableArrayList();
        String k;
        int id;

        try
        {
            ResultSet wynik = baza.getResult("SELECT * FROM kategorie;");
            while(wynik.next())
            {
                id = wynik.getInt(1);
                k = wynik.getString(2);
                kategorie.add(new Kategoria(k,id));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się wczytać listy kategorii");
        }

        tabela_kategorie.itemsProperty().setValue(kategorie);
        kolumna_kategoria.setCellValueFactory(new PropertyValueFactory<Kategoria, String>("kategoria"));
    }

    public void wczytajZestawy()
    {
        ObservableList<Zestaw> zestawy = FXCollections.observableArrayList();
        String z,jezyk_zestawu;
        int id;

        try
        {
            ResultSet wynik = baza.getResult("SELECT zestawy.nazwa,owner,jezyki.jezyk,zestawy.id FROM zestawy JOIN jezyki ON zestawy.jezyk=jezyki.id WHERE jezyki.jezyk='"+WyborJezykaController.jezyk+"' AND owner="+ LogowanieController.id+";");
            while(wynik.next())
            {
                id = wynik.getInt(4);
                z = wynik.getString(1);
                jezyk_zestawu = wynik.getString(3);
                zestawy.add(new Zestaw(z,id,jezyk_zestawu));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się wczytać listy zestawów");
        }

        tabela_zestawy.itemsProperty().setValue(zestawy);
        kolumna_zestawy.setCellValueFactory(new PropertyValueFactory<Zestaw, String>("zestaw"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wczytajKategorie();
        wczytajZestawy();
    }
}
