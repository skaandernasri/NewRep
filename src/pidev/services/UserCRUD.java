/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.services;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import pidev.entities.Role;
import pidev.entities.User;
import pidev.gui.SignupController;
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

            String requete = "INSERT INTO utilisateur(nom,prenom,cin,date_naiss,num_permis,ville,num_tel,login,mdp,photo_personel,photo_permis,role)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pst = Connexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getCin());
            pst.setString(4, u.getDate_naiss());
            pst.setString(10, u.getPhoto_personel());
            pst.setString(11, u.getPhoto_permis());
            pst.setString(5, u.getNum_permis());
            pst.setString(6, u.getVille());
            pst.setString(7, u.getNum_tel());
            pst.setString(12, u.getRole().toString());
            pst.setString(8, u.getEmail());
            pst.setString(9, u.getPassword());
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerUtilisateur(User t) {
        try {
            String requete = "SELECT id from utilisateur where login=" + "'" + t.getEmail() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                String requete1 = "DELETE from utilisateur where  id= " + "'" + rs.getInt(1) + "'";
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
            String requete = "UPDATE utilisateur SET  login= " + "'" + t.getEmail() + "'"
                    + "mdp=" + "'" + t.getPassword() + "'"
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
        List<User> myList = new ArrayList<>();
        String requete = "SELECT id,nom,prenom,cin,date_naiss,num_permis,ville,num_tel,login FROM utilisateur where role = " + "'" + "client" + "'";
        try {
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public boolean emaildejaUtilise(User t) {
        boolean test = false;
        try {
            String requete = "SELECT * from utilisateur where login = " + "'" + t.getEmail() + "'";
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
            String requete = "SELECT * from utilisateur where cin = " + "'" + t.getCin() + "'";
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
            String requete = "SELECT * from utilisateur where num_permis = " + "'" + t.getNum_permis() + "'";
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

            String requete = "SELECT * from utilisateur where login = " + "'" + t.getEmail() + "' and mdp = " + "'" + t.getPassword() + "'";
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
            String requete = "SELECT id,nom,prenom,cin,num_permis,ville,num_tel,login,mdp FROM utilisateur where login = " + "'" + t.getEmail() + "'";
            Statement st = Connexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                u.setId(rs.getInt(1));
                u.setNom(rs.getNString(2));
                u.setPrenom(rs.getNString(3));
                u.setCin(rs.getNString(4));
                u.setNum_permis(rs.getNString(5));
                u.setVille(rs.getNString(6));
                u.setNum_tel(rs.getNString(7));
                u.setEmail(rs.getNString(8));
                u.setPassword(rs.getNString(9));
                // u.setRole(rs.getObject(9, role));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    @Override
    public void uploadPhotoPersonnel(User t) throws IOException {
        File dossierDest = new File("C:/Users/skann/OneDrive/Documents/NetBeansProjectsm/swift_ride/UploadFileLocation/PhotoPersonnel");
        JFileChooser image_upload = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpeg", "jpg", "png");
        image_upload.setFileFilter(filter);
        int res = image_upload.showSaveDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new DataInputStream(new FileInputStream(image_upload.getSelectedFile()));
                File imagedesination = new File(dossierDest, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + image_upload.getSelectedFile().getName());
                 output = Files.newOutputStream(imagedesination.toPath());
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                t.setPhoto_personel(imagedesination.toPath().toString());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
           
            } 
                    input.close();
                                   output.close();

            }
        }
    

    @Override
    public void uploadPhotoPermis(User t)  throws IOException {
         File dossierDest = new File("C:/Users/skann/OneDrive/Documents/NetBeansProjectsm/swift_ride/UploadFileLocation/PhotoPermis");
        JFileChooser image_upload = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpeg", "jpg", "png");
        image_upload.setFileFilter(filter);
        int res = image_upload.showSaveDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new DataInputStream(new FileInputStream(image_upload.getSelectedFile()));
                File imagedesination = new File(dossierDest, new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + image_upload.getSelectedFile().getName());
                 output = Files.newOutputStream(imagedesination.toPath());
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                t.setPhoto_permis(imagedesination.toPath().toString());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
           
            } 
                    input.close();
                                   output.close();

            }
        }

}
