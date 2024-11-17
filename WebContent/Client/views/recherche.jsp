<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Produits par Catégorie</title>
</head>
<body>

<h2>Produits filtrés par catégorie</h2>

<c:if test="${not empty produitsParCategorie}">
    <c:forEach var="produit" items="${produitsParCategorie}">
        <div>
            <h3>${produit.nom}</h3>
            <p>${produit.description}</p>
            <p>Prix: ${produit.prix} MAD</p>
            <img src="${produit.image}" alt="${produit.nom}" />
        </div>
    </c:forEach>
</c:if>

<c:if test="${empty produitsParCategorie}">
    <p>Aucun produit trouvé pour cette catégorie.</p>
</c:if>



</body>
</html>
