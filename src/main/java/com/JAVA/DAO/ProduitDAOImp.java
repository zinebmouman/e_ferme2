package com.JAVA.DAO;

import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Promotion;
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


    
}
