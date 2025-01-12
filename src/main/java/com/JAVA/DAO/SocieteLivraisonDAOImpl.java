package com.JAVA.DAO;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.Societedelivraison;
import com.JAVA.utils.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocieteLivraisonDAOImpl implements SocieteLivraisonDAO {

    private final DAOFactory daoFactory;

    public SocieteLivraisonDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addSociete(Societedelivraison societe) throws SQLException {
        String addUserQuery = """
                INSERT INTO user (Nom, email, login, password, telephone, type) 
                VALUES (?, ?, ?, ?, ?, 3)
                """;
        String addSocieteQuery = "INSERT INTO societedelivraison (id, zone_livraison) VALUES (?, ?)";

        try (Connection conn = daoFactory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUser = conn.prepareStatement(addUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, societe.getNom());
                stmtUser.setString(2, societe.getEmail());
                stmtUser.setString(3, societe.getLogin());
                stmtUser.setString(4, societe.getPassword());
                stmtUser.setString(5, societe.getTelephone());
                stmtUser.executeUpdate();

                try (ResultSet rs = stmtUser.getGeneratedKeys()) {
                    if (rs.next()) {
                        societe.setId(rs.getLong(1));
                    }
                }
            }

            try (PreparedStatement stmtSociete = conn.prepareStatement(addSocieteQuery)) {
                stmtSociete.setLong(1, societe.getId());
                stmtSociete.setString(2, societe.getZoneLivraison());
                stmtSociete.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'ajout de la société", e);
        }
    }

    @Override
    public List<Societedelivraison> getAllSocietes() throws SQLException {
        List<Societedelivraison> societes = new ArrayList<>();
        String query = """
                SELECT s.id, s.zone_livraison, u.Nom, u.email, u.telephone
                FROM societedelivraison s
                INNER JOIN user u ON s.id = u.id
                """;

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Societedelivraison societe = new Societedelivraison();
                societe.setId(rs.getLong("id"));
                societe.setZoneLivraison(rs.getString("zone_livraison"));
                societe.setNom(rs.getString("Nom"));
                societe.setEmail(rs.getString("email"));
                societe.setTelephone(rs.getString("telephone"));
                societes.add(societe);
            }
        }

        return societes;
    }

    @Override
    public Societedelivraison getSocieteById(int id) throws SQLException {
        Societedelivraison societe = null;
        String query = """
                SELECT s.id, s.zone_livraison, u.Nom, u.email, u.telephone
                FROM societedelivraison s
                INNER JOIN user u ON s.id = u.id
                WHERE s.id = ?
                """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    societe = new Societedelivraison();
                    societe.setId(rs.getLong("id"));
                    societe.setZoneLivraison(rs.getString("zone_livraison"));
                    societe.setNom(rs.getString("Nom"));
                    societe.setEmail(rs.getString("email"));
                    societe.setTelephone(rs.getString("telephone"));
                }
            }
        }

        return societe;
    }

    @Override
    public void updateSociete(Societedelivraison societe) throws SQLException {
        String updateUserQuery = """
                UPDATE user SET Nom = ?, email = ?, telephone = ? WHERE id = ?
                """;
        String updateSocieteQuery = "UPDATE societedelivraison SET zone_livraison = ? WHERE id = ?";

        try (Connection conn = daoFactory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUser = conn.prepareStatement(updateUserQuery)) {
                stmtUser.setString(1, societe.getNom());
                stmtUser.setString(2, societe.getEmail());
                stmtUser.setString(3, societe.getTelephone());
                stmtUser.setLong(4, societe.getId());
                stmtUser.executeUpdate();
            }

            try (PreparedStatement stmtSociete = conn.prepareStatement(updateSocieteQuery)) {
                stmtSociete.setString(1, societe.getZoneLivraison());
                stmtSociete.setLong(2, societe.getId());
                stmtSociete.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour de la société", e);
        }
    }

    @Override
    public void deleteSociete(int id) throws SQLException {
        String deleteSocieteQuery = "DELETE FROM societedelivraison WHERE id = ?";
        String deleteUserQuery = "DELETE FROM user WHERE id = ?";

        try (Connection conn = daoFactory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtSociete = conn.prepareStatement(deleteSocieteQuery);
                 PreparedStatement stmtUser = conn.prepareStatement(deleteUserQuery)) {

                stmtSociete.setInt(1, id);
                stmtSociete.executeUpdate();

                stmtUser.setInt(1, id);
                stmtUser.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la suppression de la société", e);
        }
    }
    
    @Override
    public List<Commande> listerCommandesAvecDetails() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = """
                SELECT c.id, c.statut, c.date, c.heure, c.total, c.consommateur_id, 
                       u.Nom AS client_nom, u.email AS client_email, u.telephone AS client_telephone,
                       cl.address AS client_adresse
                FROM commande c
                INNER JOIN user u ON c.consommateur_id = u.id
                LEFT JOIN consommateur cl ON u.id = cl.id
                """;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId((long) rs.getInt("id"));
                commande.setStatut(rs.getString("statut"));
                commande.setDate(rs.getString("date"));
                commande.setHeure(rs.getString("heure"));
                commande.setTotal((int) rs.getDouble("total"));
                commande.setConsommateur_id((long) rs.getDouble("consommateur_id"));
                commande.setClientNom(rs.getString("client_nom"));
                commande.setClientEmail(rs.getString("client_email"));
                commande.setclientTelephone(rs.getString("client_telephone"));
                commande.setClientAdresse(rs.getString("client_adresse")); // Récupérer l'adresse
                commandes.add(commande);
            }
        }

        return commandes;
    }


    @Override
    public boolean mettreAJourStatutCommande(int idCommande, String nouveauStatut) throws SQLException {
        String query = "UPDATE commande SET statut = ? WHERE id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nouveauStatut);
            stmt.setInt(2, idCommande);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }


}
