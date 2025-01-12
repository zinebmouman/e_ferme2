package com.JAVA.DAO;



import com.JAVA.Beans.Commande;
import com.JAVA.Beans.Societedelivraison;

import java.sql.SQLException;
import java.util.List;

public interface SocieteLivraisonDAO {

    void addSociete(Societedelivraison societe) throws SQLException;
    List<Societedelivraison> getAllSocietes() throws SQLException;
    Societedelivraison getSocieteById(int id) throws SQLException;
    void updateSociete(Societedelivraison societe) throws SQLException;
    void deleteSociete(int id) throws SQLException;
    List<Commande> listerCommandesAvecDetails() throws SQLException;
    boolean mettreAJourStatutCommande(int idCommande, String nouveauStatut) throws SQLException;
}
