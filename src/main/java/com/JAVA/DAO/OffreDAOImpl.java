package com.JAVA.DAO;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.OffreProduit;
import com.JAVA.Beans.Produit;
import com.JAVA.utils.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreDAOImpl {
    private DAOFactory daoFactory;

    public  OffreDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }



 

    // Ajouter une nouvelle offre
 // Ajouter une nouvelle offre
    public Long ajouterOffre(Offre offre) throws SQLException {
        String query = "INSERT INTO offre (nom, prix_pack, taux_reduction, date_debut, date_fin, description, fermier_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) { // Ajout RETURN_GENERATED_KEYS pour récupérer l'ID généré
            ps.setString(1, offre.getNom());
            ps.setDouble(2, offre.getPrixPack());
            ps.setDouble(3, offre.getTauxReduction());
            ps.setDate(4, new java.sql.Date(offre.getDateDebut().getTime()));
            ps.setDate(5, new java.sql.Date(offre.getDateFin().getTime()));
            ps.setString(6, offre.getDescription());
            ps.setLong(7, offre.getFermierId());

            // Utiliser executeUpdate pour les requêtes d'insertion
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Récupérer l'identifiant généré
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        return null; // Retourner null si aucune clé n'est générée
    }


    // Associer des produits à une offre
    public void ajouterProduitsAOffre(Long offreId, List<Long> produitIds) throws SQLException {
        String query = "INSERT INTO offre_produit (offre_id, produit_id) VALUES (?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            for (Long produitId : produitIds) {
                ps.setLong(1, offreId);
                ps.setLong(2, produitId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }


    public void ajouterOffreProduit(OffreProduit offreProduit) {
        String sql = "INSERT INTO offre_produit (offre_id, produit_id) VALUES (?, ?)";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, offreProduit.getOffreId());
            ps.setLong(2, offreProduit.getProduitId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit à l'offre : " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Obtenir la liste des produits associés à une offre
    public List<Long> getProduitsParOffre(Long offreId) throws SQLException {
        List<Long> produits = new ArrayList<>();
        String query = "SELECT produit_id FROM offre_produit WHERE offre_id = ?";
        try (Connection connection = daoFactory.getConnection();
                PreparedStatement  ps = connection.prepareStatement(query);
      		  ResultSet resultSet = ps.executeQuery())  {
            ps.setLong(1, offreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    produits.add(rs.getLong("produit_id"));
                }
            }
        }
        return produits;
    }
  
    public List<Offre> getToutesLesOffres(Long fermierId) throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String query = "SELECT * FROM offre WHERE fermier_id = ?";

        // Obtenir une connexion via la DAOFactory
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Définir le paramètre de la requête
            ps.setLong(1, fermierId);

            // Exécuter la requête et traiter le résultat
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Offre offre = new Offre();
                    offre.setIdOffre(rs.getLong("id"));
                    offre.setNom(rs.getString("nom"));
                    offre.setPrixPack(rs.getDouble("prix_pack"));
                    offre.setTauxReduction(rs.getDouble("taux_reduction"));
                    offre.setDateDebut(rs.getDate("date_debut"));
                    offre.setDateFin(rs.getDate("date_fin"));
                    offre.setDescription(rs.getString("description"));
                    offre.setFermierId(rs.getLong("fermier_id"));

                    // Ajouter l'offre à la liste
                    offres.add(offre);
                }
            }
        } catch (SQLException e) {
            // Log de l'exception (si un système de log est utilisé)
            System.err.println("Erreur lors de la récupération des offres : " + e.getMessage());
            throw e; // Relancer l'exception pour la gestion dans les couches supérieures
        }

        return offres;
    }
    public List<Produit> getProduitsParFermier(Long fermierId) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE fermier_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, fermierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(rs.getLong("id"));
                    produit.setNom(rs.getString("nom"));
                    produit.setPrix(rs.getDouble("prix"));
                    produit.setDescription(rs.getString("description"));
                    produits.add(produit);
                }
            }
        }
        return produits;
    }
    public void ajouterProduitAOffre(Long offreId, Long produitId) throws SQLException {
        String query = "INSERT INTO offre_produit (offre_id, produit_id) VALUES (?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, offreId);
            ps.setLong(2, produitId);
            ps.executeUpdate();
        }
    }
    public void retirerProduitDeOffre(Long offreId, Long produitId) throws SQLException {
        String query = "DELETE FROM offre_produit WHERE offre_id = ? AND produit_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, offreId);
            ps.setLong(2, produitId);
            ps.executeUpdate();
        }
    }
    public List<Produit> getProduitsAssocies(Long offreId) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.* FROM produit p " +
                       "JOIN offre_produit op ON p.id = op.produit_id " +
                       "WHERE op.offre_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, offreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(rs.getLong("id"));
                    produit.setNom(rs.getString("nom"));
                    produit.setPrix(rs.getDouble("prix"));
                    produit.setDescription(rs.getString("descreption"));
                    produits.add(produit);
                }
            }
        }
        return produits;
    }





}


