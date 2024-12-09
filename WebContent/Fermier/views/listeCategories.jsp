<%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Catégories</title>
</head>
<body>
    <h1>Liste des Catégories</h1>

    <!-- Bouton pour ajouter une nouvelle catégorie -->
    <a href="${pageContext.request.contextPath}/produitservlet?action=addCat&idFermier=${idFermier}">
        <button>Ajouter une nouvelle catégorie</button>
    </a>

    <!-- Affichage de la liste des catégories -->
    <table border="1">
        <tr>
            <th>Nom</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="categorie" items="${categories}">
            <tr>
                <td>${categorie.nom}</td>
                <td>${categorie.description_categorie}</td>
                <td>
                    <!-- Bouton pour ajouter un produit à cette catégorie -->
                    <a href="${pageContext.request.contextPath}/produitservlet?action=ajouterProduit&id_categorie=${categorie.idCategorie}&idFermier=${idFermier}">
                        <button>Ajouter Produit</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
