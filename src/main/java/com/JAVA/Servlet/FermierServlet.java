package com.JAVA.Servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.JAVA.Beans.Categorie;
import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Reclamation;
import com.JAVA.DAO.FermierDAO;
import com.JAVA.DAO.FermierDAOImpl;
import com.JAVA.DAO.ProduitDAO;
import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/fermier")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FermierServlet extends HttpServlet {
    private FermierDAO fermierDAO;
    private ProduitDAOImp produitDAO;
    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        fermierDAO = new FermierDAOImpl(daoFactory);
        produitDAO = new ProduitDAOImp(daoFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("list".equals(action)) {
                afficherProduitsFermier(request, response);
            } else if ("addForm".equals(action)) {
                List<Categorie> categories = produitDAO.getAllCategories();
                request.setAttribute("categories", categories);
                System.out.println("Produits disponibles : " + categories);
                request.getRequestDispatcher("/Fermier/views/AjouterProduit.jsp").forward(request, response);
            }else if ("listRec".equals(action)) {
                List<Reclamation> reclamations = produitDAO.getAllReclamations();
                System.out.println("reclamations disponibles : " + reclamations);
                request.setAttribute("reclamations", reclamations);
                request.getRequestDispatcher("/admin/views/reclamations.jsp").forward(request, response); // Ajout du forward manquant
            }
 
             else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des données.");
        }}

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            ajouterProduit(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non reconnue.");
        }
    }

    private void afficherProduitsFermier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long idFermier = Long.parseLong(request.getParameter("idFermier"));
            List<Produit> produits = fermierDAO.getProduitsParFermier(idFermier);
            System.out.println("Début du chargement des produits");
            List<Produit> produit =fermierDAO.getProduitsParFermier(idFermier);
            System.out.println("Produits chargés : " + produit.size());

            request.setAttribute("produits", produits);
            request.getRequestDispatcher("/Fermier/views/liste_produit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID Fermier invalide.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement des produits.");
        }
    }

    private void ajouterProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Étape 1 : Récupération des données
            Long idFermier = Long.parseLong(request.getParameter("idFermier"));
            String nom = request.getParameter("nom");
            double prix = Double.parseDouble(request.getParameter("prix"));
            int quantite = Integer.parseInt(request.getParameter("quantite"));
            String description = request.getParameter("description");
            Date dateRecolte = Date.valueOf(request.getParameter("date_recolte"));
            Long idCategorie = Long.parseLong(request.getParameter("id_categorie"));

            // Étape 2 : Sauvegarde de l'image
            Part imagePart = request.getPart("image");
            String imageFileName = saveImage(imagePart);

            // Étape 3 : Création de l'objet Produit
            Produit produit = new Produit();
            produit.setNom(nom);
            produit.setPrix(prix);
            produit.setQuantite(quantite);
            produit.setDescription(description);
            produit.setDateRecolte(dateRecolte);
            produit.setImage(imageFileName);
            produit.setUserId(idFermier);

            
            produit.setid_categorie((Long) idCategorie);
            System.out.println("ID Catégorie reçu : " + request.getParameter("id_categorie"));

            System.out.println("Produ disponibles : " + produit);
            // Étape 4 : Ajout à la base de données
            fermierDAO.addProduit(produit);

            // Redirection après succès
            response.sendRedirect(request.getContextPath() + "/fermier?action=list&idFermier=" + idFermier);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Données invalides : " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'enregistrement de l'image.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur SQL : " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur inattendue : " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
  

        
    
    private String saveImage(Part imagePart) throws IOException {
        String fileName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
        String imageDir = getServletContext().getRealPath("/images");
        File imageDirFile = new File(imageDir);
        if (!imageDirFile.exists()) {
            imageDirFile.mkdirs();
        }
        File imageFile = new File(imageDirFile, fileName);
        try (InputStream inputStream = imagePart.getInputStream()) {
            Files.copy(inputStream, imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }
}
