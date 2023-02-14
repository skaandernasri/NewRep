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
import java.util.ArrayList;
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

            String requete = "INSERT INTO user(nom,prenom,cin,date_naissance,photo_personel,photo_permis,num_permis,ville,num_tel,role,email,password)"
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
            pst.setString(11, u.getEmail());
            pst.setString(12, u.getPassword());
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerUtilisateur(User t) {
        try {
            String requete="SELECT id from user where email="+ "'" + t.getEmail() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if(rs.next()){
            String requete1 = "DELETE from user where  id= " + "'" + rs.getInt(1) + "'";
            Statement pst = Connexion.getInstance().getCnx().createStatement();
            pst.executeUpdate(requete);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void modifierUtilisateur(User t) {
        try {
            String requete = "UPDATE user SET  email= " + "'" + t.getEmail() + "'"
                    + "passowrd=" + "'" + t.getPassword() + "'"
                    + "num_tel" + "'" + t.getNum_tel() + "'"
                    + "nom" + "'" + t.getNom() + "'"
                    + "prenom" + "'" + t.getPrenom() + "'"
                    + "ville" + "'" + t.getVille() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> consulterListe() {
   List<User> myList=new ArrayList<>();
        String requete="SELECT id,nom,prenom,cin,date_naissance,num_permis,ville,num_tel,email FROM user where role = "+"'"+"client"+"'";
        try {
            Statement st = Connexion.getInstance().getCnx().createStatement();
          ResultSet rs=  st.executeQuery(requete);
          while(rs.next()){
              User u = new User();
              u.setId(rs.getInt(1));
              u.setNom(rs.getNString(2));
              u.setPrenom(rs.getNString(3));
              u.setCin(rs.getNString(4));
              u.setDate_naiss(rs.getNString(5));
              u.setNum_permis(rs.getNString(6));
              u.setVille(rs.getNString(7));
              u.setNum_tel(rs.getNString(8));
              u.setEmail(rs.getNString(9));
              myList.add(u);
          }
         
          
        }catch (SQLException ex) {     
            System.out.println(ex.getMessage());
        }
        return myList;    }

    @Override
    public boolean emaildejaUtilise(User t) {
        boolean test = false;
        try {
            String requete = "SELECT * from user where email = " + "'" + t.getEmail() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
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
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
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
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
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

            String requete = "SELECT * from user where email = " + "'" + t.getEmail() + "' and password = " + "'" + t.getPassword() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            test = rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return test;
    }

    @Override
    public User getUserByEmail(User t) {
        User u = new User();
        try {
            String requete="SELECT id,nom,prenom,cin,num_permis,ville,num_tel,email FROM user where email = " + "'" + t.getEmail() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs=  st.executeQuery(requete);
            while(rs.next()){
                u.setId(rs.getInt(1));
                u.setNom(rs.getNString(2));
                u.setPrenom(rs.getNString(3));
                u.setCin(rs.getNString(4));
                u.setNum_permis(rs.getNString(5));
                u.setVille(rs.getNString(6));
                u.setNum_tel(rs.getNString(7));
                u.setEmail(rs.getNString(8));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
         return u;
    }

}
