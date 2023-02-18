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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import pidev.entities.User;
import pidev.services.UserCRUD;
import pidev.utils.UserSession;

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
    Alert.AlertType alertType;
    Stage stage = new Stage();
    @FXML
    private Button btndelete;
//    SigninController signincontroller=new SigninController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         UserCRUD uc = new UserCRUD();
        User user = uc.getUserByEmail(UserSession.getEmail());
            tfnom.setText(user.getNom());   
            tfprenom.setText(user.getPrenom());  
            tfemail.setText(user.getEmail());
            tfville.setText(user.getVille());   
            tfnum_tel.setText(user.getNum_tel());
            pfpassword.setText(user.getPassword());
            pfnew_password.setText(user.getPassword());
    }

    public Stage getUpdateWindowStage() {
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

    @FXML
    private void modifier(ActionEvent event) {
        Window owner = btvalider.getScene().getWindow();
        UserCRUD uc = new UserCRUD();
        User user = uc.getUserByEmail(UserSession.getEmail());
            /*tfnom.setText(user.getNom());   
            tfprenom.setText(user.getPrenom());  
            tfemail.setText(user.getEmail());
            tfville.setText(user.getVille());   
            tfnum_tel.setText(user.getNum_tel());
            pfpassword.setText(user.getPassword());
            pfnew_password.setText(user.getPassword());*/
        if (!(tfnum_tel.getText().chars().allMatch(Character::isDigit)) || tfnum_tel.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de téléphone doit etre composé seulement de 8 chiffres !");
        if (!(pfpassword.getText().equals(pfnew_password.getText()))) {
            showAlert(alertType.ERROR, owner, "Erreur", "Mot de passe incorrect");
        } else {
            user.setNom(tfnom.getText().toString());
            user.setPrenom(tfprenom.getText().toString());  
            user.setEmail(tfemail.getText().toString());
            user.setVille(tfville.getText().toString());
            user.setNum_tel(tfnum_tel.getText().toString());
            user.setPassword(pfnew_password.getText().toString());
            uc.modifierUtilisateur(user);
            UserSession.updateUserSession(user.getEmail(), user.getPassword());
            System.out.println(user);
        }
    }
    }

    @FXML
    private void delete(ActionEvent event) {
        UserCRUD uc = new UserCRUD();
        if (displayModalPopup("Supprimer", "utililsateur supprimer", "utililsateur non supprimer"));
        {
           
            try {
                User user = uc.getUserByEmail(UserSession.getEmail());
                uc.supprimerUtilisateur(user);
                UserSession.cleanUserSession();
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Signup.fxml"));
                Parent root =loader.load();
                SignupController sc=loader.getController();
                sc.signUpWindow();
                Stage stage = (Stage) btndelete.getScene().getWindow();
                stage.close();
            } catch (IOException ex) {
            System.out.println(ex.getMessage());
            }
               
            

        }

    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public boolean displayModalPopup(String message, String yesmessage, String nomessage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;
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
