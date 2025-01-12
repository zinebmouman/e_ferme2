package com.JAVA.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import com.JAVA.Beans.Categorie;
import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Promotion;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

@WebServlet("/produitservlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProduitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProduitDAOImp produitDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        produitDAO = new ProduitDAOImp(daoFactory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
      

        if ("ajouterCategorie".equalsIgnoreCase(action)) {
            String nomCategorie = request.getParameter("nom_categorie");
            String descriptionCategorie = request.getParameter("description_categorie");
            Long idFermier = Long.parseLong(request.getParameter("idFermier")); // Toujours récupérer idFermier
            Categorie categorie = new Categorie();
            categorie.setNom(nomCategorie);
            categorie.setdescription_categorie(descriptionCategorie);

            produitDAO.ajouterCategorie(categorie);

            response.sendRedirect("produitservlet?action=affichercat&idFermier=" + idFermier);
        }  else if ("add".equalsIgnoreCase(action)) {
            try {
                String nom = request.getParameter("nom");
                double prix = Double.parseDouble(request.getParameter("prix"));
                int quantite = Integer.parseInt(request.getParameter("quantite"));
                String description = request.getParameter("description");
                long idCategorie = Long.parseLong(request.getParameter("id_categorie"));
                Long idFermier = Long.parseLong(request.getParameter("idFermier"));
                // Récupérer la date de récolte
                String dateRecolteStr = request.getParameter("date_recolte");
                java.sql.Date dateRecolte = null;
                if (dateRecolteStr != null && !dateRecolteStr.isEmpty()) {
                    dateRecolte = java.sql.Date.valueOf(dateRecolteStr);
                }

                Part imagePart = request.getPart("image");
                String imageFileName = saveImage(imagePart);

                Produit produit = new Produit();
                produit.setNom(nom);
                produit.setPrix(prix);
                produit.setQuantite(quantite);
                produit.setDescription(description);
                produit.setImage(imageFileName);
                produit.setDateRecolte(dateRecolte);
                produit.setid_categorie(idCategorie);
                produit.setUserId(idFermier);

                produitDAO.addProduit(produit);

                response.sendRedirect("produitservlet?action=affichercat&idFermier=" + idFermier);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Erreur lors de l'ajout du produit : " + e.getMessage());
                request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
            }
        }
        else if ("addPromotion".equalsIgnoreCase(action)) {
            try {
                // Récupération des paramètres de la requête
                String dateDebutStr = request.getParameter("date_debut");
                Long idFermier = Long.parseLong(request.getParameter("idFermier"));
                String dateFinStr = request.getParameter("date_fin");
                double taux = Double.parseDouble(request.getParameter("taux"));
                String description = request.getParameter("description");
                long idProduit = Long.parseLong(request.getParameter("id_produit"));

                // Conversion des dates au format java.sql.Date
                java.sql.Date dateDebut = java.sql.Date.valueOf(dateDebutStr);
                java.sql.Date dateFin = java.sql.Date.valueOf(dateFinStr);

                // Création de l'objet Promotion
                Promotion promotion = new Promotion();
                promotion.setDateDebut(dateDebut);
                promotion.setDateFin(dateFin);
                promotion.setTaux(taux);
                promotion.setDescription(description);
                promotion.setIdProduit(idProduit);
                System.out.println("promotion : " + promotion);
                // Appel à la DAO pour ajouter la promotion
                produitDAO.ajouterPromotion(promotion);

                // Redirection vers la page des produits après l'ajout
                response.sendRedirect("produitservlet?action=afficherProduits&idFermier=" + idFermier);
            } catch (Exception e) {
                e.printStackTrace();
                // Gestion des erreurs
                request.setAttribute("error", "Erreur lors de l'ajout de la promotion : " + e.getMessage());
                request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
            }
        }


    }
    

    @Override
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idFermierParam = request.getParameter("idFermier");
        Long idFermier = null;

        if (idFermierParam != null && !idFermierParam.isEmpty()) {
            try {
                idFermier = Long.parseLong(idFermierParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "ID Fermier invalide.");
                request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "ID Fermier manquant.");
            request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
            return;
        }

        if ("affichercat".equalsIgnoreCase(action)) {
            if (idFermierParam != null && !idFermierParam.isEmpty()) { // Vérification si idFermier est non nul et non vide
                try {
                    // Récupération des catégories à partir de votre DAO
                    List<Categorie> categories = produitDAO.getCategories(); 
                    System.out.println("Catégories disponibles pour JSP : " + categories); // Peut être conservé pour le débogage

                    // S'il n'y a pas de catégories, vous pouvez gérer le cas ici
                    if (categories == null || categories.isEmpty()) {
                        request.setAttribute("message", "Aucune catégorie disponible.");
                    } else {
                        request.setAttribute("categories", categories); // Attribuer la liste des catégories à la requête
                        request.setAttribute("idFermier", idFermier);  // Attribuer idFermier à la requête
                    }

                    // Faire le forward vers la JSP de liste des catégories
                    request.getRequestDispatcher("/Fermier/views/listeCategories.jsp").forward(request, response);
                    return;

                } catch (Exception e) {
                    // Si une exception se produit, l'afficher dans les logs et renvoyer une erreur
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Erreur lors de la récupération des catégories.");
                    request.getRequestDispatcher("/Fermier/views/errorPage.jsp").forward(request, response);
                }
            } else {
                // Si idFermier est invalide ou null, rediriger vers une page d'erreur ou afficher un message
                request.setAttribute("errorMessage", "ID Fermier est invalide.");
                request.getRequestDispatcher("/Fermier/views/errorPage.jsp").forward(request, response);
            }
        }



            if ("ajouterProduit".equalsIgnoreCase(action)) {
            // Transférer les données nécessaires à la JSP AjouterProduit.jsp
        	Long   idCategorie = Long.parseLong(request.getParameter("id_categorie"));
            request.setAttribute("idFermier", idFermier);
            request.setAttribute("idCategorie", idCategorie);
            request.getRequestDispatcher("/Fermier/views/AjouterProduit.jsp").forward(request, response);
        }


         else if ("addCat".equalsIgnoreCase(action)) {
            request.setAttribute("idFermier", idFermier);
            request.getRequestDispatcher("/Fermier/views/AjouterCategorie.jsp").forward(request, response);
        }
         else if ("ajouterPromotion".equalsIgnoreCase(action)) {
        	    Long id_produit = Long.parseLong(request.getParameter("id_produit"));
        	    System.out.println("Produ disponibles : " + id_produit);
        	
        	    request.setAttribute("idFermier", idFermier);
        	    request.setAttribute("idProduit", id_produit);
        	    
        	    request.getRequestDispatcher("/Fermier/views/AjouterPromotion.jsp").forward(request, response);
         }else if ("afficherProduits".equalsIgnoreCase(action)) {
        	    if (idFermier != null) {
        	        List<Produit> produits = produitDAO.getProduitsByFermier(idFermier);
        	        request.setAttribute("produits", produits);
        	        request.setAttribute("idFermier", idFermier);
        	        request.getRequestDispatcher("/Fermier/views/liste_produit.jsp").forward(request, response);
        	        }
         } else  if ("promotions".equalsIgnoreCase(action)) {
     	    System.out.println("idFermier disponibles : " + idFermier);    
        	 if (idFermier != null) {

        	        List<Produit> produitsEnPromotion = produitDAO.getProduitsEnPromotionParFermier(idFermier);
        	        System.out.println("Produits en promotion : " + produitsEnPromotion);

        	        request.setAttribute("produitsEnPromotion", produitsEnPromotion);
        	        request.getRequestDispatcher("/Fermier/views/produitsEnPromotion.jsp").forward(request, response);
        	        }
        	    
         }else {
        	        request.setAttribute("error", "ID Fermier non spécifié.");
        	        request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
        	    }
        	
    }

   

    private String saveImage(Part imagePart) throws IOException {
        if (imagePart == null || imagePart.getSize() <= 0) {
            return null;
        }

        // Obtenir le nom du fichier téléchargé
        String fileName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
        
        // Définir le chemin relatif pour enregistrer les images
        String relativePath = "/e_ferme/Client/images/";
        String imageDir = getServletContext().getRealPath(relativePath);

        // Créer le répertoire si nécessaire
        File imageDirFile = new File(imageDir);
        if (!imageDirFile.exists()) {
            imageDirFile.mkdirs();
        }

        // Enregistrer l'image dans le répertoire
        File imageFile = new File(imageDirFile, fileName);
        try (InputStream inputStream = imagePart.getInputStream()) {
            Files.copy(inputStream, imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        // Retourner le chemin complet pour stocker dans la base de données
        return relativePath + fileName;
    }


}
