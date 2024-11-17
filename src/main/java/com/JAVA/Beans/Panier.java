package com.JAVA.Beans;

public class Panier {
    private Long id;
    private Long consommateurId;
    private Long produitId;
    private int quantite;
    private Double prix;
    private Long offreId;
    private Long promotionId;
    private Produit produit;  // Objet Produit
    private Offre offre; 

    // Constructeur par défaut
    public Panier() {}

    // Constructeur avec paramètres
    public Panier(Long consommateurId, Long produitId, int quantite, Double prix, Long offreId, Long promotionId) {
        this.consommateurId = consommateurId;
        this.produitId = produitId;
        this.quantite = quantite;
        this.prix = prix;
        this.offreId = offreId;
        this.promotionId = promotionId;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsommateurId() {
        return consommateurId;
    }

    public void setConsommateurId(Long consommateurId) {
        this.consommateurId = consommateurId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Long getOffreId() {
        return offreId;
    }

    public void setOffreId(Long offreId) {
        this.offreId = offreId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    @Override
    public String toString() {
        return "Panier{" +
               "id=" + id +
               ", consommateurId=" + consommateurId +
               ", produitId=" + produitId +
               ", quantite=" + quantite +
               ", prix=" + prix +
               ", offreId=" + offreId +
               ", promotionId=" + promotionId +
               ", produit=" + (produit != null ? produit.toString() : "Produit non défini") +
               ", offre=" + (offre != null ? offre.toString() : "Offre non définie") +
               '}';
    }

}
