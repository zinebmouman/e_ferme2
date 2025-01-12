package com.JAVA.Servlet;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/generatePDF")
public class PDFGenerator extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Détails de la commande, cela peut être dynamique, récupéré depuis un backend
        String commandeDetails = "Commande ID : 12345\nDate : 2024-12-01\nTotal : 200 MAD";

        // Configurer la réponse pour télécharger le PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=commande.pdf");

        // Créer le PDF
        try (OutputStream out = response.getOutputStream()) {
            PdfWriter pdfWriter = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            document.add(new Paragraph("Détails de la commande :"));
            document.add(new Paragraph(commandeDetails));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
