/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pidev.entities.User;
import pidev.services.UserCRUD;
import pidev.utils.UserSession;

/**
 * FXML Controller class
 *
 * @author skann
 */
public class ProfileController implements Initializable {

    @FXML
    private ImageView ivphoto_personnel;
    @FXML
    private ImageView ivphoto_permis;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label email;
    @FXML
    private Label num_tel;
    @FXML
    private Label num_permis;
    @FXML
    private Label Ville;
    @FXML
    private Label cin;
    @FXML
    private Button btnmodifierprofile;
    @FXML
    private Button btnlogout;
    @FXML
    private Button btnconsulterliste;
UserCRUD uc=new UserCRUD();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if((uc.getUserByEmail(UserSession.getEmail()).getRole().equals(User.Role.valueOf("CLIENT"))))
            btnconsulterliste.setVisible(false);
        InputStream stream = null;
        InputStream stream1 = null;
        try {
            UserCRUD uc = new UserCRUD();
            User user = uc.getUserByEmail(UserSession.getEmail());
            nom.setText(user.getNom());
            prenom.setText(user.getPrenom());
            email.setText(user.getEmail());
            num_tel.setText(user.getNum_tel());
            num_permis.setText(user.getNum_permis());
            Ville.setText(user.getVille());
            cin.setText(user.getCin());
            stream = new FileInputStream(user.getPhoto_personel());
            stream1 = new FileInputStream(user.getPhoto_permis());
            Image photopermis = new Image(stream1);
            Image photopersonnel = new Image(stream);
            ivphoto_personnel.setImage(photopersonnel);
            ivphoto_permis.setImage(photopersonnel);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Stage profileWindow() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Profil");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return stage;
    }
    @FXML
    private void Déconnecter(ActionEvent event){
         try {
             UserSession.cleanUserSession(); 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signin.fxml"));
            Parent root = loader.load();
            SigninController sc = loader.getController();
            Stage stage = (Stage) btnlogout.getScene().getWindow();
            stage.close();
            sc.connectWindow();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    @FXML
   private void updateScreen(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateUser.fxml"));
            Parent root = loader.load();
            UpdateUserController uuc = loader.getController();
            Stage stage = (Stage) btnmodifierprofile.getScene().getWindow();
            stage.close();
            uuc.getUpdateWindowStage();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
   }
   @FXML
   private void consulterliste(ActionEvent event){
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserList.fxml"));
            Parent root = loader.load();
            UserListController ulc = loader.getController();
            Stage stage = (Stage) btnconsulterliste.getScene().getWindow();
            stage.close();
            ulc.userListWindow();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
   }

}
