package com.JAVA.Servlet;

import com.JAVA.Beans.Societedelivraison;
import com.JAVA.DAO.FermierDAOImpl;
import com.JAVA.DAO.SocieteDAO;
import com.JAVA.DAO.SocieteDAOImpl;
import com.JAVA.utils.DAOFactory;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Livreur")
public class SocieteServlet extends HttpServlet {

    private  SocieteDAO societeDAO ;


    @Override
    public void init() throws ServletException {
    	societeDAO = new SocieteDAOImpl(DAOFactory.getInstance());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    request.getRequestDispatcher("/admin/views/societeForm.jsp").forward(request, response);
                    break;
                case "affiche":
                    listSocietes(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteSociete(request, response);
                    break;
                default:
                    response.sendRedirect("/admin/views/index.jsp");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addSociete(request, response);
            } else if ("update".equals(action)) {
                updateSociete(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listSocietes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Societedelivraison> societes = societeDAO.getAllSocietes();
        request.setAttribute("societes", societes);
        request.getRequestDispatcher("/admin/views/listSocietes.jsp").forward(request, response);
    }

    private void addSociete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
    	Societedelivraison societe = new Societedelivraison();
        societe.setNom(request.getParameter("nom"));
        societe.setEmail(request.getParameter("email"));
        societe.setLogin(request.getParameter("login"));
        societe.setPassword(request.getParameter("password"));
        societe.setTelephone(request.getParameter("telephone"));
        societe.setZoneLivraison(request.getParameter("zone_livraison"));

        societeDAO.addSociete(societe);
        response.sendRedirect("Livreur?action=affiche");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Societedelivraison existingSociete = societeDAO.getSocieteById(id);
        request.setAttribute("societe", existingSociete);
        request.getRequestDispatcher("/admin/views/societeForm.jsp").forward(request, response);
    }

    private void updateSociete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Societedelivraison societe = new Societedelivraison();
        societe.setId(Long.parseLong(request.getParameter("id")));
        societe.setNom(request.getParameter("nom"));
        societe.setEmail(request.getParameter("email"));
        societe.setTelephone(request.getParameter("telephone"));
        societe.setZoneLivraison(request.getParameter("zone_livraison"));

        societeDAO.updateSociete(societe);
        response.sendRedirect("Livreur?action=affiche");
    }

    private void deleteSociete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        societeDAO.deleteSociete(id);
        response.sendRedirect("Livreur?action=affiche");
    }
}
