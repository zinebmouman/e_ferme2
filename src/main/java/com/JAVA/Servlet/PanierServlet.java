package com.JAVA.Servlet;

import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Panier;
import com.JAVA.Beans.Produit;
import com.JAVA.DAO.PanierDAO;
import com.JAVA.DAO.PanierDAOImpl;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PanierServlet")
public class PanierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PanierDAO panierDAO;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() {
        this.panierDAO = new PanierDAOImpl(DAOFactory.getInstance());
        this.produitDAO = new ProduitDAOImp(DAOFactory.getInstance());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Vérification du paramètre 'user_id'
            String userIdParam = request.getParameter("user_id");
            if (userIdParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le paramètre 'user_id' est requis.");
                return;
            }
            Long userId = Long.parseLong(userIdParam);

            // Récupérer les paniers associés à l'utilisateur
            List<Panier> paniers = panierDAO.getPanierParConsommateur(userId);

            List<Produit> produits = new ArrayList<>();
            List<Offre> offres = new ArrayList<>();
            System.out.println("Paniers disponibles : " + paniers);

            // Parcourir chaque panier pour récupérer les informations détaillées
            for (Panier panier : paniers) {
                // Vérification si produit_id ou offre_id est présent
                if (panier.getProduitId() != null) {
                    // Si produit_id est présent, récupérer le produit
                    Produit produit = produitDAO.getProduitByID(panier.getProduitId());
                    if (produit != null) {
                        produits.add(produit); // Ajouter le produit à la liste
                        panier.setPrix(produit.getPrix()); // Assigner le prix du produit
                    } else {
                        System.out.println("Produit non trouvé pour id : " + panier.getProduitId());
                    }
                } else if (panier.getOffreId() != null) {
                    // Si offre_id est présent (et produit_id est nul), récupérer l'offre
                    Offre offre = produitDAO.getOffreByID(panier.getOffreId());
                    if (offre != null) {
                        offres.add(offre); // Ajouter l'offre à la liste
                        panier.setPrix(offre.getPrixPack()); // Assigner le prix de l'offre
                    } else {
                        System.out.println("Offre non trouvée pour id : " + panier.getOffreId());
                    }
                } else {
                    System.out.println("Panier sans produit ni offre trouvé pour consommateur_id : " + panier.getConsommateurId());
                }
            }

            // Ajouter les attributs à la requête pour l'affichage dans la JSP
            request.setAttribute("produits", produits);
            request.setAttribute("offres", offres);
            request.setAttribute("paniers", paniers);

            // Log des objets pour débogage
            System.out.println("Paniers disponibles : " + paniers);
            System.out.println("Produits disponibles : " + produits);
            System.out.println("Offres disponibles : " + offres);

            // Forward vers la JSP pour l'affichage
            request.getRequestDispatcher("Client/views/Panier.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le paramètre 'user_id' n'est pas valide.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération du panier.");
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération des paramètres
            Long produitId = request.getParameter("produit_id") != null ? Long.parseLong(request.getParameter("produit_id")) : null;
            Long consommateurId = Long.parseLong(request.getParameter("user_id"));
            int nouvelleQuantite = Integer.parseInt(request.getParameter("quantite"));
            String offreIdParam = request.getParameter("offre_id");
            Long offreId = offreIdParam != null ? Long.parseLong(offreIdParam) : null;

