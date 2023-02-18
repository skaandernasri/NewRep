/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.entities;


import java.text.*;
import java.util.Calendar;
/**
 *
 * @author skann
 */
public class User {

    private int id;
    
    private String nom;
    private String prenom;
    private String cin;
    private String date_naiss;
    private String photo_personel;
    private String photo_permis;
    private String num_permis;
    private String ville;
    private String num_tel;
    private Role role;
    private String email;
    private String password;
    public User(String nom, String prenom, String cin, String date_naiss, String photo_personel, String photo_permis, String num_permis, String ville, String num_tel, Role role, String email, String password) {
        
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.date_naiss = date_naiss;
        this.photo_personel = photo_personel;
        this.photo_permis = photo_permis;
        this.num_permis = num_permis;
        this.ville = ville;
        this.num_tel = num_tel;
        this.role = role;
        this.email = email;
        this.password = password;
    }

   

    public User() {
    }

    public User(String email, String password) {
    }

    public User(int id, String nom, String prenom, String cin, String date_naiss, String num_permis, String ville, String num_tel, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.date_naiss = date_naiss;
        this.num_permis = num_permis;
        this.ville = ville;
        this.num_tel = num_tel;
        this.email = email;
    }

   
   
    public User(String i) {
        this.nom = i;
    }

   

  
  
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

 

    public String getPhoto_personel() {
        return photo_personel;
    }

    public void setPhoto_personel(String photo_personel) {
        this.photo_personel = photo_personel;
    }

    public String getPhoto_permis() {
        return photo_permis;
    }

    public void setPhoto_permis(String photo_permis) {
        this.photo_permis = photo_permis;
    }

    public String getNum_permis() {
        return num_permis;
    }

    public void setNum_permis(String num_permis) {
        this.num_permis = num_permis;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
public String getDate_naiss(){
    return this.date_naiss;
}
public void setDate_naiss(String date_naiss){
    this.date_naiss=date_naiss;
}
   public enum Role {
ADMIN,CLIENT,NULL
}
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", date_naiss=" + date_naiss + ", num_permis=" + num_permis + ", ville=" + ville + ", num_tel=" + num_tel + ", role=" + role + ", email=" + email + '}';
    }



    
    
}
