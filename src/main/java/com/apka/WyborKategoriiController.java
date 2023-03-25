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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WyborKategoriiController implements Initializable {

    @FXML public Button przycisk_wybor;
    @FXML public TableColumn kol_kategoria;
    @FXML public TableView lista_kategorie;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;
    @FXML public Label label_wybrany_jezyk;


    Mysql baza = MainApplication.getInstance().getSql();

    public static String kategoria;
    public static int wybrane1 = 0;

    public void wybierzKategorieAction(ActionEvent actionEvent) throws IOException {
        kategoria = kol_kategoria.getCellData(lista_kategorie.getSelectionModel().getSelectedItem()).toString();
        System.out.println("Wybrana kategoria: " + kategoria);
        wybrane1 = 1;
        WyborZestawuController.wybrane2 = 0;

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
        anchorPane.getStyleClass().add("anchorPane");
        pane.getStyleClass().add("pane");

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

        lista_kategorie.itemsProperty().setValue(kategorie);
        kol_kategoria.setCellValueFactory(new PropertyValueFactory<Kategoria, String>("kategoria"));

        label_wybrany_jezyk.setText("Wybrany język: " + WyborJezykaController.jezyk);
    }
}
