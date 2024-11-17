package com.JAVA.DAO;

import com.JAVA.Beans.Consommateur;
import com.JAVA.utils.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsommateurDAOImpl implements ConsommateurDAO {
    private DAOFactory daoFactory;

    public ConsommateurDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouterConsommateur(Consommateur consommateur) throws SQLException {
        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement consommateurStmt = null;

        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false); // Début de transaction

            // Insérer dans la table `user`
            String userQuery = "INSERT INTO user (Nom, email, login, password, telephone, type) VALUES (?, ?, ?, ?, ?, ?)";
            userStmt = connection.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, consommateur.getNom());
            userStmt.setString(2, consommateur.getEmail());
            userStmt.setString(3, consommateur.getLogin());
            userStmt.setString(4, consommateur.getPassword());
            userStmt.setString(5, consommateur.getTelephone());
            userStmt.setLong(6, consommateur.getType());

            int affectedRows = userStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'utilisateur, aucune ligne ajoutée.");
            }

            // Récupérer l'ID généré
            try (var generatedKeys = userStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consommateur.setId(generatedKeys.getLong(1)); // Attribuer l'ID généré
                } else {
                    throw new SQLException("Échec de la création de l'utilisateur, aucun ID généré.");
                }
            }

            // Insérer dans la table `consommateur`
            String consommateurQuery = "INSERT INTO consommateur (id, address) VALUES (?, ?)";
            consommateurStmt = connection.prepareStatement(consommateurQuery);
            consommateurStmt.setLong(1, consommateur.getId()); // Utiliser l'ID de l'utilisateur
            consommateurStmt.setString(2, consommateur.getAddress());
            consommateurStmt.executeUpdate();

            connection.commit(); // Valider la transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Annuler la transaction en cas d'erreur
            }
            throw e;
        } finally {
            if (userStmt != null) userStmt.close();
            if (consommateurStmt != null) consommateurStmt.close();
            if (connection != null) {
                connection.setAutoCommit(true); // Réactiver l'auto-commit
                connection.close();
            }
        }
    }
}
