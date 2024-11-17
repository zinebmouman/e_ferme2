package com.JAVA.Servlet;

import com.JAVA.DAO.ProduitDAO;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;
import com.JAVA.Beans.Categorie;
import com.JAVA.Beans.Offre;
import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Promotion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/ListerProduits")
public class ListerProduitsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        produitDAO = new ProduitDAOImp(daoFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer toutes les données nécessaires
            List<Produit> produits = produitDAO.getAllProduits();
            List<Produit> produitsAvecPromotion = produitDAO.getProduitsAvecPromotion();
            List<Offre> offres = produitDAO.getAllOffres();
            List<Promotion> promotions = produitDAO.getAllPromotions();
            List<Categorie> categories = produitDAO.getAllCategories();
            Map<Long, List<Produit>> produitsParOffre = new HashMap<>();

            // Log des données pour vérification
            System.out.println("Produits disponibles : " + produits);
            System.out.println("Produits avec promotion : " + produitsAvecPromotion);
            System.out.println("Offres disponibles : " + offres);
            System.out.println("Promotions disponibles : " + promotions);

            // Récupérer les produits pour chaque offre
            for (Offre offre : offres) {
                List<Produit> produitsAvecOffre = produitDAO.getProduitsAvecOffre(offre.getIdOffre());
                produitsParOffre.put(offre.getIdOffre(), produitsAvecOffre);
                System.out.println("Produits pour l'offre ID " + offre.getIdOffre() + " : " + produitsAvecOffre);
            }

            // Récupérer les produits par catégorie
            String categorieId = request.getParameter("idc");
            List<Produit> produitsParCategorie = new ArrayList<>();
            if (categorieId != null && !categorieId.isEmpty() && !categorieId.equals("0")) {
                produitsParCategorie = produitDAO.getProduitsParCategorie(Long.parseLong(categorieId));
                request.setAttribute("produitsParCategorie", produitsParCategorie);
            } else {
                // Si idc est 0, afficher tous les produits
                request.setAttribute("produits", produits);
            }
            
         // Créer une Map pour associer produit_id avec promotion_id
            Map<Long, Long> produitPromotionMap = new HashMap<>();

            // Remplir la map avec les promotions
            for (Promotion promotion : promotions) {
                for (Produit produit : produits) {
                    if (produit.getIdProduit() == promotion.getIdProduit()) {
                        produitPromotionMap.put(produit.getIdProduit(), promotion.getIdPromotion());
                    }
                }
            }

            // Passer cette map dans les attributs de la requête
            request.setAttribute("produitPromotionMap", produitPromotionMap);


            // Passer les attributs à la requête
            request.setAttribute("categories", categories);
            request.setAttribute("offres", offres);
            request.setAttribute("promotions", promotions);
            request.setAttribute("produitsParOffre", produitsParOffre);
            request.setAttribute("produitsAvecPromotion", produitsAvecPromotion);

            // Récupérer le paramètre `page` pour rediriger vers la bonne vue
            String page = request.getParameter("page");
            if ("index".equals(page)) {
                request.getRequestDispatcher("/Client/views/index.jsp").forward(request, response);
            } else if ("home".equals(page)) {
                request.getRequestDispatcher("/Client/views/homme.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/Client/views/listeProduits.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de catégorie ou d'offre invalide.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur interne du serveur.");
        }
    }
}
