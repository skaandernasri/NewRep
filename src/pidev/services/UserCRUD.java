/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev.entities.Role;
import pidev.entities.User;
import pidev.interfaces.InterfaceCRUD;
import pidev.utils.Connexion;

/**
 *
 * @author skann
 */
public class UserCRUD implements InterfaceCRUD<User> {

    @Override
    public void ajouterUtilisateur(User u) {

        try {

            String requete = "INSERT INTO user(nom,prenom,cin,date_naissance,photo_personel,photo_permis,num_permis,ville,num_tel,role,login,password)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = Connexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getCin());
            pst.setString(4, u.getDate_naiss());
            pst.setString(5, u.getPhoto_personel());
            pst.setString(6, u.getPhoto_permis());
            pst.setString(7, u.getNum_permis());
            pst.setString(8, u.getVille());
            pst.setString(9, u.getNum_tel());
            pst.setString(10, u.getRole().getRole());
            pst.setString(11, u.getLogin());
            pst.setString(12, u.getPassword());
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> listeDesUtilisateurs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean LogindejaUtilise(User t) {
        boolean test = false;
        try {
            String requete = "SELECT * from user where login = " + "'" + t.getLogin() + "'";
            Statement pst = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            test = rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

    @Override
    public boolean CindejaUtilise(User t) {
        boolean test = false;
        try {
            String requete = "SELECT * from user where cin = " + "'" + t.getCin() + "'";
            Statement pst = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            test = rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

    @Override
    public boolean num_permidejaUtilise(User t) {
        boolean test = false;
        try {
            String requete = "SELECT * from user where num_permis = " + "'" + t.getNum_permis() + "'";
            Statement pst = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            test = rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

    @Override
    public boolean authentifier(User t) {
        boolean test = false;
        try {

            String requete = "SELECT * from user where login = " + "'" + t.getLogin() + "' and password = " + "'" + t.getPassword() + "'";
            Statement pst = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = pst.executeQuery(requete);
            test = rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

}
