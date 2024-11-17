package com.JAVA.DAO;

import com.JAVA.Beans.Categorie;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Produit;
import java.sql.SQLException;
import java.util.List;

public interface ProduitDAO {
    List<Produit> getAllProduits() throws SQLException;
    List<Produit> getProduitsAvecPromotion() throws SQLException;
    List<Produit> getProduitsAvecOffre() throws SQLException;
    List<Categorie> getAllCategories() throws SQLException;
    List<Produit> getProduitsParCategorie(long idCategorie) throws SQLException;
    Produit getProduitByID(long idProduit) throws SQLException;
    Offre getOffreByID(long offreId) throws SQLException;
}
