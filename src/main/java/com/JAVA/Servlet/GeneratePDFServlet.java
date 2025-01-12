package com.JAVA.Servlet;

import java.io.*;
import javax.servlet.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/generatePDFServlet")
public class GeneratePDFServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les détails de la commande (vous pouvez les obtenir de votre base de données ou d'une autre source)
        String commandeDetails = "Commande ID : 12345\nDate : 2024-12-01\nTotal : 200 MAD";

        // Définir les paramètres de la réponse pour indiquer qu'il s'agit d'un fichier PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=commande.pdf");

        // Appeler la méthode pour générer le PDF et l'envoyer à la sortie
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateInvoicePDF(response.getOutputStream(), commandeDetails);
    }
}
