package com.JAVA.DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;
import com.JAVA.utils.DAOFactory;

public class CommandeDAOImpl implements CommandeDAO {
    private DAOFactory daoFactory;

    public CommandeDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public int addCommande(Commande commande) {
        String sql = "INSERT INTO commande (statut, date, heure, total, consommateur_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection(); // Récupérer la connexion depuis DAOFactory
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, commande.getStatut());
            ps.setString(2, commande.getDate());
            ps.setString(3, commande.getHeure());
            ps.setFloat(4, commande.getTotal());
            ps.setFloat(5, commande.getConsommateur_id());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retourne l'ID généré pour la commande
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 si l'ajout échoue
    }
    
    @Override
    public boolean updateCommande(Commande commande) {
        String sql = "UPDATE commande SET statut = ?, date = ?, heure = ?, total = ?, consommateur_id = ? WHERE id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, commande.getStatut());
            ps.setString(2, commande.getDate());
            ps.setString(3, commande.getHeure());
            ps.setFloat(4, commande.getTotal());
            ps.setLong(5, commande.getConsommateur_id());
            ps.setLong(6, commande.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Commande getCommandeById(long commandeId) {
        String sql = "SELECT * FROM commande WHERE id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, commandeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Commande commande = new Commande();
                    commande.setId(rs.getLong("id"));
                    commande.setStatut(rs.getString("statut"));
                    commande.setDate(rs.getString("date"));
                    commande.setHeure(rs.getString("heure"));
                    commande.setTotal((int) rs.getFloat("total"));
                    commande.setConsommateur_id(rs.getLong("consommateur_id"));
                    return commande;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Commande> getCommandesParConsommateur(long consommateurId) throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT * FROM Commande WHERE consommateur_id = ?";
        
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, consommateurId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Commande commande = new Commande();
                    commande.setId(resultSet.getLong("id"));
                    commande.setConsommateur_id(resultSet.getLong("consommateur_id"));
                    commande.setDate(resultSet.getString("date"));
                    commande.setHeure(resultSet.getString("heure"));
                    commande.setTotal((int) resultSet.getDouble("total"));
                    commande.setStatut(resultSet.getString("statut"));
                    commandes.add(commande);
                }
            }
        }
        return commandes;
    }
    public void addCommandeProduits(int commandeId, List<Panier> paniers, List<Integer> produitIds) {
        String sqlInsertProduit = "INSERT INTO commande_produit (commande_id, produit_id, quantite) VALUES (?, ?, ?)";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement psProduit = connection.prepareStatement(sqlInsertProduit)) {

        	Map<Integer, Integer> produitQuantites = new HashMap<>();

        	// Ajouter les produits du panier
        	for (Panier panier : paniers) {
        	    if (panier.getProduitId() != null) { // Si un produit est associé
        	        int produitId = panier.getProduitId().intValue(); // Convert Long to int
        	        int quantite = panier.getQuantite();
        	        produitQuantites.put(
        	            produitId,
        	            produitQuantites.getOrDefault(produitId, 0) + quantite
        	        );
        	    }
        	}

        	// Calculer les quantités totales des produits associés aux offres
        	for (Integer produitId : produitIds) {
        	    produitQuantites.put(
        	        produitId,
        	        produitQuantites.getOrDefault(produitId, 0) + 1 // Chaque occurrence dans la liste ajoute 1 à la quantité
        	    );
        	}


            // Insérer les produits avec leurs quantités dans la table commande_produit
            for (Map.Entry<Integer, Integer> entry : produitQuantites.entrySet()) {
                psProduit.setInt(1, commandeId);
                psProduit.setInt(2, entry.getKey()); // Produit ID
                psProduit.setInt(3, entry.getValue()); // Quantité totale
                psProduit.addBatch();
            }

            // Exécuter toutes les insertions
            psProduit.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout des produits à la commande.");
        }
    }

    public void updateProduitQuantite(List<Panier> paniers, List<Integer> produitIds) {
        String sqlUpdateProduit = "UPDATE produit SET quantite = quantite - ? WHERE id = ? AND quantite >= ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement psUpdate = connection.prepareStatement(sqlUpdateProduit)) {

            // Map pour stocker les quantités totales par produit
            Map<Integer, Integer> produitQuantites = new HashMap<>();

            // Ajouter les quantités des produits dans les paniers
            for (Panier panier : paniers) {
                if (panier.getProduitId() != null) { // Vérifier si un produit est associé
                    int produitId = panier.getProduitId().intValue();
                    int quantite = panier.getQuantite();
                    produitQuantites.put(
                        produitId,
                        produitQuantites.getOrDefault(produitId, 0) + quantite
                    );
                }
            }

            // Ajouter les quantités des produits dans la liste produitIds
            for (Integer produitId : produitIds) {
                produitQuantites.put(
                    produitId,
                    produitQuantites.getOrDefault(produitId, 0) + 1 // Chaque occurrence ajoute 1 à la quantité
                );
            }

            // Mettre à jour la table produit
            for (Map.Entry<Integer, Integer> entry : produitQuantites.entrySet()) {
                psUpdate.setInt(1, entry.getValue()); // Quantité à réduire
                psUpdate.setInt(2, entry.getKey());   // ID du produit
                psUpdate.setInt(3, entry.getValue()); // Vérifier qu'il y a assez de stock
                psUpdate.addBatch();
            }

            // Exécuter toutes les mises à jour
            int[] updateCounts = psUpdate.executeBatch();

            // Vérifier si toutes les mises à jour ont réussi
            for (int count : updateCounts) {
                if (count == 0) {
                    throw new RuntimeException("Erreur : Un ou plusieurs produits n'ont pas assez de stock.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour des quantités des produits.");
        }
    }

    


}
