package com.JAVA.DAO;

import com.JAVA.Beans.Fermies;
import com.JAVA.Beans.Produit;
import com.JAVA.utils.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FermierDAOImpl implements FermierDAO {
    private static final String INSERT_PRODUIT_SQL =
        "INSERT INTO produit (nom, prix, quantite, description, image, date_recolte, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_USER_SQL =
        "INSERT INTO user (Nom, email, login, password, telephone, type) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_FERMIER_SQL =
        "INSERT INTO fermies (id, address, type_fermies) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_FERMIERS_SQL =
        "SELECT u.id, u.Nom, u.email, u.login, u.telephone, f.address, f.type_fermies " +
        "FROM user u " +
        "JOIN fermies f ON u.id = f.id " +
        "WHERE u.type = 2";
    private static final String UPDATE_FERMIER_SQL =
        "UPDATE fermies SET address = ?, type_fermies = ? WHERE id = ?";
    private static final String DELETE_FERMIER_SQL =
        "DELETE FROM fermies WHERE id = ?";
    private static final String SELECT_FERMIER_BY_ID_SQL =
        "SELECT u.id, u.Nom, u.email, u.login, u.telephone, f.address, f.type_fermies " +
        "FROM user u " +
        "JOIN fermies f ON u.id = f.id WHERE u.id = ?";

    private final DAOFactory daoFactory;

    public FermierDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addProduit(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit (nom, prix, quantite, descreption, image, date_recolte, user_id, id_categorie) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, produit.getNom());
            statement.setDouble(2, produit.getPrix());
            statement.setInt(3, produit.getQuantite());
            statement.setString(4, produit.getDescription());
            statement.setString(5, produit.getImage());      
            statement.setDate(6, new java.sql.Date(produit.getDateRecolte().getTime()));
            statement.setLong(7, produit.getUserId());
            statement.setLong(8, produit.getid_categorie());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Échec de l'ajout du produit, aucune ligne ajoutée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
            throw e;
        }
    }



   
    @Override
    public List<Produit> getProduitsParFermier(Long idFermier) {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE user_id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramétrage de la requête
            statement.setLong(1, idFermier);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getLong("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setDescription(resultSet.getString("description")); // Assurez-vous que c'est correct dans la base
                    produit.setImage(resultSet.getString("image"));
                    produit.setDateRecolte(resultSet.getDate("date_recolte"));
                    produit.setUserId(resultSet.getLong("user_id"));

                    produits.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Vous pouvez également lever une exception personnalisée ou gérer l'erreur différemment
        }

        return produits;
    }




    @Override
    public void addFermier(Fermies fermier) throws SQLException {
        try (Connection connection = daoFactory.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement userStmt = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, fermier.getNom());
                userStmt.setString(2, fermier.getEmail());
                userStmt.setString(3, fermier.getLogin());
                userStmt.setString(4, fermier.getPassword());
                userStmt.setString(5, fermier.getTelephone());
                userStmt.setInt(6, 2); // Type = 2 pour Fermier

                userStmt.executeUpdate();

                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        fermier.setId(generatedKeys.getLong(1));
                    } else {
                        connection.rollback();
                        throw new SQLException("Échec de l'obtention de l'ID généré pour le fermier.");
                    }
                }
            }

            try (PreparedStatement fermierStmt = connection.prepareStatement(INSERT_FERMIER_SQL)) {
                fermierStmt.setLong(1, fermier.getId());
                fermierStmt.setString(2, fermier.getAddress());
                fermierStmt.setString(3, fermier.getTypeFermies());
                fermierStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'ajout du fermier : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Fermies> getAllFermiers() throws SQLException {
        List<Fermies> fermiers = new ArrayList<>();

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_FERMIERS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fermies fermier = new Fermies(
                    rs.getLong("id"),
                    rs.getString("Nom"),
                    rs.getString("email"),
                    rs.getString("login"),
                    null, // Le mot de passe n'est pas retourné
                    rs.getString("telephone"),
                    null,
                    rs.getString("address"),
                    rs.getString("type_fermies")
                );
                fermiers.add(fermier);
            }
        }

        return fermiers;
    }

    @Override
    public void updateFermier(Fermies fermier) throws SQLException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(UPDATE_FERMIER_SQL)) {

            stmt.setString(1, fermier.getAddress());
            stmt.setString(2, fermier.getTypeFermies());
            stmt.setLong(3, fermier.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteFermier(Long id) throws SQLException {
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_FERMIER_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Fermies getFermierById(Long id) throws SQLException {
        Fermies fermier = null;

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_FERMIER_BY_ID_SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fermier = new Fermies(
                        rs.getLong("id"),
                        rs.getString("Nom"),
                        rs.getString("email"),
                        rs.getString("login"),
                        null,
            
                        rs.getString("telephone"),
                        null,
                        rs.getString("address"),
                        rs.getString("type_fermies")
                        
                    );
                }
            }
        }

        return fermier;
    }
}
