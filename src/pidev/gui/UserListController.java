/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
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
    private ListView<User> lvliste;

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
        lvliste.setItems(users);

    }

}
