package com.JAVA.Beans;

/**
 * Societedelivraison.java
 * Cette classe modèle représente une entité Societedelivraison.
 */
public class Societedelivraison extends User {
    protected String zoneLivraison; // Zone de livraison

    // Constructeur par défaut
    public Societedelivraison() {
        super();
    }

    // Constructeur avec paramètres (hérité de User)
    public Societedelivraison(Long id, String nom, String email, String login, String password, String telephone,Long type, String zoneLivraison) {
        super(id, nom, email, login, password, telephone, type);
        this.zoneLivraison = zoneLivraison;
    }

    // Getters et Setters
    public String getZoneLivraison() {
        return zoneLivraison;
    }

    public void setZoneLivraison(String zoneLivraison) {
        this.zoneLivraison = zoneLivraison;
    }

    // Méthode toString pour afficher les informations de la société de livraison
    @Override
    public String toString() {
        return "Societedelivraison [id=" + id + ", nom=" + nom + ", zoneLivraison=" + zoneLivraison + "]";
    }
}
