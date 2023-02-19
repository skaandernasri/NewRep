/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.interfaces;

import java.io.IOException;
import java.util.List;
import pidev.entities.User;

/**
 *
 * @author skann
 */
public interface InterfaceCRUD<T> {
     public void ajouterUtilisateur(T t);
     public void supprimerUtilisateur(T t);
     public boolean modifierUtilisateur(T t);
     public boolean emaildejaUtilise(String email);
     public boolean cindejaUtilise(String  cin);
     public boolean num_permidejaUtilise(String num);
     public void uploadPhotoPersonnel(T t) throws IOException;
     public void uploadPhotoPermis(T t) throws IOException;
     public boolean authentifier(T t);
    public List<T> consulterListe();
    public User getUserByEmail(String s);
   
}
