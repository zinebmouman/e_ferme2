package com.JAVA.Beans;


public class Email {
    private int id;
    private String emailConsommateur;
    private String sujet;
    private String contenu;
    private String dateEnvoi;

    // Constructeurs
    public Email() {}

    public Email(String emailConsommateur, String sujet, String contenu) {
        this.emailConsommateur = emailConsommateur;
        this.sujet = sujet;
        this.contenu = contenu;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailConsommateur() {
        return emailConsommateur;
    }

    public void setEmailConsommateur(String emailConsommateur) {
        this.emailConsommateur = emailConsommateur;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}
