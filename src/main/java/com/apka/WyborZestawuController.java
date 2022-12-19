package com.apka;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class WyborZestawuController implements Initializable {

    public Button przycisk_wybor;
    public TableView lista_zestawy;
    public TableColumn kol_zestaw;

    Mysql baza = MainApplication.getInstance().getSql();

    public static String zestaw;
    public static int wybrane2 = 0;

    public void wybierzZestawAction(ActionEvent actionEvent) throws IOException {
        zestaw = kol_zestaw.getCellData(lista_zestawy.getSelectionModel().getSelectedItem()).toString();
        System.out.println("Wybrany zestaw: " + zestaw);
        wybrane2 = 2;
        WyborKategoriiController.wybrane1 = 0;

        Parent root = FXMLLoader.load(getClass().getResource("menu_zestawu.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Jezyk> zestawy = FXCollections.observableArrayList();
        String z;

        try
        {
            ResultSet wynik = baza.getResult("SELECT zestawy.nazwa,owner,jezyki.jezyk FROM zestawy JOIN jezyki ON zestawy.jezyk=jezyki.id WHERE jezyki.jezyk='"+WyborJezykaController.jezyk+"' AND owner="+ LogowanieController.id+";");
            while(wynik.next())
            {
                z = wynik.getString(1);
                zestawy.add(new Jezyk(z));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się wczytać listy kategorii");
        }

        lista_zestawy.itemsProperty().setValue(zestawy);
        kol_zestaw.setCellValueFactory(new PropertyValueFactory<Jezyk, String>("jezyk"));
    }
}
