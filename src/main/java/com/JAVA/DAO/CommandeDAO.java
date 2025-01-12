package com.JAVA.DAO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;


public interface CommandeDAO {
    int addCommande(Commande commande);
    boolean updateCommande(Commande commande);
    Commande getCommandeById(long commandeId);
    List<Commande> getCommandesParConsommateur(long consommateurId) throws SQLException;
    void addCommandeProduits(int commandeId, List<Panier> produitsSimples, List<Integer> produitIds) throws SQLException;
    void updateProduitQuantite(List<Panier> paniers, List<Integer> produitIds) throws SQLException;
   }

