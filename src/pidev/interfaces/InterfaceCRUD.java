/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.interfaces;

import java.util.List;

/**
 *
 * @author skann
 */
public interface InterfaceCRUD<T> {
     public void ajouterUtilisateur(T t);
     public boolean LogindejaUtilise(T t);
     public boolean CindejaUtilise(T t);
     public boolean num_permidejaUtilise(T t);
     public boolean authentifier(T t);
    public List<T> listeDesUtilisateurs();
}
