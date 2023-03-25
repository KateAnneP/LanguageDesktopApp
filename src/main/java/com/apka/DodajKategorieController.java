package com.apka;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DodajKategorieController implements Initializable {
    @FXML public Button przycisk_utworz;
    @FXML public TextField pole_kategoria;
    @FXML public Button przycisk_powrot;
    @FXML public TextField pole_zestaw;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;

    Mysql baza = MainApplication.getInstance().getSql();
    public int jezyk;

    @FXML
    public void utworzAction(ActionEvent actionEvent) throws IOException {
        zapiszKategorieDoBazy();

        Parent root = FXMLLoader.load(getClass().getResource("menu_dodawanie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu_dodawanie.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    public void zapiszKategorieDoBazy()
    {
        try {
            ResultSet wynik = baza.getResult("SELECT id FROM jezyki WHERE jezyk='"+WyborJezykaController.jezyk+ "';");
            wynik.next();
            jezyk = wynik.getInt(1);
        }
        catch(SQLException e) {}

        String kategoria = pole_kategoria.getText();
        String zestaw = pole_zestaw.getText();
        if(kategoria != "")
            baza.update("INSERT INTO kategorie(`nazwa`) VALUES ('"+kategoria+"');");
        if(zestaw != "")
            baza.update("INSERT INTO zestawy(`nazwa`, `owner`, `jezyk`) VALUES ('"+zestaw+"','"+LogowanieController.id+"','"+jezyk+"');");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.getStyleClass().add("anchorPane");
        pane.getStyleClass().add("pane");
    }
}
