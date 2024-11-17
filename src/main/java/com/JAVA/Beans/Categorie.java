package com.JAVA.Beans;

/**
 * Categorie.java
 * Cette classe représente une entité Categorie.
 */
public class Categorie {
    private Long id_categorie;
    private String nom_categorie; // Nom de la catégorie
    private String description_categorie;

    // Constructeur par défaut
    public Categorie() {}

    // Constructeur avec paramètres
    public Categorie(Long id_categorie, String nom_categorie,  String description_categorie) {
        this.id_categorie = id_categorie;
        this.nom_categorie = nom_categorie;
        this.description_categorie = description_categorie;
    }

    // Getters et Setters
    public Long getIdCategorie() {
        return id_categorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.id_categorie = idCategorie;
    }

    public String getNom() {
        return nom_categorie;
    }

    public void setNom(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }
    public String getdescription_categorie() {
        return description_categorie;
    }

    public void setdescription_categorie(String description_categorie) {
        this.description_categorie = description_categorie;
    }

    @Override
    public String toString() {
        return "Categorie [id_categorie=" + id_categorie + ", nom_categorie=" + nom_categorie + ", description_categorie=" + description_categorie + "]";
    }
}
