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
import pidev.services.UserCRUD;

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
    private User currentUser;
    Stage stage = new Stage();

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

            currentUser = new User(user.getEmail(), user.getPassword());
           FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateUser.fxml"));
            UpdateUserController uuc = loader.getController();
            uuc.setCurrentUser(currentUser);
            updateWindow(stage);

        }
        else
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Compte introuvable");

    }

   
 private Stage updateWindowStage(Stage stage) {
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
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public boolean validateEmail(TextField email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }
}
