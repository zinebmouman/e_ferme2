package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;
import com.JAVA.DAO.CommandeDAO;
import com.JAVA.DAO.OffreProduitDAO;
import com.JAVA.DAO.PanierDAO;
import com.JAVA.DAO.PanierDAOImpl;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PaimentServlet")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DAOFactory daoFactory;
    private PanierDAO panierDAO;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.produitDAO = new ProduitDAOImp(DAOFactory.getInstance());
        this.panierDAO = new PanierDAOImpl(DAOFactory.getInstance());
        this.daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Option 1: Rediriger vers une page spécifique ou une erreur
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "La méthode GET n'est pas supportée pour cette ressource.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les paramètres de la requête
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

            // Création de l'objet commande
            CommandeDAO commandeDAO = daoFactory.getCommandeDAO();
            Commande commande = new Commande();
            commande.setConsommateur_id(userId);
            commande.setStatut("En attente de confirmation");
            commande.setDate(date);
            commande.setHeure(heure);
            commande.setTotal((int) totalPanier);

            // Récupération du panier de l'utilisateur
            List<Panier> paniers = panierDAO.getPanierParConsommateur(userId);
            if (paniers.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le panier est vide.");
                return;
            }

            List<Integer> produitIds = new ArrayList<>();
            List<Panier> produitsSimples = new ArrayList<>();

            // Parcourir les paniers pour distinguer les produits simples et les produits liés à une offre
            for (Panier panier : paniers) {
                if (panier.getProduitId() != null) {
                    produitsSimples.add(panier);
                } else if (panier.getOffreId() != null) {
                	OffreProduitDAO offreProduitDAO = new OffreProduitDAO(daoFactory);
                    List<Integer> produitsDeOffre = offreProduitDAO.getProduitIdsParOffre(panier.getOffreId());
                    if (produitsDeOffre != null && !produitsDeOffre.isEmpty()) {
                        produitIds.addAll(produitsDeOffre);
                    } else {
                        System.out.println("Offre non trouvée pour id : " + panier.getOffreId());
                    }
                } else {
                    System.out.println("Panier sans produit ni offre trouvé pour consommateur_id : " + panier.getConsommateurId());
                }
            }
            System.out.println("les produits : " + produitsSimples);
            System.out.println("les produits : " + paniers);
            System.out.println("les offres : " + produitIds);

            // Ajouter la commande dans la base
            int commandeId = commandeDAO.addCommande(commande);
            if (commandeId == -1) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de la commande.");
                return;
            }
            

            // Ajouter les produits à la commande
            commandeDAO.addCommandeProduits(commandeId, produitsSimples, produitIds);

            commandeDAO.updateProduitQuantite(produitsSimples, produitIds);
            // Vider le panier du consommateur après la commande
            panierDAO.viderPanierParConsommateur(userId);
            request.setAttribute("commande", commande);
            // Rediriger vers la page de confirmation
            request.setAttribute("message", "Commande ajoutée avec succès !");
            request.getRequestDispatcher("/Client/views/confirmationPaiement.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les paramètres 'user_id' ou 'totalPanier' ne sont pas valides.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de la commande.");
        }
    }

}
