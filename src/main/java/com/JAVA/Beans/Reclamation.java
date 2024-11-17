package com.JAVA.Beans;

/**
 * Reclamation.java
 * Cette classe modèle représente une entité Reclamation.
 */
public class Reclamation {
    protected Long idReclamation;   // Identifiant de la réclamation
    protected Long idConsommateur;  // Identifiant du consommateur
    protected String description;   // Description de la réclamation
    protected String dateReclamation; // Date de la réclamation

    // Constructeur par défaut
    public Reclamation() {
        super();
    }

    // Constructeur avec paramètres (sans identifiant)
    public Reclamation(Long idConsommateur, String description, String dateReclamation) {
        super();
        this.idConsommateur = idConsommateur;
        this.description = description;
        this.dateReclamation = dateReclamation;
    }

    // Constructeur avec tous les paramètres
    public Reclamation(Long idReclamation, Long idConsommateur, String description, String dateReclamation) {
        super();
        this.idReclamation = idReclamation;
        this.idConsommateur = idConsommateur;
        this.description = description;
        this.dateReclamation = dateReclamation;
    }

    // Getters et Setters
    public Long getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(Long idReclamation) {
        this.idReclamation = idReclamation;
    }

    public Long getIdConsommateur() {
        return idConsommateur;
    }

    public void setIdConsommateur(Long idConsommateur) {
        this.idConsommateur = idConsommateur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(String dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    // Méthode toString pour afficher les informations de la réclamation
    @Override
    public String toString() {
        return "Reclamation [idReclamation=" + idReclamation + ", idConsommateur=" + idConsommateur 
                + ", description=" + description + ", dateReclamation=" + dateReclamation + "]";
    }
}
