package com.JAVA.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.JAVA.Beans.CommandeProduit;
import com.JAVA.utils.DAOFactory;

public class CommandeProduitDAOImpl implements CommandeProduitDAO {
    private DAOFactory daoFactory;

    public CommandeProduitDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addCommandeProduit(CommandeProduit commandeProduit) {
        String sql = "INSERT INTO commande_produit (commande_id, produit_id, quantite) VALUES (?, ?, ?)";
        
        // Vérification si l'objet CommandeProduit est valide
        if (commandeProduit == null) {
            throw new IllegalArgumentException("Le produit de commande ne peut pas être nul");
        }

        try (Connection connection = daoFactory.getConnection();  // Récupération de la connexion
             PreparedStatement ps = connection.prepareStatement(sql)) {
             
            // Préparation et exécution de la requête SQL
            ps.setLong(1, commandeProduit.getCommande_id());  // ID de la commande
            ps.setLong(2, commandeProduit.getProduit_id());   // ID du produit
            ps.setInt(3, commandeProduit.getQuantite());      // Quantité

            ps.executeUpdate();  // Exécution de la requête
        } catch (SQLException e) {
            // Log de l'erreur pour le débogage
            e.printStackTrace();
        }
    }
}
