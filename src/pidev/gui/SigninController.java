/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pidev.entities.User;
import pidev.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class SigninController implements Initializable {
@FXML
    private TextField tflogin;
 @FXML
    private PasswordField pfpassword;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        private void Signin(ActionEvent event) {
            User user = new User();
                  user.setLogin(tflogin.getText());
                  user.setPassword(pfpassword.getText());
                  UserCRUD uc = new UserCRUD();
        if (uc.authentifier(user));
         //   currentUser = new User(user.getLogin(), user.getPassword());
        }
    
}
