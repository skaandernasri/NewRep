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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pidev.entities.User;
import pidev.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class UpdateUserController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfville;
    @FXML
    private TextField tfnum_tel;
    @FXML
    private PasswordField pfpassword;
    @FXML
    private PasswordField pfnew_password;
    @FXML
    private Button btvalider;
SignupController signcontroller = new SignupController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifier(ActionEvent event) {
        UserCRUD uc= new UserCRUD();
      User user =  uc.getUserByEmail(signcontroller.getCurrentUser());
      user.setNom(tfnom.getText().toString());
      user.setPrenom(tfprenom.getText().toString());
      user.setEmail(tfemail.getText().toString());
      user.setVille(tfville.getText().toString());
      user.setNum_tel(tfnum_tel.getText().toString());
      user.setPassword(pfnew_password.getText().toString());
      uc.modifierUtilisateur(user);
    }
     public void delete(ActionEvent event){
        UserCRUD uc = new UserCRUD();
        uc.supprimerUtilisateur(signcontroller.getCurrentUser());
            
    }
    
    
    
}
