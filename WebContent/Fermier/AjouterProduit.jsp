<!DOCTYPE html>
<html>
<meta charset="UTF-8">

<head>
    <title>Ajouter un produit</title>
</head>
<body>
    <% 
        // Récupérer l'user_id depuis la requête
        String userId = request.getParameter("user_id");

        // Vérifier si userId est null ou vide, sinon afficher un message d'erreur
        if (userId == null || userId.isEmpty()) {
    %>
        <p style="color:red;">Erreur : Le user_id est manquant.</p>
    <% 
        } else {
    %>
        <h1>Ajouter un produit pour l'utilisateur ID: <%= userId %></h1>
    <% 
        }
    %>

    <form action="${pageContext.request.contextPath}/ajouterProduit?user_id=<%= userId %>" method="POST" enctype="multipart/form-data">
        <!-- Champ caché pour passer l'user_id -->
      


       <label for="nom">Nom du produit</label>
    <input type="text" name="nom" required>

    <label for="prix">Prix</label>
    <input type="number" name="prix" required>

    <label for="quantite">Quantité</label>
    <input type="number" name="quantite" required>

    <label for="description">Description</label>
    <textarea name="description" required></textarea>

    <label for="date_recolte">Date de récolte</label>
    <input type="date" name="date_recolte" required>

    <label for="image">Image</label>
    <input type="file" name="image" required>

    <button type="submit">Ajouter le produit</button>
    </form>
</body>
</html>
