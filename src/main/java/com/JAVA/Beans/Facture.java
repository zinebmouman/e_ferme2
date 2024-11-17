package com.JAVA.Beans;

/**
 * Facture.java
 * Cette classe modèle représente une entité Facture.
 */
public class Facture {
    protected Long idFacture;        // Identifiant de la facture
    protected Long idCommande;       // Identifiant de la commande
    protected String dateFacture;    // Date de la facture
    protected Double montantTotal;   // Montant total de la facture

    // Constructeur par défaut
    public Facture() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public Facture(Long idCommande, String dateFacture, Double montantTotal) {
        super();
        this.idCommande = idCommande;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
    }

    // Constructeur avec tous les paramètres
    public Facture(Long idFacture, Long idCommande, String dateFacture, Double montantTotal) {
        super();
        this.idFacture = idFacture;
        this.idCommande = idCommande;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
    }

    // Getters et Setters
    public Long getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Long idFacture) {
        this.idFacture = idFacture;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    // Méthode toString pour afficher les informations de la facture
    @Override
    public String toString() {
        return "Facture [idFacture=" + idFacture + ", idCommande=" + idCommande + ", dateFacture=" + dateFacture
                + ", montantTotal=" + montantTotal + "]";
    }
}
