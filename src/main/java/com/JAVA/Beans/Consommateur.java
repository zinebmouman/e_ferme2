package com.JAVA.Beans;

/**
 * Consommateur.java
 * Cette classe modèle représente une entité Consommateur.
 */
public class Consommateur extends User {
    protected String address; // Adresse du consommateur

    // Constructeur par défaut
    public Consommateur() {
        super();
    }

    // Constructeur avec paramètres (hérité de User)
    public Consommateur(Long id, String nom, String email, String login, String password, String telephone, Long type, String address) {
        super(id, nom, email, login, password, telephone, type);
        this.address = address;
    }

    // Getters et Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Méthode toString pour afficher les informations du consommateur
    @Override
    public String toString() {
        return "Consommateur [id=" + id + ", nom=" + nom + ", address=" + address + "]";
    }
}
