<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Réponse à la Réclamation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h1 class="text-center mb-4">Réponse à la Réclamation</h1>
    <form method="post" action="ReponseReclamationServlet">
        <input type="hidden" name="action" value="sendResponse">
        <input type="hidden" name="emailConsommateur" value="${emailConsommateur}">
        <textarea name="message" class="form-control mb-3" placeholder="Entrez votre réponse ici" required></textarea>
        <button type="submit" class="btn btn-success">Envoyer</button>
    </form>
</div>
</body>
</html>
