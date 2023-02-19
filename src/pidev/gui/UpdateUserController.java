/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import javafx.scene.control.Alert;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static pidev.gui.SignupController.showAlert;
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
    @FXML
    private Button btnprofile;
    UserCRUD uc = new UserCRUD();
    User user = new User();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        user = uc.getUserByEmail(UserSession.getEmail());
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

        user = uc.getUserByEmail(UserSession.getEmail());
        /*tfnom.setText(user.getNom());   
            tfprenom.setText(user.getPrenom());  
            tfemail.setText(user.getEmail());
            tfville.setText(user.getVille());   
            tfnum_tel.setText(user.getNum_tel());
            pfpassword.setText(user.getPassword());
            pfnew_password.setText(user.getPassword());*/
        if (tfnom.getText().isEmpty()
                || tfprenom.getText().isEmpty() || tfemail.getText().isEmpty()
                || tfville.getText().isEmpty() || tfnum_tel.getText().isEmpty()
                || tfemail.getText().isEmpty() || (pfpassword.getText().isEmpty() && pfnew_password.getText().isEmpty())) {
            showAlert(alertType.ERROR, owner, "Erreur", "Il reste des champs vides!");
        }
        else if (!(pfpassword.getText().equals(pfnew_password.getText()))) {
            showAlert(alertType.ERROR, owner, "Erreur", "Mot de passe incorrect");
        }else if (pfpassword.getText().length() < 6) {
            showAlert(Alert.AlertType.ERROR, owner, "Echec!",
                    "Le mot de passe doit etre composé de 6 chiffres au minimum");
        } else if (!(tfnum_tel.getText().chars().allMatch(Character::isDigit)) || tfnum_tel.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de téléphone doit etre composé seulement de 8 chiffres !");
        } else {
if ((showVerification("Modifier","Modifier votre compte ?"))) {
            try {
                
                    user.setNom(tfnom.getText().toString());
                    user.setPrenom(tfprenom.getText().toString());
                    user.setEmail(tfemail.getText().toString());
                    user.setVille(tfville.getText().toString());
                    user.setNum_tel(tfnum_tel.getText().toString());
                    user.setPassword(pfnew_password.getText().toString());
                    uc.modifierUtilisateur(user);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                    Parent root = loader.load();
                    ProfileController pc = loader.getController();
                    pc.profileWindow();
                    Stage stage = (Stage) btnprofile.getScene().getWindow();
                    stage.close();
                    UserSession.updateUserSession(user.getEmail(), user.getPassword());
                    System.out.println(user);
                }
             catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
}

        }
    }
    

    @FXML
    private void delete(ActionEvent event) {

        if ((showVerification("Supprimer","Supprimer votre compte ?")))
        {

            try {
                uc.supprimerUtilisateur(user);
                UserSession.cleanUserSession();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
                Parent root = loader.load();
                SignupController sc = loader.getController();
                sc.signUpWindow();
                Stage stage = (Stage) btndelete.getScene().getWindow();
                stage.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        

    }

    @FXML
    public void profileWindow(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));

            Parent root = loader.load();
            ProfileController pc = loader.getController();
            pc.profileWindow();
            Stage stage = (Stage) btnprofile.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


   public static boolean showVerification(String message,String message1) {
        boolean verif = false;
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(message);
        alert.setHeaderText(message1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            verif = true;
        }
        return verif;
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
