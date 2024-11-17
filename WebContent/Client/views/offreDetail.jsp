<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Détails de l'Offre</title>
</head>
<body>
<h1>Offre: ${offre.idOffre}</h1>
    <h1>Offre: ${offre.nom}</h1>
    <p>Description: ${offre.description}</p>
    <p>Prix du Pack: ${offre.prixPack}</p>
    <p>Réduction: ${offre.tauxReduction}%</p>
    <p>Date de début: ${offre.dateDebut}</p>
    <p>Date de fin: ${offre.dateFin}</p>

    <h2>Produits associés à cette offre</h2>
    <ul>
        <c:forEach var="produit" items="${produitsAvecOffre}">
            <li>
                <h3>${produit.nom}</h3>
                <p>Prix: ${produit.prix}</p>
                <p>Description: ${produit.description}</p>
                <p><img src="${produit.image}" alt="${produit.nom}" width="100"></p>
            </li>
        </c:forEach>
    </ul>
   <form action="${pageContext.request.contextPath}/PanierServlet" method="post">
    <input type="number" name="user_id" value="${param.user_id}" required>
    <input type="number" name="offre_id" value="${offre.idOffre}">
    
    <!-- Paramètre produit_id facultatif -->
    <input type="number" name="produit_id" value="${produit != null ? produit.idProduit : ''}">

    <input type="number" name="quantite" value="1">
    <input type="number" name="prix" value="${offre.prixPack}" required>

    <button type="submit" class="cart">Add Offer to Cart</button>
</form>


</body>
</html>
