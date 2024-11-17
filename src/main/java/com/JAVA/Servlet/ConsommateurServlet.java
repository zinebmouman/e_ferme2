package com.JAVA.Servlet;

import com.JAVA.Beans.Consommateur;
import com.JAVA.DAO.ConsommateurDAO;
import com.JAVA.DAO.ConsommateurDAOImpl;
import com.JAVA.utils.DAOFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ajouterConsommateur")
public class ConsommateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConsommateurDAO consommateurDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.consommateurDAO = new ConsommateurDAOImpl(daoFactory);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("Nom"); // Assurez-vous que ce nom est correct
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telephone");
        String address = request.getParameter("address");

        Consommateur consommateur = new Consommateur();
        consommateur.setNom(nom);
        consommateur.setEmail(email);
        consommateur.setLogin(login);
        consommateur.setPassword(password);
        consommateur.setTelephone(telephone);
        consommateur.setType(4L); // 4 pour le type consommateur
        consommateur.setAddress(address);

        try {
            consommateurDAO.ajouterConsommateur(consommateur);
            response.sendRedirect(request.getContextPath() + "/ListerProduits?page=home&user_id=" + consommateur.getId()); // Page de succès avec redirection incluant l'ID de l'utilisateur
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Page d'erreur si l'ajout échoue
        }
    }
}
