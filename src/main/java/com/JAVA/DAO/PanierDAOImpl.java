package com.JAVA.DAO;

import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;
import com.JAVA.utils.DAOFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierDAOImpl implements PanierDAO {
    private DAOFactory daoFactory;

    public PanierDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterAuPanier(Panier panier) throws SQLException {
        String query = "INSERT INTO panier (consommateur_id, produit_id, quantite, prix, offre_id, promotion_id) VALUES (?, ?, ?, ?, ?, ?)";

        // Vérifier la validité des paramètres
        if (panier == null || panier.getConsommateurId() == null || panier.getPrix() == 0) {
            throw new SQLException("Les informations du panier sont invalides.");
        }

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, panier.getConsommateurId()); // consommateur_id

            // Gestion de produit_id null
            if (panier.getProduitId() != null) {
                statement.setLong(2, panier.getProduitId()); // produit_id
            } else {
                statement.setNull(2, Types.BIGINT);
            }

            statement.setInt(3, panier.getQuantite()); // quantite
            statement.setDouble(4, panier.getPrix()); // prix (vous pouvez remplacer par BigDecimal si nécessaire)

            // Gestion de offre_id null
            if (panier.getOffreId() != null) {
                statement.setLong(5, panier.getOffreId()); // offre_id
            } else {
                statement.setNull(5, Types.BIGINT);
            }

            // Gestion de promotion_id null
            if (panier.getPromotionId() != null) {
                statement.setLong(6, panier.getPromotionId()); // promotion_id
            } else {
                statement.setNull(6, Types.BIGINT);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("L'ajout au panier a échoué.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'ajout au panier", e);
        }
    }


    // Méthode pour récupérer le prix promotionnel ou le prix d'une offre
    public double getPrixReduit(Long produitId, Long offreId, Long promotionId) throws SQLException {
        double prixReduit = 0.0;
        String query;

        if (offreId != null) {
            query = "SELECT prix_pack FROM offre WHERE id = ?";
            try (Connection connection = daoFactory.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, offreId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        prixReduit = resultSet.getDouble("prix_pack");
                    }
                }
            }
        } else if (promotionId != null) {
            query = "SELECT p.prix * (1 - pr.taux_promotion / 100) AS prix_reduit FROM produit p JOIN promotion pr ON p.id = ? WHERE pr.id = ?";
            try (Connection connection = daoFactory.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, produitId);
                statement.setLong(2, promotionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        prixReduit = resultSet.getDouble("prix_reduit");
                    }
                }
            }
        }

        return prixReduit;
    }

    @Override
    public void modifierQuantite(Long produitId, Long offreId, Long consommateurId, int nouvelleQuantite) throws SQLException {
        String queryProduit = "SELECT quantite FROM produit WHERE id = ?";
        String queryOffreProduits = "SELECT p.id, p.quantite FROM produit p JOIN offre_produit op ON p.id = op.produit_id WHERE op.offre_id = ?";
        String queryUpdatePanierProduit = "UPDATE panier SET quantite = ? WHERE produit_id = ? AND consommateur_id = ?";
        String queryUpdatePanierOffre = "UPDATE panier SET quantite = ? WHERE offre_id = ? AND consommateur_id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statementProduit = connection.prepareStatement(queryProduit);
             PreparedStatement statementOffreProduits = connection.prepareStatement(queryOffreProduits);
             PreparedStatement statementUpdatePanierProduit = connection.prepareStatement(queryUpdatePanierProduit);
             PreparedStatement statementUpdatePanierOffre = connection.prepareStatement(queryUpdatePanierOffre)) {

            // Vérification pour produit_id
            if (produitId != null) {
                statementProduit.setLong(1, produitId);
                try (ResultSet resultSet = statementProduit.executeQuery()) {
                    if (resultSet.next()) {
                        int quantiteDisponible = resultSet.getInt("quantite");
                        if (nouvelleQuantite > quantiteDisponible) {
                            throw new SQLException("La quantité demandée n'est pas disponible pour ce produit.");
                        }
                    } else {
                        throw new SQLException("Produit non trouvé.");
                    }
                }
                statementUpdatePanierProduit.setInt(1, nouvelleQuantite);
                statementUpdatePanierProduit.setLong(2, produitId);
                statementUpdatePanierProduit.setLong(3, consommateurId);
                if (statementUpdatePanierProduit.executeUpdate() == 0) {
                    throw new SQLException("La mise à jour de la quantité pour le produit a échoué.");
                }
            }

            // Vérification pour offre_id
            if (offreId != null) {
                statementOffreProduits.setLong(1, offreId);
                boolean quantiteDisponible = true;
                try (ResultSet resultSet = statementOffreProduits.executeQuery()) {
                    while (resultSet.next()) {
                        int quantiteProduit = resultSet.getInt("quantite");
                        if (nouvelleQuantite > quantiteProduit) {
                            quantiteDisponible = false;
                            break; // Si une quantité est insuffisante, on arrête la vérification
                        }
                    }
                }
                if (!quantiteDisponible) {
                    throw new SQLException("La quantité demandée n'est pas disponible pour un ou plusieurs produits de l'offre.");
                }

                // Mise à jour de la quantité dans le panier pour tous les produits de l'offre
                statementUpdatePanierOffre.setInt(1, nouvelleQuantite);
                statementUpdatePanierOffre.setLong(2, offreId);
                statementUpdatePanierOffre.setLong(3, consommateurId);
                if (statementUpdatePanierOffre.executeUpdate() == 0) {
                    throw new SQLException("La mise à jour de la quantité pour l'offre a échoué.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Panier> getPanierParConsommateur(Long consommateurId) throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM panier WHERE consommateur_id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, consommateurId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Créer un objet Panier et remplir avec les données de la base
                    Panier panier = new Panier();
                    panier.setId(resultSet.getLong("id"));
                    panier.setConsommateurId(resultSet.getLong("consommateur_id"));
                    panier.setProduitId(resultSet.getObject("produit_id", Long.class));  // Peut être null
                    panier.setOffreId(resultSet.getObject("offre_id", Long.class));      // Peut être null
                    panier.setQuantite(resultSet.getInt("quantite"));
                    panier.setPrix(resultSet.getDouble("prix"));  // Assurez-vous que le prix est correctement récupéré comme double
                    panier.setPromotionId(resultSet.getObject("promotion_id", Long.class));  // Peut être null

                    // Ajouter le panier à la liste
                    paniers.add(panier);
                }
            }
        }
        return paniers;
    }




    @Override
    public void supprimerDuPanier(Long produitId, Long offreId, Long consommateurId) throws SQLException {
        String querySupprimer = "DELETE FROM panier WHERE (produit_id = ? OR offre_id = ?) AND consommateur_id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(querySupprimer)) {
            statement.setObject(1, produitId);
            statement.setObject(2, offreId);
            statement.setLong(3, consommateurId);
            statement.executeUpdate();
        }
    }





    
    
}
