/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.interfaces;

import java.util.List;
import pidev.entities.User;

/**
 *
 * @author skann
 */
public interface InterfaceCRUD<T> {
     public void ajouterUtilisateur(T t);
     public void supprimerUtilisateur(T t);
     public void modifierUtilisateur(T t);
     public boolean emaildejaUtilise(T t);
     public boolean CindejaUtilise(T t);
     public boolean num_permidejaUtilise(T t);
     public boolean authentifier(T t);
    public List<T> consulterListe();
    public User getUserByEmail(T t);
}
