package com.JAVA.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.JAVA.utils.DAOFactory;
import com.JAVA.Beans.Promotion;

public class PromotionDAO {
    private final DAOFactory daoFactory;

    public PromotionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Méthode pour récupérer la promotion par ID
    public Promotion getPromotionByID(Long promotionId) {
        String sql = "SELECT * FROM promotion WHERE id = ? AND CURRENT_DATE BETWEEN date_debut AND date_fin";
        Promotion promotion = null;

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, promotionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    promotion = new Promotion();
                    promotion.setIdPromotion((long) resultSet.getInt("id"));
                    promotion.setDateDebut(resultSet.getDate("date_debut"));
                    promotion.setDateFin(resultSet.getDate("date_fin"));
                    promotion.setTaux(resultSet.getDouble("taux"));
                    promotion.setDescription(resultSet.getString("description"));
                    promotion.setIdProduit((long) resultSet.getInt("id_produit"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de la promotion avec l'ID : " + promotionId);
        }

        return promotion;
    }
}
