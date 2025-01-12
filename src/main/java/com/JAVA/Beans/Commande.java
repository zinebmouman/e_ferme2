package com.JAVA.Beans;

/**
 * Commande.java
 * Cette classe modèle représente une entité Commande.
 */
public class Commande {
    private Long id;              // Identifiant de la commande
    private Long consommateur_id; // Identifiant du consommateur
    private String statut;        // Statut de la commande
    private String date;          // Date de la commande
    private String heure;         // Heure de la commande
    private int total; 
    private String clientNom;
    private String clientEmail;
    private String clientTelephone;
    private String clientAdresse;

    // Constructeur par défaut
    public Commande() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public Commande(Long consommateur_id, String date, String heure, String statut) {
        super();
        this.consommateur_id = consommateur_id;
        this.date = date;
        this.heure = heure;
        this.statut = statut;
    }

    // Constructeur avec tous les paramètres
    public Commande(Long id, Long consommateur_id, String date, String heure, String statut) {
        super();
        this.id = id;
        this.consommateur_id = consommateur_id;
        this.date = date;
        this.heure = heure;
        this.statut = statut;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsommateur_id() {
        return consommateur_id;
    }

    public void setConsommateur_id(Long consommateur_id) {
        this.consommateur_id = consommateur_id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    public String getclientTelephone() {
        return clientTelephone;
    }

    public void setclientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
    }


    // Méthode toString pour afficher les informations de la commande
    public String getClientAdresse() {
        return clientAdresse;
    }

    public void setClientAdresse(String clientAdresse) {
        this.clientAdresse = clientAdresse;
    }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", consommateur_id=" + consommateur_id + ", date=" + date
            + ", heure=" + heure + ", statut=" + statut + ", clientNom=" + clientNom + ", clientEmail=" + clientEmail
            + ", clientTelephone=" + clientTelephone + ", clientAdresse=" + clientAdresse + "]";
    }
}
