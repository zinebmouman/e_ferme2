<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<%@ page buffer="8kb" autoFlush="true" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Liste des Catégories</title>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />
</head>
<body>
 
                    
    
    <div class="container mt-4">
        <h1 class="text-center mb-4">Liste des Catégories</h1>

        <!-- Bouton pour ajouter une nouvelle catégorie -->
        <div class="text-center mb-4">
            <a href="${pageContext.request.contextPath}/produitservlet?action=addCat&idFermier=${idFermier}" class="btn hvr-hover btn-secondary">Ajouter une nouvelle catégorie</a>
        </div>

        <!-- Affichage de la liste des catégories -->
        <c:if test="${not empty categories}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Nom</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="categorie" items="${categories}">
                            <tr>
                                <td>${categorie.nom}</td>
                                <td>${categorie.description_categorie}</td>
                                <td>
                                    <!-- Bouton pour ajouter un produit à cette catégorie -->
                                    <a href="${pageContext.request.contextPath}/produitservlet?action=ajouterProduit&id_categorie=${categorie.idCategorie}&idFermier=${idFermier}" class="btn hvr-hover btn-secondary">Ajouter Produit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        
        <c:if test="${empty categories}">
            <div class="alert alert-warning text-center">
                <p>Aucune catégorie trouvée pour ce fermier.</p>
            </div>
        </c:if>
    </div>
    
    <jsp:include page="js.jsp" />
    <jsp:include page="footer.jsp" />
</body>
</html>
