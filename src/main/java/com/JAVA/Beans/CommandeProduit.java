package com.JAVA.Beans;

/**
 * CommandeProduit.java
 * Cette classe modèle représente une entité CommandeProduit.
 */
public class CommandeProduit {
    private Long commande_id;   // Identifiant de la commande
    private Long produit_id;    // Identifiant du produit
    private int quantite; 
    

    // Constructeur par défaut
    public CommandeProduit() {
        super();
    }

    // Constructeur avec tous les paramètres
    public CommandeProduit(Long commande_id, Long produit_id, int quantite) {
        super();
        this.commande_id = commande_id;
        this.produit_id = produit_id;
        this.quantite = quantite;
    }

    // Getters et Setters
    public Long getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(Long commande_id) {
        this.commande_id = commande_id;
    }

    public Long getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(Long produit_id) {
        this.produit_id = produit_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    

    // Méthode toString pour afficher les informations de la commande produit
    @Override
    public String toString() {
        return "CommandeProduit [commande_id=" + commande_id + ", produit_id=" + produit_id
                + ", quantite=" + quantite + "]";
    }
}
