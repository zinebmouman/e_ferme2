package com.JAVA.Servlet;

import java.io.*;
import javax.servlet.*;

import com.JAVA.Beans.Commande;
import com.JAVA.DAO.CommandeDAO;
import com.JAVA.DAO.PanierDAO;
import com.JAVA.DAO.PanierDAOImpl;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ExecutepaimentServlet")
public class ExecutePaymentServlet extends HttpServlet {

	
	  private DAOFactory daoFactory;
	    private PanierDAO panierDAO;
	
	 @Override
	    public void init() throws ServletException {
	        super.init();
	        this.panierDAO = new PanierDAOImpl(DAOFactory.getInstance());
	        this.daoFactory = DAOFactory.getInstance();
	    }
	 
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Option 1: Rediriger vers une page spécifique ou une erreur
	        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "La méthode GET n'est pas supportée pour cette ressource.");

	        // Option 2: Si vous souhaitez afficher quelque chose pour la méthode GET
	        // request.getRequestDispatcher("/Client/views/affichagePage.jsp").forward(request, response);
	    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	String paymentId = request.getParameter("paymentId");
        	 String userIdParam = request.getParameter("user_id");
             String totalPanierParam = request.getParameter("totalPanier");
             String date = request.getParameter("date");
             String heure = request.getParameter("heure");
        	
             if (userIdParam == null || totalPanierParam == null || date == null || heure == null) {
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tous les paramètres sont requis.");
                 return;
             }

             Long userId = Long.parseLong(userIdParam);
             double totalPanier = Double.parseDouble(totalPanierParam);
            // Avant de rediriger vers PayPal, créez une commande dans la base de données
            Commande commande = new Commande();
         // Remplissez les informations de la commande avec les données du panier, etc.
            commande.setConsommateur_id(userId);  // L'ID de l'utilisateur ou du consommateur
            commande.setTotal((int) totalPanier); // Le total du panier
            commande.setStatut("En attente"); // Statut initial de la commande

            CommandeDAO commandeDAO = DAOFactory.getInstance().getCommandeDAO();
            commandeDAO.addCommande(commande); // Sauvegarde la commande dans la base de données

            // Récupérer l'ID de la commande créée
            long commandeId = commande.getId();

         // Rediriger l'utilisateur vers PayPal avec l'ID de commande
         response.sendRedirect("confirmationPaiement.jsp?commandeId=" + commandeId);

            
            String payerId = request.getParameter("PayerID");

            // Appeler l'API PayPal pour exécuter le paiement
            boolean paymentSuccess = PayPalClient.executePayment(paymentId, payerId);

            if (paymentSuccess) {
            	
                commande.setStatut("Payée");
                commandeDAO.updateCommande(commande);

               
                
                panierDAO.viderPanierParConsommateur(commande.getConsommateur_id());

                // Rediriger vers la page de confirmation de commande
                response.sendRedirect("/Client/views/confirmationCommande.jsp");
            } else {
                // Si le paiement a échoué, afficher une page d'erreur
                response.sendRedirect("erreur.jsp");
            }

        } catch (Exception e) {
            // Si une erreur se produit, afficher une page d'erreur les chose à faire c'est de changer cette servlet à enregistre la commande puis prendre son id et effectuer paiment
            response.sendRedirect("error.jsp");
        }
    }
}
