package com.JAVA.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.Commande;
import com.JAVA.Beans.CommandeProduit;
import com.JAVA.Beans.Offre;
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

    private DAOFactory daoFactory;
    private PanierDAO panierDAO;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.panierDAO = new PanierDAOImpl(DAOFactory.getInstance());
        this.produitDAO = new ProduitDAOImp(DAOFactory.getInstance());
        this.daoFactory = DAOFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String consommateurIdParam = request.getParameter("user_id");
        if (consommateurIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètre 'consommateur_id' manquant.");
            return;
        }

        try {
            long consommateurId = Long.parseLong(consommateurIdParam);
            CommandeDAO commandeDAO = daoFactory.getCommandeDAO();
            List<Commande> commandes = commandeDAO.getCommandesParConsommateur(consommateurId);

            // Ajouter les commandes à la requête pour l'affichage
            request.setAttribute("commandes", commandes);

            // Rediriger vers la page JSP
            request.getRequestDispatcher("/Client/views/commandesParConsommateur.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le paramètre 'consommateur_id' doit être un nombre.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des commandes.");
        }
    }


    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les paramètres
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

            // Créer une commande temporaire pour afficher avant confirmation
            CommandeDAO commandeDAO = daoFactory.getCommandeDAO();
            Commande commande = new Commande();
            commande.setConsommateur_id(userId);
            commande.setStatut("En attente de confirmation");
            commande.setDate(date);
            commande.setHeure(heure);
            commande.setTotal((int) totalPanier);

            // Récupérer les produits du panier
            List<Panier> paniers = panierDAO.getPanierParConsommateur(userId);
            if (paniers.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le panier est vide.");
                return;
            }

            // Listes pour les produits et offres
            List<Produit> produits = new ArrayList<>();
            List<Offre> offres = new ArrayList<>();

            // Parcourir chaque panier pour récupérer les informations détaillées
            for (Panier panier : paniers) {
                // Vérification si produitId ou offreId est présent
                if (panier.getProduitId() != null) {
                    // Si produit_id est présent, récupérer le produit
                    Produit produit = produitDAO.getProduitByID(panier.getProduitId());
                    if (produit != null) {
                        produits.add(produit); // Ajouter le produit à la liste
                        panier.setprixPanier(panier.getprixPanier()); // Assigner le prix du produit
                    } else {
                        System.out.println("Produit non trouvé pour id : " + panier.getProduitId());
                    }
                } else if (panier.getOffreId() != null) {
                    // Si offre_id est présent (et produit_id est nul), récupérer l'offre
                    Offre offre = produitDAO.getOffreByID(panier.getOffreId());
                    if (offre != null) {
                        offres.add(offre); // Ajouter l'offre à la liste
                        panier.setprixPanier(offre.getPrixPack()); // Assigner le prix de l'offre
                    } else {
                        System.out.println("Offre non trouvée pour id : " + panier.getOffreId());
                    }
                } else {
                    System.out.println("Panier sans produit ni offre trouvé pour consommateur_id : " + panier.getConsommateurId());
                }
            }

            System.out.println("les commandes : " + commande);
            System.out.println("les produits : " +produits);
            System.out.println("lesoffres : " + offres);
            // Ajouter les produits et offres à l'attribut de la requête pour affichage
            request.setAttribute("commande", commande);
            request.setAttribute("produits", produits);
            request.setAttribute("offres", offres);
            request.setAttribute("paniers", paniers);

            // Rediriger vers la page de confirmation de paiement
            request.getRequestDispatcher("/Client/views/confirmationCommande.jsp").forward(request, response);
            
         // Ajout du code pour vider le panier après paiement validé
            CommandeProduitDAO commandeProduitDAO = daoFactory.getCommandeProduitDAO();
            
            String commandeIdParam = request.getParameter("commandeId");
            String montantParam = request.getParameter("montant");
            
            if (commandeIdParam != null && montantParam != null) {
                Long commandeId = Long.parseLong(commandeIdParam);
                double montant = Double.parseDouble(montantParam);

                // Valider le paiement et vider le panier
                if (montant >= totalPanier) { // Vérification si le montant est suffisant
                    commande.setStatut("Payée");
                    commandeDAO.updateCommande(commande);

                    // Supprimer les éléments du panier pour l'utilisateur
                    panierDAO.viderPanierParConsommateur(userId);
                    System.out.println("Panier vidé pour l'utilisateur ID : " + userId);
                } else {
                    System.out.println("Montant insuffisant pour valider la commande.");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le montant est insuffisant.");
                    return;
                }
            }


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les paramètres 'user_id' ou 'totalPanier' ne sont pas valides.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération du panier.");
        }
    }

}
