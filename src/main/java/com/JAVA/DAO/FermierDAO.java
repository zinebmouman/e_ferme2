package com.JAVA.DAO;

import com.JAVA.Beans.Produit;
import java.sql.SQLException;

public interface FermierDAO {
    void addProduit(Produit produit) throws SQLException;
    // Autres méthodes si nécessaire, par exemple : getProduitById, updateProduit, deleteProduit
}
