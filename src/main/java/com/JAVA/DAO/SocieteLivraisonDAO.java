package com.JAVA.DAO;



import com.JAVA.Beans.Societedelivraison;

import java.sql.SQLException;
import java.util.List;

public interface SocieteLivraisonDAO {
    List<Societedelivraison> listerCommandesAvecDetails() throws Exception;
    boolean mettreAJourStatutCommande(int idCommande, String nouveauStatut) throws SQLException;
}