            // Cas 1: Mise à jour d'un produit
            if (produitId != null) {
                // Vérification de la quantité disponible pour le produit
                if (!produitDAO.verifierQuantiteDisponible(produitId, offreId, nouvelleQuantite)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La quantité demandée n'est pas disponible pour ce produit.");
                    return;
                }
                // Mise à jour de la quantité dans le panier pour ce produit
                panierDAO.modifierQuantite(produitId, offreId, consommateurId, nouvelleQuantite);

            } 
            // Cas 2: Mise à jour pour une offre
            else if (offreId != null) {
                List<Produit> produitsOffre = produitDAO.getProduitsAvecOffre(offreId);
                for (Produit produit : produitsOffre) {
                    // Vérification de la quantité disponible pour chaque produit de l'offre
                    if (!produitDAO.verifierQuantiteDisponible(produit.getIdProduit(), offreId, nouvelleQuantite)) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantité insuffisante pour le produit " + produit.getNom() + " dans l'offre.");
                        return;
                    }
                }
                // Mise à jour de la quantité dans le panier pour chaque produit de l'offre
                for (Produit produit : produitsOffre) {
                    panierDAO.modifierQuantite(produit.getIdProduit(), offreId, consommateurId, nouvelleQuantite);
                }
            }

            // Redirection vers la page du panier après mise à jour
            response.sendRedirect(request.getContextPath() + "/PanierServlet?user_id=" + consommateurId);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les paramètres ne sont pas valides.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour de la quantité.");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long produitId = request.getParameter("produit_id") != null ? Long.parseLong(request.getParameter("produit_id")) : null;
            Long consommateurId = Long.parseLong(request.getParameter("user_id"));
            String offreIdParam = request.getParameter("offre_id");
            Long offreId = offreIdParam != null ? Long.parseLong(offreIdParam) : null;

            if (produitId != null) {
                panierDAO.supprimerDuPanier(produitId, offreId, consommateurId);
            } else if (offreId != null) {
                List<Produit> produitsOffre = produitDAO.getProduitsAvecOffre(offreId);
                for (Produit produit : produitsOffre) {
                    panierDAO.supprimerDuPanier(produit.getIdProduit(), offreId, consommateurId);
                }
            }

            response.sendRedirect(request.getContextPath() + "/PanierServlet?user_id=" + consommateurId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le paramètre 'produit_id' ou 'consommateur_id' n'est pas valide.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du produit du panier.");
        }
    }


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("PUT".equals(request.getParameter("_method"))) {
	        doPut(request, response);
	        return;
	    }
		
		if ("delete".equals(request.getParameter("_method"))) {
			doDelete(request, response);
	        return;
	    }
		
	    try {
	        // Récupérer les paramètres envoyés par le formulaire
	        String userIdParam = request.getParameter("user_id");
	        String produitIdParam = request.getParameter("produit_id");
	        String offreIdParam = request.getParameter("offre_id");
	        String promotionIdParam = request.getParameter("promotion_id");
	        String prixParam = request.getParameter("prix");
	        String quantiteParam = request.getParameter("quantite");

	        // Vérification des paramètres requis
	        if (userIdParam == null || prixParam == null) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Les paramètres 'user_id' et 'prix' sont requis.");
	            return;
	        }

	        Long userId = Long.parseLong(userIdParam);
	        Long produitId = (produitIdParam != null && !produitIdParam.isEmpty()) ? Long.parseLong(produitIdParam) : null;
	        Long offreId = (offreIdParam != null && !offreIdParam.isEmpty()) ? Long.parseLong(offreIdParam) : null;
	        Long promotionId = (promotionIdParam != null && !promotionIdParam.isEmpty()) ? Long.parseLong(promotionIdParam) : null;
	        double prix = Double.parseDouble(prixParam);
	        double quantite = Double.parseDouble(quantiteParam);

	        // Créer un objet Panier et y ajouter les informations nécessaires
	        Panier panier = new Panier();
	        panier.setOffreId(offreId);
	        panier.setPromotionId(promotionId);
	        panier.setProduitId(produitId);  // Produit peut être null
	        panier.setConsommateurId(userId);
	        panier.setQuantite((int) quantite);
	        panier.setPrix(prix); // Utiliser directement le prix envoyé par le formulaire

	        // Ajouter le produit au panier
	        panierDAO.ajouterAuPanier(panier);

	        // Message de succès
	        request.setAttribute("successMessage", prix > 0 ? "Produit avec réduction ajouté au panier avec succès." : "Produit ajouté au panier avec succès.");
	        response.sendRedirect(request.getContextPath() + "/ListerProduits?page=home&user_id=" + userId);
	    } catch (NumberFormatException e) {
	        // Gestion des erreurs de format
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erreur de format de paramètre : " + e.getMessage());
	    } catch (SQLException e) {
	        // Gestion des erreurs SQL
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'accès à la base de données : " + e.getMessage());
	    } catch (Exception e) {
	        // Gestion d'autres erreurs
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue : " + e.getMessage());
	    }
	}



}
