package com.JAVA.Beans;

import java.util.Date;

/**
 * Offre.java
 * Cette classe modèle représente une entité Offre.
 */
public class Offre {
    private Long idOffre;          // Identifiant de l'offre
    private String nom;            // Nom de l'offre
    private Double prixPack;       // Prix du pack
    private Double tauxReduction;  // Taux de réduction de l'offre
    private Date dateDebut;        // Date de début de l'offre
    private Date dateFin;          // Date de fin de l'offre
    private String description;    // Description de l'offre

    // Constructeur par défaut
    public Offre() {}

    // Constructeur avec paramètres (sans identifiant)
    public Offre(String nom, Double prixPack, Double tauxReduction, Date dateDebut, Date dateFin, String description) {
        this.nom = nom;
        this.prixPack = prixPack;
        this.tauxReduction = tauxReduction;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }

    // Constructeur avec tous les paramètres
    public Offre(Long idOffre, String nom, Double prixPack, Double tauxReduction, Date dateDebut, Date dateFin, String description) {
        this.idOffre = idOffre;
        this.nom = nom;
        this.prixPack = prixPack;
        this.tauxReduction = tauxReduction;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }

    // Getters et Setters
    public Long getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(Long idOffre) {
        this.idOffre = idOffre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixPack() {
        return prixPack;
    }

    public void setPrixPack(Double prixPack) {
        this.prixPack = prixPack;
    }

    public Double getTauxReduction() {
        return tauxReduction;
    }

    public void setTauxReduction(Double tauxReduction) {
        this.tauxReduction = tauxReduction;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Offre [idOffre=" + idOffre + ", nom=" + nom + ", prixPack=" + prixPack + ", tauxReduction=" + tauxReduction
                + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", description=" + description + "]";
    }
}
