package com.JAVA.Beans;

/**
 * Fermies.java
 * Cette classe modèle représente une entité Fermies.
 */
public class Fermies extends User {
    private String address; // Adresse de la ferme
    private String typeFermies;

    // Constructeur par défaut
    public Fermies() {
        super();
    }

    // Constructeur avec paramètres (hérité de User)
    public Fermies(Long id, String nom, String email, String login, String password, String telephone, Long type, String address, String typeFermies) {
        super(id, nom, email, login, password, telephone, type);
        this.address = address;
        this.typeFermies = typeFermies;
    }

    // Getters et Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeFermies() {
        return typeFermies;
    }

    public void setTypeFermies(String typeFermies) {
        this.typeFermies = typeFermies;
    }

    // Méthode toString pour afficher les informations de la ferme
    @Override
    public String toString() {
        return "Fermies [id=" + id + ", nom=" + nom + ", address=" + address + ", typeFermies=" + typeFermies + "]";
    }
}
