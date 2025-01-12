package com.JAVA.Beans;

import java.time.LocalDateTime;

/**
 * Facture.java
 * Cette classe modèle représente une entité Facture.
 */
public class Facture {
    protected Long idFacture;        // Identifiant de la facture
    protected Long idCommande;       // Identifiant de la commande
    protected LocalDateTime dateFacture; // Date de la facture
    protected Double montantTotal;   // Montant total de la facture
    protected String statutPaiement; // Statut du paiement (ex : Non payé, Payé)
    protected String methodePaiement; // Méthode de paiement utilisée (ex : Carte bancaire)

    // Constructeur par défaut
    public Facture() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public Facture(Long idCommande, LocalDateTime dateFacture, Double montantTotal, String statutPaiement, String methodePaiement) {
        super();
        this.idCommande = idCommande;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statutPaiement = statutPaiement;
        this.methodePaiement = methodePaiement;
    }

    // Constructeur avec tous les paramètres
    public Facture(Long idFacture, Long idCommande, LocalDateTime dateFacture, Double montantTotal, String statutPaiement, String methodePaiement) {
        super();
        this.idFacture = idFacture;
        this.idCommande = idCommande;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statutPaiement = statutPaiement;
        this.methodePaiement = methodePaiement;
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

    public LocalDateTime getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDateTime dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    // Méthode toString pour afficher les informations de la facture
    @Override
    public String toString() {
        return "Facture [idFacture=" + idFacture + ", idCommande=" + idCommande + ", dateFacture=" + dateFacture
                + ", montantTotal=" + montantTotal + ", statutPaiement=" + statutPaiement
                + ", methodePaiement=" + methodePaiement + "]";
    }
}
