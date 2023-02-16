/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private TextField tfemail;
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
    Stage secondaryStage = new Stage();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void PhotoPersonnel(ActionEvent event) {

        try {
            UserCRUD uc = new UserCRUD();
            uc.uploadPhotoPersonnel(user);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void PhotoPermis(ActionEvent event) {
        try {
            UserCRUD uc = new UserCRUD();
            uc.uploadPhotoPermis(user);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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
                || tfprenom.getText().isEmpty() || tfemail.getText().isEmpty()
                || date_naissance.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).isEmpty() || tfnum_permi.getText().isEmpty()
                || tfville.getText().isEmpty() || tfnum_tel.getText().isEmpty()
                || tfemail.getText().isEmpty() || pfpassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "il reste un ou des champs vide!");
        } else if (user.getPhoto_permis().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "vous devez enregistrez une photo de votre permis!");
        } else if (user.getPhoto_personel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "vous devez enregistrez une photo personnelle!");
        } else if (!(validateEmail(tfemail))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "La format d'email est incorrect!");
        } else // if(tfnom!=""&&)
        {
            user.setNom(tfnom.getText());
        }
        user.setPrenom(tfprenom.getText());
        user.setEmail(tfemail.getText());
        user.setCin(tfcin.getText());
        user.setDate_naiss(date_naissance.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        user.setNum_permis(tfnum_permi.getText());
        user.setVille(tfville.getText());
        user.setNum_tel(tfnum_tel.getText());
        user.setRole(Role.CLIENT);
        user.setEmail(tfemail.getText());
        user.setPassword(pfpassword.getText());
        UserCRUD uc = new UserCRUD();
        if (uc.emaildejaUtilise(user)) {
            infoBox("Email deja utilisé", null, "Echec");
        } else if (uc.CindejaUtilise(user)) {
            infoBox("Cin déja utilisé", null, "Echec");
        } else if (uc.num_permidejaUtilise(user)) {
            infoBox("numéro de permis déja utilisé", null, "Echec");
        } else {
            uc.ajouterUtilisateur(user);
            infoBox("Utilisateur ajouté avec succé", null, "succé");
            connectWindow(secondaryStage);

        }

    }

    public boolean validateEmail(TextField email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }

    public void connectWindow(Stage secondaryStage) {
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
        user.setEmail(tfemail.getText());
        user.setPassword(pfpassword.getText());
        UserCRUD uc = new UserCRUD();
        if (uc.authentifier(user)) {
            currentUser = new User(user.getEmail(), user.getPassword());
        }
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

    public User getCurrentUser() {
        return this.currentUser;
    }

}
