package com.JAVA.Beans;

/**
 * User.java
 * Cette classe modèle représente une entité User.
 */
public class User {
    protected Long id;           // Identifiant de l'utilisateur
    protected String nom;        // Nom de l'utilisateur
    protected String email;      // Email de l'utilisateur
    protected String login;      // Login de l'utilisateur
    protected String password;   // Mot de passe de l'utilisateur
    protected String telephone;  // Téléphone de l'utilisateur
    protected Long type;         // Type de l'utilisateur

    // Constructeur par défaut
    public User() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public User(String nom, String email, String login, String password, String telephone, Long type) {
        super();
        this.nom = nom;
        this.email = email;
        this.login = login;
        this.password = password;
        this.telephone = telephone;
        this.type = type;
    }

    // Constructeur avec tous les paramètres
    public User(Long id, String nom, String email, String login, String password, String telephone, Long type) {
        super();
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.login = login;
        this.password = password;
        this.telephone = telephone;
        this.type = type;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    // Méthode toString pour afficher les informations de l'utilisateur
    @Override
    public String toString() {
        return "User [id=" + id + ", nom=" + nom + ", email=" + email + ", login=" + login + ", telephone=" + telephone + ", type=" + type + "]";
    }
}
