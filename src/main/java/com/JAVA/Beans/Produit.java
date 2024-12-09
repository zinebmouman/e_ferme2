package com.JAVA.Beans;

import java.util.Date;

/**
 * Produit.java
 * Cette classe modèle représente une entité Produit.
 */
public class Produit {
    protected Long idProduit;       // Identifiant du produit
    protected String nom;           // Nom du produit
    protected Double prix;          // Prix du produit
    protected int quantite;         // Quantité du produit
    protected String description;   // Description du produit
    protected String image;         // Chemin de l'image du produit
    protected Date dateRecolte;     // Date de récolte du produit
    protected Long userId;
    protected Long id_categorie; // Identifiant de l'utilisateur (au lieu de fermier_id)
    private Promotion promotion;   // Objet promotion
    private Offre offre;           // Objet offre
    private Categorie categorie;

    // Constructeur par défaut
    public Produit() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public Produit(String nom, Double prix, int quantite, String description, String image, Date dateRecolte, Long userId, Long id_categorie  ,Categorie categorie) {
        super();
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.description = description;
        this.image = image;
        this.dateRecolte = dateRecolte;
        this.userId = userId;
        this.id_categorie = id_categorie ;
        this.categorie = categorie;
    }

    // Constructeur avec tous les paramètres
    public Produit(Long idProduit, String nom, Double prix, int quantite, String description, String image, Date dateRecolte, Long userId, Categorie categorie) {
        super();
        this.idProduit = idProduit;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.description = description;
        this.image = image;
        this.dateRecolte = dateRecolte;
        this.userId = userId;
        
        this.categorie = categorie;
    }

    // Getters et Setters
    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateRecolte() {
        return dateRecolte;
    }

    public void setDateRecolte(Date dateRecolte) {
        this.dateRecolte = dateRecolte;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getid_categorie() {
        return id_categorie;
    }

    public void setid_categorie(Long id_categorie) {
        this.id_categorie = id_categorie;
    }

    // Méthode toString pour afficher les informations du produit
    @Override
    public String toString() {
        return "Produit [idProduit=" + idProduit + ", nom=" + nom + ", prix=" + prix + ", quantite=" + quantite
                + ", description=" + description + ", image=" + image + ", dateRecolte=" + dateRecolte
                + ", userId=" + userId + ", id_categorie=" + id_categorie + "]";
    }

    // Getters et setters pour promotion et offre
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    // Méthode setIdCategorie ajoutée ici
    public void setIdCategorie(int categorieId) {
        this.categorie = new Categorie(); // Assurez-vous que la classe Categorie a un constructeur par défaut
        this.categorie.setIdCategorie((long) categorieId); // Assurez-vous que la classe Categorie a une méthode setId
    }
    public void setId_Categorie(int categorieId) {
        this.categorie = new Categorie(); // Assurez-vous que la classe Categorie a un constructeur par défaut
        this.categorie.setIdCategorie((long) categorieId); // Assurez-vous que la classe Categorie a une méthode setId
    }
	public void setCategorie(Categorie categorie2) {
		// TODO Auto-generated method stub
		
	}
}
