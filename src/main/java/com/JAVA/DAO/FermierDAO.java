package com.JAVA.DAO;

import com.JAVA.Beans.Fermies;
import com.JAVA.Beans.Produit;
import java.sql.SQLException;
import java.util.List;

public interface FermierDAO {
    void addProduit(Produit produit) throws SQLException;
    public List<Produit> getProduitsParFermier(Long idFermier);
    // Autres méthodes si nécessaire, par exemple : getProduitById, updateProduit, deleteProduit
   void addFermier(Fermies fermier) throws SQLException;
    List<Fermies> getAllFermiers() throws SQLException;
    void updateFermier(Fermies fermier) throws SQLException;
    void deleteFermier(Long id) throws SQLException;
    Fermies getFermierById(Long id) throws SQLException;
}

