package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.CommandeProduit;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;
import com.JAVA.DAO.CommandeDAO;
import com.JAVA.DAO.CommandeProduitDAO;
import com.JAVA.DAO.PanierDAO;
import com.JAVA.DAO.PanierDAOImpl;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CommanderServlet")
public class CommanderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Déclaration des DAO
    private DAOFactory daoFactory;
    private PanierDAO panierDAO;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.panierDAO = new PanierDAOImpl(DAOFactory.getInstance());
        this.produitDAO = new ProduitDAOImp(DAOFactory.getInstance());
        this.daoFactory = DAOFactory.getInstance(); // Initialisation de l'instance DAOFactory
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les paramètres du formulaire
            String userIdParam = request.getParameter("user_id");
            String totalPanierParam = request.getParameter("totalPanier");
            String date = request.getParameter("date");
            String heure = request.getParameter("heure");

            // Affichage des paramètres dans la console pour débogage
            System.out.println("user_id: " + userIdParam);
            System.out.println("totalPanier: " + totalPanierParam);
            System.out.println("date: " + date);
            System.out.println("heure: " + heure);

            // Vérification de la validité des paramètres
            if (userIdParam == null || totalPanierParam == null || date == null || heure == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tous les paramètres sont requis.");
                return;
            }

            // Convertir les paramètres
            Long userId = Long.parseLong(userIdParam);
            double totalPanier = Double.parseDouble(totalPanierParam);

            // Affichage des paramètres convertis
            System.out.println("userId: " + userId);
            System.out.println("totalPanier (double): " + totalPanier);

            // Créer une nouvelle commande
            CommandeDAO commandeDAO = daoFactory.getCommandeDAO();
            Commande commande = new Commande();
            commande.setConsommateur_id(userId);
            commande.setStatut("En cours"); // Statut initial
            commande.setDate(date);
            commande.setHeure(heure);
            commande.setTotal((int) totalPanier); // Assurez-vous que vous avez un champ total dans Commande

            // Enregistrer la commande et récupérer son ID
            int commandeId = commandeDAO.addCommande(commande);

            // Récupérer les produits du panier de l'utilisateur
            List<Panier> paniers = panierDAO.getPanierParConsommateur(userId);
            if (paniers.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le panier est vide.");
                return;
            }

            // Ajouter les produits dans la table `commande_produit`
            CommandeProduitDAO commandeProduitDAO = daoFactory.getCommandeProduitDAO();
            for (Panier panier : paniers) {
                CommandeProduit commandeProduit = new CommandeProduit();
                commandeProduit.setCommande_id((long) commandeId);
                commandeProduit.setProduit_id(panier.getProduitId());
                commandeProduit.setQuantite(panier.getQuantite());

                // Ajouter chaque produit à la commande
                commandeProduitDAO.addCommandeProduit(commandeProduit);
            }

            // Récupérer les informations détaillées des produits
            List<Produit> produits = produitDAO.getProduitsParIds(paniers);

            // Ajouter les produits et la commande à l'attribut de la requête
            request.setAttribute("commande", commande); 
            request.setAttribute("produits", produits); 
            request.setAttribute("paniers", paniers);

            // Redirection vers la page de confirmation de commande
            request.getRequestDispatcher("/Client/views/confirmationCommande.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les paramètres 'user_id' ou 'totalPanier' ne sont pas valides.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la commande.");
        }
    }
}
