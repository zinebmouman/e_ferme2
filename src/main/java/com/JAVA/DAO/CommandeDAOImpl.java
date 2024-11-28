package com.JAVA.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

}
