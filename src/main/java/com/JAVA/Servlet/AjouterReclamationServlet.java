package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.JAVA.DAO.ReclamationDAO;
import com.JAVA.DAO.ReclamationDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AjouterReclamation")
public class AjouterReclamationServlet extends HttpServlet {
    private ReclamationDAO reclamationDAO;

    @Override
    public void init() throws ServletException {
        // Initialisation du DAO
        DAOFactory daoFactory = DAOFactory.getInstance();
        reclamationDAO = new ReclamationDAOImp(daoFactory);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contenu = request.getParameter("contenu");
        String consommateurIdStr = request.getParameter("consommateurId");
        int consommateurId = Integer.parseInt(consommateurIdStr);
        if (contenu != null && consommateurIdStr != null) {
            try {
                
                boolean reclamationAjoutee = reclamationDAO.ajouterReclamation(contenu, consommateurId);

                if (reclamationAjoutee) {
                    request.setAttribute("message", "Réclamation ajoutée avec succès.");
                } else {
                    request.setAttribute("message", "Impossible d'ajouter la réclamation.");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("message", "Une erreur est survenue lors de l'ajout.");
            }
        } else {
            request.setAttribute("message", "Données invalides fournies pour l'ajout.");
        }
        response.sendRedirect(request.getContextPath() + "/ListerProduits?page=home&user_id=" + consommateurId);
        // Rediriger ou afficher un message après l'opération
        
    }
}

