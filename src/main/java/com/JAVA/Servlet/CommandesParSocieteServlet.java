package com.JAVA.Servlet;

import java.io.IOException;
import java.util.List;

import com.JAVA.Beans.Societedelivraison;
import com.JAVA.DAO.SocieteLivraisonDAO;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ListerCommandesServlet")
public class CommandesParSocieteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private SocieteLivraisonDAO societedelivraisonDAO;

    @Override
    public void init() throws ServletException {
        this.societedelivraisonDAO = DAOFactory.getInstance().getSocietedelivraisonDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération des commandes avec leurs détails
            List<Societedelivraison> commandes = societedelivraisonDAO.listerCommandesAvecDetails();
            for (Societedelivraison commande : commandes) {
                System.out.println(commande);
            }

            // Ajouter les données au scope de la requête
            request.setAttribute("commandes", commandes);

            // Rediriger vers la vue JSP
            request.getRequestDispatcher("societelivraison/views/commandes.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des commandes.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String statut = request.getParameter("statut");
        int idCommande = Integer.parseInt(request.getParameter("idCommande"));

        try {
            boolean statutMisAJour = societedelivraisonDAO.mettreAJourStatutCommande(idCommande, statut);
            if (statutMisAJour) {
                request.setAttribute("message", "Statut mis à jour avec succès.");
            } else {
                request.setAttribute("message", "Impossible de modifier le statut : commande déjà livrée.");
            }
            response.sendRedirect("ListerCommandesServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour du statut.");
        }
    }

}
