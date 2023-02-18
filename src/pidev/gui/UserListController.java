/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import pidev.entities.User;
import pidev.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class UserListController implements Initializable {

    
    @FXML
    private TableView<User> tvliste;
    @FXML
    private TableColumn<User, Integer> ID;
    @FXML
    private TableColumn<User, String> Nom;
    @FXML
    private TableColumn<User, String> Prenom;
    @FXML
    private TableColumn<User, String> Email;
    @FXML
    private TableColumn<User, String> Cin;
    @FXML
    private TableColumn<User, String> date_naiss;
    @FXML
    private TableColumn<User, String> num_permis;
    @FXML
    private TableColumn<User, String> num_tel;
    @FXML
    private TableColumn<User, String> ville;
Stage stage=new Stage();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userList();

    }

    public void userList() {
        UserCRUD uc = new UserCRUD();
        ObservableList<User> users = FXCollections.observableArrayList(uc.consulterListe());
        tvliste.setItems(users);
        ID.setCellValueFactory(cellData ->new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
       Prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        Email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        Cin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCin()));
        date_naiss.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate_naiss()));
        num_permis.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNum_permis()));
        num_tel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNum_tel()));
        ville.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVille()));
    }
 public Stage userListWindow() { try {
            Parent root = FXMLLoader.load(getClass().getResource("UserList.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Liste des utilisateurs");
            stage.setScene(scene);
            stage.show();
             return stage;
        } catch (IOException ex) {
System.out.println(ex.getMessage());
        }
 return stage;
 }
 }
