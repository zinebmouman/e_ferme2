package com.JAVA.Servlet;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;

public class PayPalClient {

    private static final String CLIENT_ID = "AQnbLZYrnl6qwXp3nb7mSYjSj7jtIug0wKR_rIJPaPC4moDhlN4TKe62XAJz86HY9FA3FdncWJN3qUXR";  // Remplacez par votre Client ID
    private static final String SECRET = "ECEf_OLEmQ9CB3Nv66h9hdv5lClmdRS9UxBPWSJIXzycvrhKi1ywDr5o9QDF6D92Bap2_n-dv0aKVOmF";        // Remplacez par votre Secret

    public static String getAccessToken() throws Exception {
        String url = "https://api.sandbox.paypal.com/v1/oauth2/token";  // Sandbox ou Live URL
        String auth = CLIENT_ID + ":" + SECRET;
        String base64Auth = java.util.Base64.getEncoder().encodeToString(auth.getBytes());

        // Connexion HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + base64Auth);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Corps de la requête
        String body = "grant_type=client_credentials";
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(body);
            writer.flush();
        }

        // Lire la réponse de l'API
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Extraire le token d'accès de la réponse JSON
            String responseStr = response.toString();
            String accessToken = responseStr.substring(responseStr.indexOf(":") + 2, responseStr.indexOf(",") - 1);
            return accessToken;
        }
    }
    public static String createPayment() throws Exception {
        String url = "https://api.sandbox.paypal.com/v1/payments/payment"; // URL pour créer un paiement
        String jsonPayload = "{"
                + "\"intent\": \"sale\","
                + "\"payer\": {"
                + "  \"payment_method\": \"paypal\""
                + "},"
                + "\"transactions\": [{"
                + "  \"amount\": {"
                + "    \"total\": \"10.00\","
                + "    \"currency\": \"USD\""
                + "  },"
                + "  \"description\": \"Commande produit X\""
                + "}],"
                + "\"redirect_urls\": {"
                + "  \"return_url\": \"http://votre_url_de_retour\","
                + "  \"cancel_url\": \"http://votre_url_annulation\""
                + "}}";

        // Connexion HTTP
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Envoyer la requête
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(jsonPayload);
            writer.flush();
        }

        // Lire la réponse
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Extraire l'URL de redirection pour PayPal
            String responseStr = response.toString();
            String approvalUrl = responseStr.substring(responseStr.indexOf("\"approval_url\":\"") + 16, responseStr.indexOf("\",\"links"));
            System.out.println("Rediriger l'utilisateur vers cette URL PayPal: " + approvalUrl);
        }
		return jsonPayload;
    }
    
    public static boolean executePayment(String paymentId, String payerId) throws Exception {
        String url = "https://api.sandbox.paypal.com/v1/payments/payment/" + paymentId + "/execute";
        String jsonPayload = "{"
                + "\"payer_id\": \"" + payerId + "\""
                + "}";
		return false;

        // Connexion HTTP et envoi de la requête pour exécuter le paiement
        // Récupérez la réponse et retournez un booléen en fonction de la réussite
    }

}

