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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EdytujSlowkoController implements Initializable {

    @FXML public Button przycisk_powrot;
    @FXML public Button przycisk_zamknijAplikacje;
    @FXML public Button przycisk_zatwierdzZmiany;
    @FXML public TextField pole_slowko;
    @FXML public TextField pole_tlumaczenie;
    @FXML public ChoiceBox lista_jezyki;
    @FXML public ChoiceBox lista_kategorie;
    @FXML public ChoiceBox lista_zestawy;
    @FXML public AnchorPane anchorPane;
    @FXML public Pane pane;

    String slowko, tlumaczenie;
    int jezyk, kategoria, zestaw;   //zmienne do których przypisywane są wartości id dot. słówka w bazie danych

    Mysql baza = MainApplication.getInstance().getSql();

    ObservableList<Jezyk> jezyki = FXCollections.observableArrayList();
    ObservableList<Kategoria> kategorie = FXCollections.observableArrayList();
    ObservableList<Zestaw> zestawy = FXCollections.observableArrayList();

    ObservableList<String> nazwaJ = FXCollections.observableArrayList();
    ObservableList<String> nazwaK = FXCollections.observableArrayList();
    ObservableList<String> nazwaZ = FXCollections.observableArrayList();

    @FXML
    public void powrotAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("lista.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Apka do nauki języków");
        scene.getStylesheets().add("style.css");
        stage.show();
    }

    @FXML
    public void zamknijAplikacjeAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    public void zatwierdzZmianyAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!!!");
        alert.setHeaderText(null);
        alert.setContentText("Czy na pewno chcesz zatwierdzić ten element?");
        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) {}
        else if(result.get() == ButtonType.OK)
        {
            edytuj();
        }
        else {
            System.out.println("Anulowano operację edytowania słówka");
        }
    }

    //funkcja pobierająca listy wszystkich języków, kategorii i zestawów dostępnych w bazie danych
    public void pobierzDane()
    {
        String nazwa, jezyk_zestawu;
        int id;

        try
        {
            ResultSet wynikJezyki = baza.getResult("SELECT * FROM jezyki;");
            while(wynikJezyki.next())
            {
                id = wynikJezyki.getInt(1);
                nazwa = wynikJezyki.getString(2);
                jezyki.add(new Jezyk(nazwa, id));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Nie udało się wczytać języków z bazy");
        }

        try
        {
            ResultSet wynikKategorie = baza.getResult("SELECT * FROM kategorie;");
            while(wynikKategorie.next())
            {
                id = wynikKategorie.getInt(1);
                nazwa = wynikKategorie.getString(2);
                kategorie.add(new Kategoria(nazwa, id));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Nie udało się wczytać kategorii z bazy");
        }

        try
        {
            ResultSet wynikZestawy = baza.getResult("SELECT * FROM zestawy WHERE owner = '"+ LogowanieController.id +"';");
            while(wynikZestawy.next())
            {
                id = wynikZestawy.getInt(1);
                nazwa = wynikZestawy.getString(2);
                jezyk_zestawu = wynikZestawy.getString(4);
                zestawy.add(new Zestaw(nazwa, id, jezyk_zestawu));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Nie udało się wczytać zestawów z bazy");
        }
    }

    public void edytuj()
    {
        String nowe_slowko, nowe_tlumaczenie, nowy_jezyk, nowa_kategoria, nowy_zestaw;
        int nowyJ_id = jezyk, nowaK_id = kategoria, nowyZ_id = zestaw;
        nowe_slowko = pole_slowko.getText();
        nowe_tlumaczenie = pole_tlumaczenie.getText();
        nowy_jezyk = lista_jezyki.getSelectionModel().getSelectedItem().toString();
        for (Jezyk j: jezyki)
        {
            if(j.getJezyk().equals(nowy_jezyk))
                nowyJ_id = j.getId_w_bazie();
        }
        nowa_kategoria = lista_kategorie.getSelectionModel().getSelectedItem().toString();
        for (Kategoria k: kategorie)
        {
            if(k.getKategoria().equals(nowa_kategoria))
                nowaK_id = k.getId_w_bazie();
        }
        nowy_zestaw = lista_zestawy.getSelectionModel().getSelectedItem().toString();
        for (Zestaw z: zestawy)
        {
            if((z.getZestaw() + " - " + z.getJezyk()).equals(nowy_zestaw))
                nowyZ_id = z.getId_w_bazie();
        }
        System.out.println("Do update'u: " + nowy_jezyk + "/" + nowa_kategoria + "/" + nowy_zestaw);
        System.out.println((nowyJ_id + "/" + nowaK_id + "/" + nowyZ_id));
        baza.update("UPDATE slowka SET slowko='"+ nowe_slowko +"',tlumaczenie='"+ nowe_tlumaczenie +"',jezyk='"+ nowyJ_id +"',kategoria='"+ nowaK_id +"',zestaw='"+ nowyZ_id +"' WHERE slowko='"+ slowko +"' AND tlumaczenie='"+ tlumaczenie +"' AND jezyk='"+ jezyk +"';");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.getStyleClass().add("anchorPane");
        pane.getStyleClass().add("pane");

        slowko = ListaController.slowko1;
        tlumaczenie = ListaController.tlumaczenie1;

        //wywołanie funkcji pobierającej listy wszystkich języków, kategorii i zestawów dostępnych w bazie danych
        pobierzDane();

        pole_slowko.setText(slowko);
        pole_tlumaczenie.setText(tlumaczenie);

        //pobieranie danych z bazy (id jezyka, kategorii, zestawu) o słówku, które przekazane jest z kontrolera Listy, jako wybrane do edycji
        try
        {
            ResultSet wynik = baza.getResult("SELECT * FROM slowka WHERE slowko='"+ slowko +"' AND tlumaczenie='"+ tlumaczenie +"';");
            wynik.next();
            jezyk = wynik.getInt(4);
            zestaw = wynik.getInt(5);
            kategoria = wynik.getInt(6);
        }
        catch(SQLException e)
        {
            System.out.println("Nie udało się pobrać informacji o słówku");
        }

        //najpierw dla języka, kategorii i zestawu tworzone są podlisty, zawierające tylko nazwy pobrane z głównych tablic (które mają też id)
        //potem te nazwy dodawane są jako opcje choiceboxa
        //potem kolejna pętla sprawdza, czy id danego elementu głównej tablicy zgadza się z id języka/kategorii/zestawu, pobranym przez zapytanie z bazy
        //jeśli się zgadza, to ta opcja ustawiana jest jako defaultowa w choiceboxie
        for (Jezyk j: jezyki) {
            nazwaJ.add(j.getJezyk());
        }
        lista_jezyki.setItems(nazwaJ);
        for (Jezyk j: jezyki) {
            if(j.getId_w_bazie() == jezyk)
                lista_jezyki.setValue(j.getJezyk());
        }

        for (Kategoria k: kategorie) {
            nazwaK.add(k.getKategoria());
        }
        lista_kategorie.setItems(nazwaK);
        for (Kategoria k: kategorie) {
            if(k.getId_w_bazie() == kategoria)
                lista_kategorie.setValue(k.getKategoria());
        }

        for (Zestaw z: zestawy) {
            nazwaZ.add(z.getZestaw() + " - " + z.getJezyk());
        }
        lista_zestawy.getItems().addAll(nazwaZ);
        for (Zestaw z: zestawy) {
            if(z.getId_w_bazie() == zestaw)
                lista_zestawy.setValue(z.getZestaw() + " - " + z.getJezyk());
        }

    }
}
