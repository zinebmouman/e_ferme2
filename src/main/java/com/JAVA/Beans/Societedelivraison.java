package com.JAVA.Beans;

public class Societedelivraison {
    private int idCommande;
    private String dateCommande;
    private String heureCommande;
    private float totalCommande;
    private String statutCommande;
    private String addressClient;
    private String nomClient;
    private String telephoneClient;

    // Getters et Setters
    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getHeureCommande() {
        return heureCommande;
    }

    public void setHeureCommande(String heureCommande) {
        this.heureCommande = heureCommande;
    }

    public float getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(float totalCommande) {
        this.totalCommande = totalCommande;
    }

    public String getStatutCommande() {
        return statutCommande;
    }

    public void setStatutCommande(String statutCommande) {
        this.statutCommande = statutCommande;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getTelephoneClient() {
        return telephoneClient;
    }

    public void setTelephoneClient(String telephoneClient) {
        this.telephoneClient = telephoneClient;
    }

    @Override
    public String toString() {
        return "Societedelivraison{" +
                "idCommande=" + idCommande +
                ", dateCommande='" + dateCommande + '\'' +
                ", heureCommande='" + heureCommande + '\'' +
                ", totalCommande=" + totalCommande +
                ", statutCommande='" + statutCommande + '\'' +
                ", addressClient='" + addressClient + '\'' +
                ", nomClient='" + nomClient + '\'' +
                ", telephoneClient='" + telephoneClient + '\'' +
                '}';
    }

}
