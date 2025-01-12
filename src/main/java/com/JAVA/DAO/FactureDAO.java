package com.JAVA.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.JAVA.utils.DAOFactory;

public class FactureDAO {

	private final DAOFactory daoFactory;

    public FactureDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
	
	
    public boolean ajouterFacture(int commandeId, int quantiteProduit, double prixTotal, String dateFacture, String statutPaiement) {
        String sql = "INSERT INTO facture (commande_id, quantite_produit, prix_total, date_facture, statut_paiement) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, commandeId);
            ps.setInt(2, quantiteProduit);
            ps.setDouble(3, prixTotal);
            ps.setString(4, dateFacture);
            ps.setString(5, statutPaiement);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
