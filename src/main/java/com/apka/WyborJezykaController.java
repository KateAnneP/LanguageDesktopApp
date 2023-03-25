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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

public class WyborJezykaController implements Initializable {

    @FXML public Button przycisk_wybor;
    @FXML public TableView lista_jezyki;
    @FXML public TableColumn kol_jezyki;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;

    Mysql baza = MainApplication.getInstance().getSql();

    public static String jezyk;

    public void wybierzJezykAction(ActionEvent actionEvent) throws IOException {
        jezyk = kol_jezyki.getCellData(lista_jezyki.getSelectionModel().getSelectedItem()).toString();
        System.out.println("Wybrany język: " + jezyk);

        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
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

        ObservableList<Jezyk> jezyki = FXCollections.observableArrayList();
        String j;
        int id;

        try
        {
            ResultSet wynik = baza.getResult("SELECT * FROM jezyki;");
            while(wynik.next())
            {
                id = wynik.getInt(1);
                j = wynik.getString(2);
                jezyki.add(new Jezyk(j, id));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Nie udało się wczytać listy języków");
        }

        lista_jezyki.itemsProperty().setValue(jezyki);
        kol_jezyki.setCellValueFactory(new PropertyValueFactory<Jezyk, String>("jezyk"));
    }
}
