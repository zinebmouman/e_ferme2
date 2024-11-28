package com.JAVA.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.JAVA.utils.DAOFactory;

public class ReclamationDAOImp implements ReclamationDAO {
    private DAOFactory daoFactory;

    public ReclamationDAOImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean ajouterReclamation(String contenu, int consommateurId) throws SQLException {
        String sql = "INSERT INTO reclamations (Contenu, date, consommateur_id) VALUES (?, CURDATE(), ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, contenu);
            ps.setInt(2, consommateurId);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0; // Retourne vrai si l'insertion a réussi
        }
    }
}
