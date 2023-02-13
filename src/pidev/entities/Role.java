/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.entities;

/**
 *
 * @author skann
 */
public class Role {

    private String role ;
    
    public Role (){
        
    }

    public Role(String role) {
this.role=role;
    }

    public String getRole() {
        return role;
    }

    

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "Role{" + "role=" + role + '}';
    }
}
