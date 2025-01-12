package com.JAVA.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.utils.DAOFactory;

public class OffreProduitDAO {
    private final DAOFactory daoFactory;

    public OffreProduitDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<Integer> getProduitIdsParOffre(Long offreId) {
        String sql = "SELECT produit_id FROM offre_produit WHERE offre_id = ?";
        List<Integer> produitIds = new ArrayList<>();

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setLong(1, offreId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    produitIds.add(resultSet.getInt("produit_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des produits pour l'offre ID : " + offreId);
        }

        return produitIds;
    }
}
