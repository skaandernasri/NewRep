/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author skann
 */
public class MainClass extends Application {
    
    @Override
    public void start(Stage primaryStage) { try {
            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Crée un compte");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
System.out.println(ex.getMessage());
        }
    
     /* try {
            FXMLLoader loader = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Parent root =loader.load();
            SignupController sc= loader.getController();
            sc.signUpWindow();
            
        } catch (IOException ex) {
System.out.println(ex.getMessage());
        }
      
    }

    /**
     * @param args the command line arguments
     */
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
