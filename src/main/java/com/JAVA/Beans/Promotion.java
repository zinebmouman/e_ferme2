package com.JAVA.Beans;

import java.util.Date;

/**
 * Promotion.java
 * Cette classe modèle représente une entité Promotion.
 */
public class Promotion {
    private Long idPromotion;       // Identifiant de la promotion
    private Date dateDebut;         // Date de début de la promotion
    private Date dateFin;           // Date de fin de la promotion
    private Double taux;            // Taux de réduction (en pourcentage)
    private String description;     // Description de la promotion
    private Long idProduit;         // Identifiant du produit auquel la promotion est associée

    // Constructeur par défaut
    public Promotion() {}

    // Constructeur avec paramètres (sans identifiant)
    public Promotion(Date dateDebut, Date dateFin, Double taux, String description, Long idProduit) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.taux = taux;
        this.description = description;
        this.idProduit = idProduit;
    }

    // Constructeur avec tous les paramètres
    public Promotion(Long idPromotion, Date dateDebut, Date dateFin, Double taux, String description, Long idProduit) {
        this.idPromotion = idPromotion;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.taux = taux;
        this.description = description;
        this.idProduit = idProduit;
    }

    // Getters et Setters
    public Long getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(Long idPromotion) {
        this.idPromotion = idPromotion;
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

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    @Override
    public String toString() {
        return "Promotion [idPromotion=" + idPromotion + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
                + ", taux=" + taux + ", description=" + description + ", idProduit=" + idProduit + "]";
    }
}
