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
import java.sql.Date;
import java.sql.SQLException;
import com.JAVA.Beans.Produit;
import com.JAVA.DAO.FermierDAO;
import com.JAVA.DAO.FermierDAOImpl;
import com.JAVA.utils.DAOFactory;

@WebServlet("/ajouterProduit")
public class FermierServlet extends HttpServlet {
    private FermierDAO fermierDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        fermierDAO = new FermierDAOImpl(daoFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Renvoie une erreur si GET est utilisé
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "La méthode GET n'est pas supportée pour cette ressource.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Vérifier si le user_id est présent dans l'URL et valide
        	String userIdParam = request.getParameter("user_id");
        	if (userIdParam == null || userIdParam.isEmpty()) {
        	    throw new IllegalArgumentException("L'ID de l'utilisateur est manquant.");
        	}
        	System.out.println("User ID récupéré : " + userIdParam);  // Debug
        	Long userId = Long.parseLong(userIdParam);  // Assurez-vous que userId est bien un nombre

        	
            // Vérification des autres paramètres du produit
            String nom = request.getParameter("nom");
            if (nom == null || nom.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom du produit est requis.");
            }

            double prix = Double.parseDouble(request.getParameter("prix"));
            if (prix <= 0) {
                throw new IllegalArgumentException("Le prix doit être supérieur à zéro.");
            }

            int quantite = Integer.parseInt(request.getParameter("quantite"));
            if (quantite <= 0) {
                throw new IllegalArgumentException("La quantité doit être supérieure à zéro.");
            }

            String description = request.getParameter("description");
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("La description est requise.");
            }

            Date dateRecolte = Date.valueOf(request.getParameter("date_recolte"));

            // Récupérer l'image téléchargée via le formulaire
            Part imagePart = request.getPart("image");
            if (imagePart == null || imagePart.getSize() == 0) {
                throw new IllegalArgumentException("Aucune image téléchargée.");
            }
            String imageFileName = saveImage(imagePart);  // Méthode pour enregistrer l'image
            String categIdParam = request.getParameter("id_categorie");
        	Long categId = Long.parseLong(categIdParam);
            // Créer un objet Produit avec les informations récupérées
            Produit produit = new Produit();

            // Ajouter le produit dans la base de données
            fermierDAO.addProduit(produit);
            response.sendRedirect("produitAjoute.jsp");  // Rediriger après succès
            System.out.println("Nom: " + nom);
            System.out.println("Prix: " + prix);
            System.out.println("Quantité: " + quantite);
            System.out.println("Description: " + description);
            System.out.println("Date de récolte: " + dateRecolte);
            System.out.println("Image: " + imageFileName);
         // Afficher tous les paramètres du formulaire pour déboguer
            request.getParameterMap().forEach((key, value) -> {
                System.out.println(key + ": " + (value.length > 1 ? String.join(", ", value) : value[0]));
            });


        } catch (IllegalArgumentException e) {
            // Gérer les erreurs liées à la validation des données d'entrée
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (SQLException e) {
            // Gérer les erreurs liées à la base de données
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout du produit.");

        } catch (Exception e) {
            // Gérer les autres exceptions
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue.");
        }
        
    }

    // Méthode pour sauvegarder l'image sur le serveur
    private String saveImage(Part imagePart) throws IOException {
        // Obtenir le nom du fichier
        String fileName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
        String imageDir = getServletContext().getRealPath("/images");  // Dossier de destination pour les images
        File imageDirFile = new File(imageDir);

        // Créer le répertoire si nécessaire
        if (!imageDirFile.exists()) {
            imageDirFile.mkdirs();
        }

        // Sauvegarder l'image sur le serveur
        File imageFile = new File(imageDirFile, fileName);
        try (InputStream inputStream = imagePart.getInputStream()) {
            Files.copy(inputStream, imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;  // Retourner le nom du fichier pour l'enregistrer dans la base de données
    }
}
