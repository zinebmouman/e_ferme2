package com.JAVA.Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.JAVA.utils.EmailUtil;

/**
 * Servlet implementation class ReponseReclamationServlet
 */
@WebServlet("/ReponseReclamationServlet")
public class ReponseReclamationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le paramètre pour déterminer l'action
        String action = request.getParameter("action");

        if ("showForm".equals(action)) {
            // Action pour afficher le formulaire de réponse
            String emailConsommateur = request.getParameter("emailConsommateur");
            String idReclamation = request.getParameter("idReclamation");

            request.setAttribute("emailConsommateur", emailConsommateur);
            request.setAttribute("idReclamation", idReclamation);

            // Rediriger vers le formulaire
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/reponseForm.jsp");
            dispatcher.forward(request, response);
        } else if ("sendResponse".equals(action)) {
            // Action pour envoyer la réponse
            String emailConsommateur = request.getParameter("emailConsommateur");
            String message = request.getParameter("message");

            try {
                // Appeler la méthode utilitaire pour envoyer l'email
                EmailUtil.sendEmail(emailConsommateur, "Réponse à votre réclamation", message);
                request.setAttribute("successMessage", "La réponse a été envoyée avec succès.");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Une erreur est survenue lors de l'envoi de l'email.");
            }

            // Rediriger vers une page de confirmation
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/views/confirmation.jsp");
            dispatcher.forward(request, response);
        } else {
            // Gestion d'une action inconnue
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue.");
        }
    }
}
