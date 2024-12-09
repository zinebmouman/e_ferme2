package com.JAVA.DAO;

import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Promotion;
import com.JAVA.Beans.Reclamation;
import com.JAVA.Beans.Categorie;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.utils.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAOImp {
    private DAOFactory daoFactory;

    public ProduitDAOImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<Produit> getAllProduits() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Produit produit = new Produit();
                produit.setIdProduit((long) resultSet.getInt("id"));
                produit.setNom(resultSet.getString("nom"));
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setQuantite(resultSet.getInt("quantite"));
                produit.setDescription(resultSet.getString("descreption"));
                produit.setImage(resultSet.getString("image"));
                produit.setDateRecolte(resultSet.getDate("date_recolte"));
                produit.setUserId((long) resultSet.getInt("user_id"));

                produits.add(produit);
            }
        }

        return produits;
    }
    
    public List<Produit> getProduitsByFermier(Long idFermier) {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE user_id = ?";
        
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, idFermier);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getLong("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setDescription(resultSet.getString("descreption"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setDateRecolte(resultSet.getDate("date_recolte"));
                    produit.setUserId(resultSet.getLong("user_id"));
                    produit.setid_categorie(resultSet.getLong("id_categorie"));

                    produits.add(produit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return produits;
    }

    public List<Produit> getProduitsAvecPromotion() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.*, promo.id, promo.taux, promo.date_debut, promo.date_fin, promo.description AS promo_description " +
                       "FROM produit p JOIN promotion promo ON p.id = promo.id_produit";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Produit produit = new Produit();
                produit.setIdProduit((long) resultSet.getInt("id"));
                produit.setNom(resultSet.getString("nom"));
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setQuantite(resultSet.getInt("quantite"));
                produit.setDescription(resultSet.getString("descreption"));
                produit.setImage(resultSet.getString("image"));
                produit.setDateRecolte(resultSet.getDate("date_recolte"));
                produit.setUserId((long) resultSet.getInt("user_id"));

                Promotion promotion = new Promotion();
                promotion.setIdPromotion(resultSet.getLong("id"));
                promotion.setDateDebut(resultSet.getDate("date_debut"));
                promotion.setDateFin(resultSet.getDate("date_fin"));
                promotion.setTaux(resultSet.getDouble("taux"));
                promotion.setDescription(resultSet.getString("promo_description"));

                produit.setPromotion(promotion);

                produits.add(produit);
            }
        }

        return produits;
    }

    public List<Produit> getProduitsAvecOffre() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.*, o.nom AS offre_nom, o.prix_pack, o.taux_reduction, o.date_debut, o.date_fin, o.description AS offre_description " +
                       "FROM produit p " +
                       "JOIN offre_produit op ON p.id = op.produit_id " +
                       "JOIN offre o ON op.offre_id = o.id";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Produit produit = new Produit();
                produit.setIdProduit((long) resultSet.getInt("id"));
                produit.setNom(resultSet.getString("nom"));
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setQuantite(resultSet.getInt("quantite"));
                produit.setDescription(resultSet.getString("descreption"));
                produit.setImage(resultSet.getString("image"));
                produit.setDateRecolte(resultSet.getDate("date_recolte"));
                produit.setUserId((long) resultSet.getInt("user_id"));

                Offre offre = new Offre();
                offre.setNom(resultSet.getString("offre_nom"));
                offre.setPrixPack(resultSet.getDouble("prix_pack"));
                offre.setTauxReduction(resultSet.getDouble("taux_reduction"));
                offre.setDateDebut(resultSet.getDate("date_debut"));
                offre.setDateFin(resultSet.getDate("date_fin"));
                offre.setDescription(resultSet.getString("offre_description"));

                produit.setOffre(offre);

                produits.add(produit);
            }
        }

        return produits;
    }
    public List<Categorie> getAllCategories() throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie"; // Assurez-vous que cette requête correspond à votre base de données

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(resultSet.getLong("id_categorie"));
                categorie.setNom(resultSet.getString("nom_categorie"));
                categorie.setdescription_categorie(resultSet.getString("description_categorie"));
                categories.add(categorie);
            }
        }

        System.out.println("Catégories récupérées: " + categories); // Ajoutez cette ligne pour vérifier
        return categories;
    }

    public List<Produit> getProduitsParCategorie(long idCategorie) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE id_categorie = ?";
        System.out.println("Requête SQL: " + query);  // Ajoutez cette ligne pour vérifier la requête

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idCategorie);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getLong("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setDescription(resultSet.getString("descreption"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setDateRecolte(resultSet.getDate("date_recolte"));
                    produit.setUserId(resultSet.getLong("user_id"));

                    produits.add(produit);
                }
            }
        }
        System.out.println("Produits par catégorie: " + produits); // Ajoutez cette ligne pour vérifier les résultats
        return produits;
    }

    
    public Produit getProduitByID(long idProduit) throws SQLException {
        Produit produit = null;
        String query = "SELECT * FROM produit WHERE id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idProduit);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    produit = new Produit();
                    produit.setIdProduit(resultSet.getLong("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setQuantite(resultSet.getInt("quantite"));
                    produit.setDescription(resultSet.getString("descreption"));
                    produit.setImage(resultSet.getString("image"));
                    produit.setDateRecolte(resultSet.getDate("date_recolte"));
                    produit.setUserId(resultSet.getLong("user_id"));
                }
            }
        }
        return produit;
    }
    public List<Produit> getProduitsParIds(List<Panier> paniers) throws SQLException {
        List<Produit> produits = new ArrayList<>();
        for (Panier panier : paniers) {
            Produit produit = getProduitByID(panier.getProduitId());
            if (produit != null) {
                produits.add(produit);
            }
        }
        return produits;
    }
 // Méthode pour récupérer toutes les offres
    public List<Offre> getAllOffres() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String query = "SELECT id, nom, date_debut, date_fin,  prix_pack, taux_reduction , description FROM offre";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setIdOffre(resultSet.getLong("id"));
                offre.setNom(resultSet.getString("nom"));
                offre.setDateDebut(resultSet.getDate("date_debut"));
                offre.setDateFin(resultSet.getDate("date_fin"));
                offre.setPrixPack(resultSet.getDouble("prix_pack"));
                offre.setTauxReduction(resultSet.getDouble("taux_reduction"));
                offre.setDescription(resultSet.getString("description"));
                offres.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des offres.");
        }

        return offres;
    }

    // Méthode pour récupérer les produits par offreId
    public List<Produit> getProduitsAvecOffre(Long offreId) throws SQLException {
        List<Produit> produitsAvecOffre = new ArrayList<>();
        String query = "SELECT p.id, p.nom, p.prix, p.descreption, p.image, p.date_recolte, p.user_id, p.id_categorie, " +
                       "of.nom AS offre_nom, of.prix_pack, of.taux_reduction " +
                       "FROM produit p " +
                       "JOIN offre_produit op ON p.id = op.produit_id " +
                       "JOIN offre of ON op.offre_id = of.id " +
                       "WHERE of.id = ?";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, offreId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit();
                    produit.setIdProduit(resultSet.getLong("id"));
                    produit.setNom(resultSet.getString("nom"));
                    produit.setPrix(resultSet.getDouble("prix"));
                    produit.setDescription(resultSet.getString("descreption"));
                    produit.setImage(resultSet.getString("image"));  // Récupérer l'image (qui peut être null)
                    produit.setDateRecolte(resultSet.getDate("date_recolte")); // Récupérer la date de récolte
                    produit.setUserId(resultSet.getLong("user_id")); // Récupérer userId (qui peut être null)
                    produit.setIdCategorie(resultSet.getInt("id_categorie")); // Récupérer la catégorie

                    // Si la catégorie est null, vous pouvez la définir à une valeur par défaut comme "Non définie"
                    if (produit.getCategorie() == null) {
                        produit.setIdCategorie((Integer) null);
                    }

                    // Gérer les produits avec des valeurs nulles pour l'image, dateRecolte ou userId
                    if (produit.getImage() == null) {
                        produit.setImage("image_par_defaut.jpg"); // Valeur par défaut pour l'image
                    }

                    // Création de l'objet Offre pour associer à chaque produit
                    Offre offre = new Offre();
                    offre.setNom(resultSet.getString("offre_nom"));
                    offre.setPrixPack(resultSet.getDouble("prix_pack"));
                    offre.setTauxReduction(resultSet.getDouble("taux_reduction"));

                    produit.setOffre(offre);
                    produitsAvecOffre.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des produits avec l'offre ID: " + offreId);
        }

        return produitsAvecOffre;
    }


    public List<Promotion> getAllPromotions() throws SQLException {
        List<Promotion> promotions = new ArrayList<>();
        String query = "SELECT id, date_debut, date_fin, taux, description, id_produit FROM promotion";

        try (Connection connection = DAOFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                Promotion promotion = new Promotion();
                promotion.setIdPromotion(resultSet.getLong("id"));
                promotion.setDateDebut(resultSet.getDate("date_debut"));
                promotion.setDateFin(resultSet.getDate("date_fin"));
                promotion.setTaux(resultSet.getDouble("taux"));
                promotion.setDescription(resultSet.getString("description"));
                promotion.setIdProduit(resultSet.getLong("id_produit"));
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de la récupération des promotions.");
        }

        return promotions;
    }


    public Offre getOffreByID(long offreId) throws SQLException {
        Offre offre = null;
        String query = "SELECT * FROM offre WHERE id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, offreId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    offre = new Offre();
                    offre.setIdOffre(resultSet.getLong("id"));
                    offre.setNom(resultSet.getString("nom"));
                    offre.setDescription(resultSet.getString("description"));
                    offre.setPrixPack(resultSet.getDouble("prix_pack"));
                    offre.setTauxReduction(resultSet.getDouble("taux_reduction"));
                    offre.setDateDebut(resultSet.getDate("date_debut"));
                    offre.setDateFin(resultSet.getDate("date_fin"));
                }
            }
        }
        return offre;
    }

    public boolean verifierQuantiteDisponible(Long produitId, Long offreId, int quantite) throws SQLException {
        String queryProduit = "SELECT quantite FROM produit WHERE id = ?";
        String queryOffre = "SELECT produit_id FROM offre_produit WHERE offre_id = ?";
        
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statementProduit = connection.prepareStatement(queryProduit);
             PreparedStatement statementOffre = connection.prepareStatement(queryOffre)) {
            
            // Si produitId est fourni, vérifier la quantité du produit spécifique
            if (produitId != null) {
                statementProduit.setLong(1, produitId);
                try (ResultSet resultSet = statementProduit.executeQuery()) {
                    if (resultSet.next()) {
                        int quantiteDisponible = resultSet.getInt("quantite");
                        return quantite <= quantiteDisponible; // Retourne true si la quantité est suffisante
                    } else {
                        return false; // Produit non trouvé
                    }
                }
            } 
            // Si offreId est fourni, vérifier les quantités des produits dans l'offre
            else if (offreId != null) {
                statementOffre.setLong(1, offreId);
                try (ResultSet resultSet = statementOffre.executeQuery()) {
                    while (resultSet.next()) {
                        Long idProduit = resultSet.getLong("produit_id");

                        // Vérifier la quantité disponible pour chaque produit dans l'offre
                        statementProduit.setLong(1, idProduit);
                        try (ResultSet produitResultSet = statementProduit.executeQuery()) {
                            if (produitResultSet.next()) {
                                int quantiteDisponible = produitResultSet.getInt("quantite");
                                if (quantite > quantiteDisponible) {
                                    return false; // Pas assez de stock pour ce produit
                                }
                            } else {
                                return false; // Produit dans l'offre non trouvé
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow exception après l'avoir loggée
        }
        
        return true; // Quantité suffisante pour tous les produits vérifiés
    }


    public void ajouterCategorie(Categorie categorie) {
        String sql = "INSERT INTO categorie (nom_categorie, description_categorie) VALUES (?, ?)";
           try (Connection connection = daoFactory.getConnection();
               
        		PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categorie.getNom());
            ps.setString(2, categorie.getdescription_categorie());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT r.id AS idReclamation, r.Contenu, r.date AS dateReclamation, " +
                     "r.consommateur_id, u.Nom AS nomConsommateur, u.email AS emailConsommateur " +
                     "FROM reclamations r " +
                     "JOIN user u ON r.consommateur_id = u.id " +
                     "WHERE u.type = 4";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getLong("idReclamation"));
                reclamation.setDescription(rs.getString("Contenu"));
                reclamation.setDateReclamation(rs.getString("dateReclamation"));
                reclamation.setIdConsommateur(rs.getLong("consommateur_id"));
                reclamation.setNomConsommateur(rs.getString("nomConsommateur"));
                reclamation.setEmailConsommateur(rs.getString("emailConsommateur"));
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des réclamations", e);
        }
        return reclamations;
    }

    public List<Categorie> getCategories() {
        String sql = "SELECT * FROM categorie";
        List<Categorie> categories = new ArrayList<>();

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(rs.getLong("id_categorie"));
                categorie.setNom(rs.getString("nom_categorie"));
                categorie.setdescription_categorie(rs.getString("description_categorie"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
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

    public List<Produit> getProduitsParCategorie(Long idCategorie) {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit WHERE id_categorie = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idCategorie);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProduit(rs.getLong("id"));
                produit.setNom(rs.getString("nom"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setDescription(rs.getString("description"));
                produit.setImage(rs.getString("image"));
                produit.setid_categorie(rs.getLong("id_categorie"));

                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }
 
    public void ajouterPromotion(Promotion promotion) {
        String query = "INSERT INTO promotion (date_debut, date_fin, taux, description, id_produit) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(promotion.getDateDebut().getTime()));
            statement.setDate(2, new java.sql.Date(promotion.getDateFin().getTime()));
            statement.setDouble(3, promotion.getTaux());
            statement.setString(4, promotion.getDescription());
            statement.setLong(5, promotion.getIdProduit());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Produit> getProduitsEnPromotionParFermier(Long idFermier) {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT p.id AS produit_id, p.nom, p.prix, p.quantite, p.descreption AS description, " +
                       "p.image, p.date_recolte, " +
                       "pr.id AS promotion_id, pr.date_debut, pr.date_fin, pr.taux, pr.description AS promotion_description " +
                       "FROM produit p " +
                       "INNER JOIN promotion pr ON p.id = pr.id_produit " +
                       "WHERE p.user_id = ?";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, idFermier);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setIdProduit(rs.getLong("produit_id"));
                produit.setNom(rs.getString("nom"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setQuantite(rs.getInt("quantite"));
                produit.setDescription(rs.getString("description"));
                produit.setImage(rs.getString("image"));
                produit.setDateRecolte(rs.getDate("date_recolte"));

                Promotion promotion = new Promotion();
                promotion.setIdPromotion(rs.getLong("promotion_id"));
                promotion.setDateDebut(rs.getDate("date_debut"));
                promotion.setDateFin(rs.getDate("date_fin"));
                promotion.setTaux(rs.getDouble("taux"));
                promotion.setDescription(rs.getString("promotion_description"));

                produit.setPromotion(promotion);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }

  
    public List<Promotion> getPromotionsByProduit(Long idProduit) {
        List<Promotion> promotions = new ArrayList<>();
        String query = "SELECT * FROM promotion WHERE id_produit = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idProduit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Promotion promotion = new Promotion(
                            resultSet.getLong("id"),
                            resultSet.getDate("date_debut"),
                            resultSet.getDate("date_fin"),
                            resultSet.getDouble("taux"),
                            resultSet.getString("description"),
                            resultSet.getLong("id_produit")
                    );
                    promotions.add(promotion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }
}
    

