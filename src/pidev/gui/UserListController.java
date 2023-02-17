/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<User,String> ID;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userList();

    }

    public void userList() {

        UserCRUD uc = new UserCRUD();
        List<User> userList = new ArrayList<>(uc.consulterListe());


        ObservableList<User> users = FXCollections.observableList(userList);
        

    }

}
