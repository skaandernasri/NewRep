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

import pidev.entities.User;
import pidev.entities.User.Role;
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

    Stage stage = new Stage();
    
    @FXML
    private Button btndeja_compte;

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
   public Stage signUpWindow(){
           

          try {
            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Crée un compte");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
System.out.println(ex.getMessage());
        }
          return stage;
    }

    @FXML
    private void signup(ActionEvent event) {
        Window owner = btnsignup.getScene().getWindow();
        if (tfcin.getText().isEmpty() || tfnom.getText().isEmpty()
                || tfprenom.getText().isEmpty() || tfemail.getText().isEmpty()
                || date_naissance.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).isEmpty() || tfnum_permi.getText().isEmpty()
                || tfville.getText().isEmpty() || tfnum_tel.getText().isEmpty()
                || tfemail.getText().isEmpty() || pfpassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "il reste un ou des champs vides!");
        } else if (!(tfcin.getText().chars().allMatch(Character::isDigit)) || tfcin.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Cin doit etre composé seulement de 8 chiffres !");
        } else if (!(tfnum_permi.getText().chars().allMatch(Character::isDigit)) || tfnum_permi.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de permis doit etre composé seulement de 8 chiffres !");
        } else if (!(tfnum_tel.getText().chars().allMatch(Character::isDigit)) || tfnum_tel.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Le numéro de téléphone doit etre composé seulement de 8 chiffres !");
        } else if (user.getPhoto_permis().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "vous devez enregistrez une photo de votre permis!");
            // } else if (btnphoto_personnel.gete) {
            //   showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "vous devez enregistrez une photo personnelle!");
        } else if (!(validateEmail(tfemail))) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "La format d'email est incorrect!");
        }

        user.setNom(tfnom.getText());
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
        } else if (uc.cindejaUtilise(user)) {
            infoBox("Cin déja utilisé", null, "Echec");
        } else if (uc.num_permidejaUtilise(user)) {
            infoBox("numéro de permis déja utilisé", null, "Echec");
        } else {
            try {
                uc.ajouterUtilisateur(user);
                infoBox("Utilisateur ajouté avec succé", null, "succé");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Signin.fxml"));
                Parent root = loader.load();
                SigninController sc = loader.getController();
                Stage stage = (Stage) btndeja_compte.getScene().getWindow();
                stage.close();
                sc.getConnectStage();
                // siginincontroller.getConnectStage();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public boolean validateEmail(TextField email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email.getText());
        return matcher.matches();
    }

    @FXML
    private void dejacompte(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signin.fxml"));
            Parent root = loader.load();
            SigninController sc = loader.getController();
            Stage stage = (Stage) btndeja_compte.getScene().getWindow();
            stage.close();
            sc.getConnectStage();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
