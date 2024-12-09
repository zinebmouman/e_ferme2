package com.JAVA.Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.JAVA.Beans.Fermies;
import com.JAVA.DAO.FermierDAO;
import com.JAVA.DAO.FermierDAOImpl;
import com.JAVA.utils.DAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/fermiers")
public class GererFermiers extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FermierDAO fermierDAO;

    @Override
    public void init() throws ServletException {
        fermierDAO = new FermierDAOImpl(DAOFactory.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) action = "list";
            switch (action) {
                case "list":
                    listFermiers(request, response);
                    break;
                case "add":
                    showAddForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteFermier(request, response);
                    break;
                default:
                    listFermiers(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Erreur dans la gestion des actions : " + e.getMessage(), e);
        }
    }

    private void listFermiers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Fermies> fermiers = fermierDAO.getAllFermiers();
            request.setAttribute("fermiers", fermiers);
            request.getRequestDispatcher("/admin/views/liste_commerce.jsp").forward(request, response);
         

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des fermiers : " + e.getMessage());
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/views/addFermier.jsp").forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer l'ID du fermier depuis la requête
            Long id = Long.parseLong(request.getParameter("id"));

            // Récupérer le fermier depuis la base de données
            Fermies fermier = fermierDAO.getFermierById(id);

            if (fermier != null) {
                // Ajouter le fermier comme attribut de la requête
                request.setAttribute("fermier", fermier);

                // Rediriger vers la page JSP pour la modification
                request.getRequestDispatcher("/admin/views/editFermier.jsp").forward(request, response);
            } else {
                // Si le fermier n'est pas trouvé, rediriger vers la liste avec un message d'erreur
                response.sendRedirect("fermiers?action=list&error=notfound");
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des informations du fermier : " + e.getMessage());
        } catch (NumberFormatException e) {
            response.sendRedirect("fermiers?action=list&error=invalidid");
        }
    }

    private void deleteFermier(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        try {
            fermierDAO.deleteFermier(id);
            response.sendRedirect("fermiers?action=list");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la suppression du fermier.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                addFermier(request, response);
            } else if ("edit".equals(action)) {
                editFermier(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement du formulaire : " + e.getMessage());
        }
    }

    private void addFermier(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Fermies fermier = new Fermies(
            null, // ID généré automatiquement
            request.getParameter("nom"),
            request.getParameter("email"),
            request.getParameter("login"),
            request.getParameter("password"),
            request.getParameter("telephone"),
            2L, // Type Fermier
            request.getParameter("address"),
            request.getParameter("typeFermies")
        );
        try {
            fermierDAO.addFermier(fermier);
            response.sendRedirect("fermiers?action=list");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'ajout du fermier : " + e.getMessage());
        }
    }

    private void editFermier(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(request.getParameter("id"));
        Fermies fermier = new Fermies(
            id,
            request.getParameter("nom"),
            request.getParameter("email"),
            request.getParameter("login"),
            request.getParameter("password"),
            request.getParameter("telephone"),
            2L, // Type Fermier
            request.getParameter("address"),
            request.getParameter("typeFermies")
        );
        try {
            fermierDAO.updateFermier(fermier);
            response.sendRedirect(request.getContextPath() + "/fermiers?action=list");

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la modification du fermier : " + e.getMessage());
        }
    }
}
