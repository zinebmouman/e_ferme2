package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.JAVA.DAO.CommandeDAO;
import com.JAVA.DAO.SocieteLivraisonDAO;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ModifierStatutCommande")
public class ModifierStatutCommandeServlet extends HttpServlet {

	private SocieteLivraisonDAO societedelivraisonDAO;

    @Override
    public void init() throws ServletException {
        this.societedelivraisonDAO = DAOFactory.getInstance().getSocietedelivraisonDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idConsm = request.getParameter("user_id");
        String idCommandeStr = request.getParameter("commandeId");
        String nouveauStatut = request.getParameter("statut");

        int idConsommateur = Integer.parseInt(idConsm);
        if (idCommandeStr != null && nouveauStatut != null) {
            try {
                int idCommande = Integer.parseInt(idCommandeStr);

                boolean statutMisAJour = societedelivraisonDAO.mettreAJourStatutCommande(idCommande, nouveauStatut);

                if (statutMisAJour) {
                    request.setAttribute("message", "Statut de la commande mis à jour avec succès.");
                } else {
                    request.setAttribute("message", "Impossible de modifier le statut. La commande est peut-être déjà livrée.");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("message", "Une erreur est survenue lors de la mise à jour.");
            }
        } else {
            request.setAttribute("message", "Données invalides fournies pour la mise à jour.");
        }
        response.sendRedirect("CommanderServlet?user_id="+ idConsommateur);
    }
}
