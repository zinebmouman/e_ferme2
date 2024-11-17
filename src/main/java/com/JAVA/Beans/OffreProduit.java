package com.JAVA.Beans;

/**
 * OffreProduit.java
 * Cette classe modèle représente l'entité OffreProduit, qui lie une offre à un produit.
 */
public class OffreProduit {
    protected Long idOffreProduit;   // Identifiant de l'offre-produit
    protected Long offreId;          // Identifiant de l'offre
    protected Long produitId;        // Identifiant du produit

    // Constructeur par défaut
    public OffreProduit() {
        super();
    }

    // Constructeur avec paramètres
    public OffreProduit(Long offreId, Long produitId) {
        super();
        this.offreId = offreId;
        this.produitId = produitId;
    }

    // Constructeur complet
    public OffreProduit(Long idOffreProduit, Long offreId, Long produitId) {
        super();
        this.idOffreProduit = idOffreProduit;
        this.offreId = offreId;
        this.produitId = produitId;
    }

    // Getters et Setters
    public Long getIdOffreProduit() {
        return idOffreProduit;
    }

    public void setIdOffreProduit(Long idOffreProduit) {
        this.idOffreProduit = idOffreProduit;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    // Méthode toString pour afficher les informations de l'offre-produit
    @Override
    public String toString() {
        return "OffreProduit [idOffreProduit=" + idOffreProduit + ", offreId=" + offreId + ", produitId=" + produitId + "]";
    }
}
