package com.JAVA.DAO;

import java.sql.*;
import com.JAVA.Beans.Commande;
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
}
