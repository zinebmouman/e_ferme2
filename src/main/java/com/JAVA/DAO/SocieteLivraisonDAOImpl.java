package com.JAVA.DAO;


import com.JAVA.Beans.Societedelivraison;
import com.JAVA.utils.DAOFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocieteLivraisonDAOImpl implements SocieteLivraisonDAO {
    private DAOFactory daoFactory;

    public SocieteLivraisonDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

  

    public List<Societedelivraison> listerCommandesAvecDetails() throws SQLException {
        List<Societedelivraison> commandes = new ArrayList<>();
        String sql = "SELECT c.id AS idCommande, c.date AS dateCommande, c.heure AS heureCommande, "
                   + "c.total AS totalCommande, c.statut AS statutCommande, "
                   + "con.address AS addressClient, u.Nom AS nomClient, u.telephone AS telephoneClient "
                   + "FROM commande c "
                   + "JOIN consommateur con ON c.consommateur_id = con.id "
                   + "JOIN user u ON con.id = u.id";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Societedelivraison commande = new Societedelivraison();
                commande.setIdCommande(rs.getInt("idCommande"));
                commande.setDateCommande(rs.getString("dateCommande"));
                commande.setHeureCommande(rs.getString("heureCommande"));
                commande.setTotalCommande(rs.getFloat("totalCommande"));
                commande.setStatutCommande(rs.getString("statutCommande"));
                commande.setAddressClient(rs.getString("addressClient"));
                commande.setNomClient(rs.getString("nomClient"));
                commande.setTelephoneClient(rs.getString("telephoneClient"));
                commandes.add(commande);
            }
        }
        return commandes;
    }
    public boolean mettreAJourStatutCommande(int idCommande, String nouveauStatut) throws SQLException {
        String sql = "UPDATE commande SET statut = ? WHERE id = ? AND statut != 'livré'";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nouveauStatut);
            ps.setInt(2, idCommande);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Renvoie vrai si la mise à jour a été effectuée
        }
    }


}
