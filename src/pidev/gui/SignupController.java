/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import pidev.entities.Role;
import pidev.entities.User;
import pidev.services.UserCRUD;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class SignupController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tflogin;
    @FXML
    private PasswordField pfpassword;
    @FXML
    private DatePicker date_naissance;
    @FXML
    private TextField tfcin;
    @FXML
    private TextField tfnum_permi;
    @FXML
    private TextField tfnum_tel;
    @FXML
    private TextField tfville;
    @FXML
    private Button btnphoto_personnel;
    @FXML
    private Button btnphoto_permi;
    @FXML
    private Button btnsignup;
    User user = new User();
    private User currentUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void PhotoPersonnel(ActionEvent event) {
        JFileChooser image_upload = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpeg", "jpg", "png");
        image_upload.setFileFilter(filter);
        int res = image_upload.showSaveDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {

            user.setPhoto_personel(image_upload.getSelectedFile().getAbsolutePath());
        }

    }

    @FXML
    private void PhotoPermis(ActionEvent event) {
        JFileChooser image_upload = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpeg", "jpg", "png");
        image_upload.setFileFilter(filter);
        int res = image_upload.showSaveDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {

            user.setPhoto_permis(image_upload.getSelectedFile().getAbsolutePath());
        }
    }

    @FXML
    private void Signup(ActionEvent event) {
        Window owner = btnsignup.getScene().getWindow();
        if (!(tfcin.getText().chars().allMatch(Character::isDigit))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Cin doit composé des chiffres seulement!");
        } else if (!(tfnum_permi.getText().chars().allMatch(Character::isDigit))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de permis doit composé des chiffres seulement!");
        } else if (!(tfnum_tel.getText().chars().allMatch(Character::isDigit))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de téléphone doit composé des chiffres seulement!");
        } else if (tfcin.getText().isEmpty() || tfnom.getText().isEmpty()
                || tfprenom.getText().isEmpty() || tflogin.getText().isEmpty()
                || date_naissance.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).isEmpty() || tfnum_permi.getText().isEmpty()
                || tfville.getText().isEmpty() || tfnum_tel.getText().isEmpty()
                || tflogin.getText().isEmpty() || pfpassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "il reste des champs vide!");
        } else if (user.getPhoto_permis().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "vous devez enregistrez une photo de votre permis!");
        } else if (user.getPhoto_personel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "vous devez enregistrez une photo personnelle!");
        } else // if(tfnom!=""&&)
        {
            user.setNom(tfnom.getText());
        }
        user.setPrenom(tfprenom.getText());
        user.setLogin(tflogin.getText());
        user.setCin(tfcin.getText());
        user.setDate_naiss(date_naissance.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        user.setNum_permis(tfnum_permi.getText());
        user.setVille(tfville.getText());
        user.setNum_tel(tfnum_tel.getText());
        user.setRole(new Role("client"));
        user.setLogin(tflogin.getText());
        user.setPassword(pfpassword.getText());
        UserCRUD uc = new UserCRUD();
        if (uc.LogindejaUtilise(user)) {
            infoBox("Login deja utilisé", null, "Failed");
        } else if (uc.CindejaUtilise(user)) {
            infoBox("Cin déja utilisé", null, "Failed");
        } else if (uc.num_permidejaUtilise(user)) {
            infoBox("numéro de permis déja utilisé", null, "Failed");
        } else {
            uc.ajouterUtilisateur(user);
            infoBox("Utilisateur ajouté avec succé", null, "succé");
            
        }
        

    }
     public void start(Stage secondaryStage) {
      try {
            Parent root = FXMLLoader.load(getClass().getResource("Signin.fxml"));
            Scene scene = new Scene(root);
            secondaryStage.setTitle("Connecter");
            secondaryStage.setScene(scene);
            secondaryStage.show();
        } catch (IOException ex) {
System.out.println(ex.getMessage());
        }
    }
     private void Signin(ActionEvent event) {
            User user = new User();
                  user.setLogin(tflogin.getText());
                  user.setPassword(pfpassword.getText());
                  UserCRUD uc = new UserCRUD();
        if (uc.authentifier(user))
            currentUser = new User(user.getLogin(), user.getPassword());
        }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    

}
