<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Affichage des Produits</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="my-4">Liste des Produits</h1>

        <!-- Affichage de tous les produits -->
        <h2>1. Tous les Produits</h2>
        <div class="row">
            <c:forEach var="produit" items="${produits}">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <img src="${produit.image}" class="card-img-top" alt="Image du produit">
                        <div class="card-body">
                            <h5 class="card-title">${produit.nom}</h5>
                            <p class="card-text">Prix: ${produit.prix} MAD</p>
                            <p class="card-text">Quantité: ${produit.quantite}</p>
                            <p class="card-text">${produit.description}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Affichage des produits avec promotion -->
        <h2>2. Produits avec Promotion</h2>
        <div class="row">
            <c:forEach var="produit" items="${produitsAvecPromotion}">
                <div class="col-md-4 mb-4">
                    <div class="card border-success">
                        <img src="${produit.image}" class="card-img-top" alt="Image du produit">
                        <div class="card-body">
                            <h5 class="card-title">${produit.nom}</h5>
                            <p class="card-text">Prix: ${produit.prix} MAD</p>
                            <p class="card-text">Quantité: ${produit.quantite}</p>
                            <p class="card-text">${produit.description}</p>
                            <div class="alert alert-success mt-3">
                                <p><strong>Promotion:</strong> ${produit.promotion.taux}% de réduction</p>
                                <p>Période: ${produit.promotion.dateDebut} - ${produit.promotion.dateFin}</p>
                                <p>Description: ${produit.promotion.description}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Affichage des produits avec offre -->
        <h2>3. Produits avec Offre</h2>
        <div class="row">
            <c:forEach var="produit" items="${produitsAvecOffre}">
                <div class="col-md-4 mb-4">
                    <div class="card border-primary">
                        <img src="${produit.image}" class="card-img-top" alt="Image du produit">
                        <div class="card-body">
                            <h5 class="card-title">${produit.nom}</h5>
                            <p class="card-text">Prix: ${produit.prix} MAD</p>
                            <p class="card-text">Quantité: ${produit.quantite}</p>
                            <p class="card-text">${produit.description}</p>
                            <div class="alert alert-primary mt-3">
                                <p><strong>Offre:</strong> ${produit.offre.nom}</p>
                                <p>Prix du pack: ${produit.offre.prixPack} MAD</p>
                                <p>Réduction: ${produit.offre.tauxReduction}%</p>
                                <p>Période: ${produit.offre.dateDebut} - ${produit.offre.dateFin}</p>
                                <p>Description: ${produit.offre.description}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Affichage des produits par catégorie -->
        <!-- Affichage des produits par catégorie -->
        <!-- Liste des catégories -->
<form method="get" action="ListerProduits">
    <label for="categorie">Sélectionner une catégorie:</label>
    <select name="idc" id="categorie">
        <c:forEach var="categorie" items="${categories}">
            <option value="${categorie.idCategorie}" ${categorie.idCategorie == param.idc ? 'selected' : ''}>${categorie.nom}</option>
        </c:forEach>
    </select>
    <button type="submit">Filtrer</button>
</form>

<div class="row">
    <c:if test="${not empty produitsParCategorie}">
        <c:forEach var="produit" items="${produitsParCategorie}">
            <div class="col-md-4 mb-4">
                <div class="card border-primary">
                    <img src="${produit.image}" class="card-img-top" alt="${produit.nom}">
                    <div class="card-body">
                        <h5 class="card-title">${produit.nom}</h5>
                        <p class="card-text">Prix: ${produit.prix} MAD</p>
                        <p class="card-text">${produit.description}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty produitsParCategorie}">
        <p>Aucun produit trouvé pour cette catégorie.</p>
    </c:if>
</div>



    </div>

    <!-- Inclusion des scripts Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
