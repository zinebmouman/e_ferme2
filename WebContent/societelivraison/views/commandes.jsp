<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Commandes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />
<body>
    <div class="container mt-4">

        <!-- Vérifier si aucune commande -->
        <c:if test="${empty commandes}">
            <div class="alert alert-warning text-center mt-4">
                <p>Aucune commande disponible.</p>
            </div>
        </c:if>

        <!-- Afficher la liste des commandes -->
        <c:if test="${not empty commandes}">
            <div class="table-responsive mt-4">
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Date</th>
                            <th>Heure</th>
                            <th>Total (MAD)</th>
                           
                            <th>Statut</th>
                            <th></th>
                            <th></th>
                            <th>Nom Client</th>
                            <th>Email</th>
                            <th>Téléphone</th>
                            <th>Adresse</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="commande" items="${commandes}">
                            <tr>
                                <td>${commande.date}    ${commande.id}</td>
                                <td>${commande.heure}</td>
                                <td>${commande.total}</td>
                                <td>${commande.statut}</td>
                                <td><form action="ListerCommandesServlet" method="POST">
        <input type="hidden" name="idCommande" value="${commande.id}" />
        <select  name="statut" id="statut-${commande.id}" 
                class="form-select" ${commande.statut == 'livré' ? 'disabled' : ''}>
            <option value="en attente" ${commande.statut == 'en attente' ? 'selected' : ''}>En attente</option>
            <option value="livré" ${commande.statut == 'livré' ? 'selected' : ''}>Livré</option>
        </select><td>
        <button type="submit" class="btn btn-danger hvr-hover" 
                ${commande.statut == 'livré' ? 'disabled' : ''}>Modifier</button></td>
    </form></td>
                                <td>${commande.clientNom}</td>
                                <td>${commande.clientEmail}</td>
                                <td>${commande.clientTelephone}</td>
                                <td>${commande.clientAdresse}</td>
                                
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="footer.jsp" />

    <jsp:include page="js.jsp" />
</body>

</html>
