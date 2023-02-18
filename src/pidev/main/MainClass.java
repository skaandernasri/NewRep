/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.main;

import pidev.entities.User;
import pidev.services.UserCRUD;

/**
 *
 * @author skann
 */
public class MainClass  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserCRUD user=new UserCRUD();
        User u=new User("azeazeazeaza");
       System.out.println( user.modifierUtilisateur(u));
        // TODO code application logic here
    }
    
}
