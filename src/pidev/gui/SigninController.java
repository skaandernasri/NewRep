/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import pidev.entities.User;
import pidev.entities.User.Role;
import static pidev.gui.SignupController.showAlert;
import pidev.services.UserCRUD;
import pidev.utils.UserSession;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class SigninController implements Initializable {

    @FXML
    private TextField tfemailtoconnect;
    @FXML
    private PasswordField pfpasswordtoconnect;
    @FXML
    private Button btnsignin;
    User user = new User();
    UpdateUserController updateusercontroller = new UpdateUserController();
    @FXML
    private Button btndejacompte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Signin(ActionEvent event) {
        Window owner = btnsignin.getScene().getWindow();
        User user = new User();
        user.setEmail(tfemailtoconnect.getText());
        user.setPassword(pfpasswordtoconnect.getText());
        UserCRUD uc = new UserCRUD();
        if (tfemailtoconnect.getText().isEmpty() || pfpasswordtoconnect.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "il reste des champs vides");
            return;
        } else if (!(validateEmail(tfemailtoconnect))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Vérifier l'email");
            return;
        } else if (uc.authentifier(user)) {
            // UserSession.getInstace(user.getEmail(), user.getPassword());
               if (uc.getUserByEmail(tfemailtoconnect.getText()).getRole().equals(Role.valueOf("CLIENT"))){
            try {
                   UserSession.getInstace(user.getEmail(), user.getPassword());
                //setCurrentUser(uc.getUserByEmail(user));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                Parent root = loader.load();
                ProfileController pc = loader.getController();
                pc.profileWindow();
              
                Stage stage = (Stage) btnsignin.getScene().getWindow();
                stage.close();
                //uuc.setCurrentUser(getCurrentUser());

                // updateusercontroller.setCurrentUser(getCurrentUser());
                // updateusercontroller.getUpdateWindowStage();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
               }
               else if(uc.getUserByEmail(tfemailtoconnect.getText()).getRole().equals(Role.valueOf("ADMIN"))) {
                 try {
                     UserSession.getInstace(user.getEmail(), user.getPassword());
                     FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                     Parent root = loader.load();
                     ProfileController pc = loader.getController();
                     pc.profileWindow();
                     Stage stage = (Stage) btnsignin.getScene().getWindow();
                     stage.close();
                 } catch (IOException ex) {
                System.out.println(ex.getMessage());
                 }

               } } else {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Compte introuvable");
        }

    }

  

    public boolean validateEmail(TextField email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }

    public Stage getConnectStage() {
        Stage stage = new Stage();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("Signin.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Connecter");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return stage;
    }
      @FXML
    private void dejacompte(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent root = loader.load();
            SignupController sc = loader.getController();
            sc.signUpWindow();
            Stage stage = (Stage) btndejacompte.getScene().getWindow();
            stage.close();
            sc.signUpWindow();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void connectWindow() {
        getConnectStage();
    }
}
