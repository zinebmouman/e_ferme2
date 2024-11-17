package com.JAVA.Beans;

/**
 * Admin.java
 * Cette classe modèle représente une entité Admin.
 */
public class Admin extends User {

    // Constructeur par défaut
    public Admin() {
        super();
    }

    // Constructeur avec paramètres (hérité de User)
    public Admin(Long id, String nom, String email, String login, String password, String telephone, Long type) {
        super(id, nom, email, login, password, telephone, type);
    }

    // Méthode toString pour afficher les informations de l'admin
    @Override
    public String toString() {
        return "Admin [id=" + id + ", nom=" + nom + ", email=" + email + ", login=" + login + ", telephone=" + telephone + "]";
    }
}
