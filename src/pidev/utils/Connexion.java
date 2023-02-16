/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author skann
 */
public class Connexion {
      String url = "jdbc:mysql://localhost:3306/swiftride";
    String login = "root";
    String pwd = "";
   private Connection cnx;
   private static Connexion instance;

    private Connexion() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion etablie");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static Connexion getInstance() {
        if (instance ==null)
            instance = new Connexion();
       
          return instance;
    }

}
