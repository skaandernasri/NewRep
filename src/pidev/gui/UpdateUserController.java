/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
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
private User currentUser;
Alert.AlertType  alertType;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifier(ActionEvent event) {
        Window owner= btvalider.getScene().getWindow();
        
        UserCRUD uc= new UserCRUD();
      User user =  uc.getUserByEmail(getCurrentUser());
     if (tfnom.getText().isEmpty())
      tfnom.setText(user.getNom());
     if(tfprenom.getText().isEmpty())
         tfnom.setText(user.getNom());
     if(tfemail.getText().isEmpty())
         tfemail.setText(user.getEmail());
     if(tfville.getText().isEmpty())
         tfville.setText(user.getVille());
     if(tfnum_tel.getText().isEmpty())
         tfnum_tel.setText(user.getNum_tel());
     if(pfpassword.getText().isEmpty()&&pfnew_password.getText().isEmpty()){
         pfpassword.setText(user.getPassword());
         pfnew_password.setText(user.getPassword());
     }
     if(!(pfpassword.getText().equals(pfnew_password.getText())))
         showAlert(alertType.ERROR,owner,"Erreur","Mot passe incorrect");
    else {
      user.setNom(tfnom.getText().toString());
      user.setPrenom(tfprenom.getText().toString());
      user.setEmail(tfemail.getText().toString());
      user.setVille(tfville.getText().toString());
      user.setNum_tel(tfnum_tel.getText().toString());
      user.setPassword(pfnew_password.getText().toString());
      uc.modifierUtilisateur(user);
    }
    }
     public void delete(ActionEvent event){
        UserCRUD uc = new UserCRUD();
        if(displayModalPopup("modifier", "utililsateur supprimer", "utililsateur non supprimer"));
        uc.supprimerUtilisateur(getCurrentUser());
            
    }
     private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
public User getCurrentUser(){
    return this.currentUser;
}
public boolean displayModalPopup(String message, String yesmessage, String nomessage) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Supprimer");
	alert.setContentText("");
        

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK)
		return true;
	return false;
}
    
     public void setCurrentUser(User currentUser) {
        this.currentUser=currentUser;
    }
     /*private Stage updateWindowStage(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UpdateUser.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Modifier");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return stage;
    }
      public void updateWindow(Stage stage) {
        updateWindowStage(stage);
    }*/
     
    
}
