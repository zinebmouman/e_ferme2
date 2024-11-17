package com.JAVA.Servlet;

import com.JAVA.DAO.ProduitDAOImp;
import com.JAVA.Beans.Produit;
import com.JAVA.Beans.Offre;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/offreDetail")
public class OffreDetailServlet extends HttpServlet {

    private ProduitDAOImp produitDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        DAOFactory daoFactory = DAOFactory.getInstance();
        produitDAO = new ProduitDAOImp(daoFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID de l'offre à partir des paramètres de l'URL
        String offreIdParam = request.getParameter("offreId");
        if (offreIdParam != null) {
            try {
                long offreId = Long.parseLong(offreIdParam);
                
                // Récupérer les produits liés à cette offre
                List<Produit> produitsAvecOffre = produitDAO.getProduitsAvecOffre(offreId);
                Offre offre = produitDAO.getOffreByID(offreId); // Récupérer les détails de l'offre

                if (offre != null) {
                    // Placer l'offre et les produits dans l'objet request pour l'affichage
                    request.setAttribute("offre", offre);
                    request.setAttribute("produitsAvecOffre", produitsAvecOffre);

                    // Rediriger vers la page JSP qui affichera les détails de l'offre
                    request.getRequestDispatcher("Client/views/offreDetail.jsp").forward(request, response);
                } else {
                    // Si l'offre n'est pas trouvée, rediriger vers une page d'erreur
                    response.sendRedirect("404.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
