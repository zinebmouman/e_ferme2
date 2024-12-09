package com.JAVA.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.JAVA.Beans.*;
import com.JAVA.DAO.*;
import com.JAVA.utils.DAOFactory;

@WebServlet("/offreServlet")

public class OffreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OffreDAOImpl offreDAO;
    private ProduitDAOImp produitDAO;


    @Override
    public void init() throws ServletException  {
        this.offreDAO = new OffreDAOImpl(DAOFactory.getInstance());
        this. produitDAO = new ProduitDAOImp(DAOFactory.getInstance());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Long idFermier = getIdFermier(request, response);

        if (idFermier == null) return; // Erreur déjà gérée

      
		switch (action) {
            case "afficherOffres":
                afficherOffres(request, response, idFermier);
                break;
            case "ajouterOffre":
                request.setAttribute("idFermier", idFermier);
                request.getRequestDispatcher("/Fermier/views/ajouterOffre.jsp").forward(request, response);
                break;
            case "associerProduits":
                associerProduits(request, response, idFermier);
                break;
          
          
          
         
        
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue.");
        }
    }


    protected void AjouterProduitOffre(HttpServletRequest request, HttpServletResponse response , Long idFermier) throws IOException {
        try {
            Long offreId = (long) Integer.parseInt(request.getParameter("offreId"));
            Long produitId = (long) Integer.parseInt(request.getParameter("produitId"));

            System.out.println("offreId disponibles : " + offreId);
            System.out.println("produitId disponibles : " + produitId);
            System.out.println("produitId disponibles : " +idFermier);
            // Ajout de l'association produit-offre
            OffreProduit offreProduit = new OffreProduit(offreId, produitId);
            offreDAO.ajouterOffreProduit(offreProduit);

            // Vérifiez que la réponse n'est pas déjà engagée
            // Redirection
            if (!response.isCommitted()) {
                response.sendRedirect("offreServlet?action=associerProduits&offreId=" + offreId + "&idFermier=" + idFermier);
            } else {
                System.err.println("Redirection impossible, la réponse est déjà engagée !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue.");
        }
    }


    private void retirerProduit(HttpServletRequest request, HttpServletResponse response, Long idFermier) throws IOException {
        try {
            String offreIdParam = request.getParameter("offreId");
            String produitIdParam = request.getParameter("produitId");

            if (offreIdParam == null || produitIdParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Paramètres manquants.");
                return;
            }

            Long offreId = Long.parseLong(offreIdParam);
            Long produitId = Long.parseLong(produitIdParam);
            System.out.println("Retrait du produit avec ID : " + produitId + " de l'offre ID : " + offreId);

            offreDAO.retirerProduitDeOffre(offreId, produitId);

            // Redirection après suppression
            response.sendRedirect("offreServlet?action=associerProduits&offreId=" + offreId + "&idFermier=" + idFermier);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide pour l'offre ou le produit.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur SQL lors du retrait du produit.");
        }
    }


		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String action = request.getParameter("action");
		    Long idFermier = getIdFermier(request, response);
		    if ("ajouterOffre".equals(action)) {
		        ajouterOffre(request, response);
		    } else if ("ajouterProduit".equals(action)) {
		        AjouterProduitOffre(request, response, idFermier);
		    } else if ("retirerProduit".equals(action)) {
		        retirerProduit(request, response, idFermier);
		    }else {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue.");
		    }
		}



    private Long getIdFermier(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idFermierParam = request.getParameter("idFermier");

        if (idFermierParam != null && !idFermierParam.isEmpty()) {
            try {
                return Long.parseLong(idFermierParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                request.setAttribute("error", "ID Fermier invalide.");
                request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
                return null;
            }
        } else {
            request.setAttribute("error", "ID Fermier manquant.");
            request.getRequestDispatcher("/Fermier/views/error.jsp").forward(request, response);
            return null;
        }
    }


    private void afficherOffres(HttpServletRequest request, HttpServletResponse response, Long idFermier) throws ServletException, IOException {
        try {
        	   System.out.println(" offres disponibles : " +  idFermier);
            List<Offre> offres = offreDAO.getToutesLesOffres(idFermier);
            System.out.println(" offres disponibles : " +  offres);
        	
            request.setAttribute("offres", offres);
            request.setAttribute("idFermier", idFermier);
            request.getRequestDispatcher("/Fermier/views/listeOffres.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'affichage des offres.");
        }
    }

    private void associerProduits(HttpServletRequest request, HttpServletResponse response, Long idFermier) throws ServletException, IOException {
        Long offreId = Long.parseLong(request.getParameter("offreId"));

        try {
            if (idFermier != null) {
                // Récupérer les produits disponibles
                List<Produit> produits = produitDAO.getProduitsByFermier(idFermier);

                // Récupérer les produits associés
                List<Produit> produitsAssocies = offreDAO.getProduitsAssocies(offreId);

                // Ajouter les attributs pour la JSP
                request.setAttribute("produits", produits);
                request.setAttribute("produitsAssocies", produitsAssocies);
                request.setAttribute("idFermier", idFermier);
                request.setAttribute("offreId", offreId);

                // Rediriger vers la page
                request.getRequestDispatcher("/Fermier/views/associerProduits.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'association des produits.");
        }
    }

    
    

    private void ajouterOffre(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        	
            Long idFermier = getIdFermier(request, response);
            System.out.println("idFermier : " +idFermier);
           	
            try {
            Offre offre = new Offre();
            offre.setNom(request.getParameter("nom"));
            offre.setPrixPack(Double.parseDouble(request.getParameter("prixPack")));
            offre.setTauxReduction(Double.parseDouble(request.getParameter("tauxReduction")));
            offre.setDescription(request.getParameter("description"));
            offre.setDateDebut(Date.valueOf(request.getParameter("dateDebut")));
            offre.setDateFin(Date.valueOf(request.getParameter("dateFin")));
            offre.setFermierId(Long.parseLong(request.getParameter("idFermier")));

            offreDAO.ajouterOffre(offre);
            request.setAttribute("idFermier", idFermier);
            response.sendRedirect("offreServlet?action=afficherOffres&idFermier="  +idFermier);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de l'offre.");
        }
    }



}
