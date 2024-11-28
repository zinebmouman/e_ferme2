package com.JAVA.DAO;

import java.sql.SQLException;
import java.util.List;

import com.JAVA.Beans.Commande;


public interface CommandeDAO {
    int addCommande(Commande commande);
    boolean updateCommande(Commande commande);
    Commande getCommandeById(long commandeId);
    List<Commande> getCommandesParConsommateur(long consommateurId) throws SQLException;
}

