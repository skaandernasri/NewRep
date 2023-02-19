/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
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
 UserCRUD uc = new UserCRUD();
    @FXML
    private Button btndeja_compte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        date_naissance.setValue(LocalDate.now().minusYears(18));
        date_naissance.setDayCellFactory(picker -> {
    final LocalDate today = LocalDate.now();
    return new DateCell() {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item.getYear() > today.minusYears(18).getYear()||(item.getYear()==today.minusYears(18).getYear()&&item.getDayOfYear()>today.getDayOfYear())) {
                setDisable(true);
                setStyle("-fx-background-color: #ffc0cb;");
            }
        }
    };
});
    }

    @FXML
    private void PhotoPersonnel(ActionEvent event) {

        try {
            uc.uploadPhotoPersonnel(user);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @FXML
    private void PhotoPermis(ActionEvent event) {


        try {
            uc.uploadPhotoPermis(user);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
 

        }

    }

    public Stage signUpWindow() {

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
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "il reste un ou des champs vides!");
        }else if (!(validateEmail(tfemail))){
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "La format de l'email est incorrect!");
        }
        else if (pfpassword.getText().length() < 6) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!",
                    "Le mot de passe doit etre composé de six chiffre au minimum");
        } else if (!(tfcin.getText().chars().allMatch(Character::isDigit)) || tfcin.getText().length() != 8) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!",
                    "Cin doit etre composé seulement de 8 chiffres !");
        }  else if (!(tfnum_permi.getText().chars().allMatch(Character::isDigit)) || tfnum_permi.getText().length() != 8) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!",
                    "Le numéro de permis doit etre composé seulement de 8 chiffres !");
        }
        else if (!(tfnum_permi.getText().chars().allMatch(Character::isDigit)) || tfnum_permi.getText().length() != 8) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!",
                    "Le numéro de permis doit etre composé seulement de 8 chiffres !");
        } else if (!(tfnum_tel.getText().chars().allMatch(Character::isDigit)) || tfnum_tel.getText().length() != 8) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!",
                    "Le numéro de téléphone doit etre composé seulement de 8 chiffres !");
            
        }  else if (!(validateEmail(tfemail))) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "La format d'email est incorrect!");
        }

        
       
       else if (uc.emaildejaUtilise(tfemail.getText())) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "Email deja utilisé");
        } else if (uc.cindejaUtilise(tfcin.getText())) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "Cin déja utilisé");
        } else if (uc.num_permidejaUtilise(tfnum_permi.getText())) {
            showAlert1(Alert.AlertType.ERROR, owner, "Echec!", "numéro de permis déja utilisé");
        }else if (user.getPhoto_personel().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Echec!", "vous devez enregistrez une photo personnel!");
             }else if (user.getPhoto_permis().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Echec!", "vous devez enregistrez une photo de votre permis!");
            // } else if (btnphoto_personnel.gete) {
            //   showAlert(Alert.AlertType.ERROR, owner, "Form Echec!", "vous devez enregistrez une photo personnelle!");
        } else {
            try {
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
                uc.ajouterUtilisateur(user);
                showAlert(Alert.AlertType.ERROR, owner, "Succés", "Utilisateur ajouté avec succès");
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

    public static boolean validateEmail(TextField email) {
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
  public void showAlert1(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
