package com.JAVA.DAO;

import com.JAVA.Beans.Produit;
import com.JAVA.utils.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FermierDAOImpl implements FermierDAO {
    // Remplacer fermies_id par user_id dans la requête SQL
    private static final String INSERT_PRODUIT_SQL = "INSERT INTO produit (nom, prix, quantite, description, image, date_recolte, user_id) VALUES (?, ?, ?, ?, ?, ?, ?);";

    private DAOFactory daoFactory;

    public FermierDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addProduit(Produit produit) throws SQLException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUIT_SQL)) {

            statement.setString(1, produit.getNom());
            statement.setDouble(2, produit.getPrix());
            statement.setInt(3, produit.getQuantite());
            statement.setString(4, produit.getDescription());
            statement.setString(5, produit.getImage());
            statement.setDate(6, new java.sql.Date(produit.getDateRecolte().getTime()));
            // Remplacer fermiesId par userId
            statement.setLong(7, produit.getUserId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Aucun produit ajouté, aucune ligne affectée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Afficher les erreurs SQL pour le débogage
            throw e;  // Rejeter l'exception pour la servlet
        }
    }
}
