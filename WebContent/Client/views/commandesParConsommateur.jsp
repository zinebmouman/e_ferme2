<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Commandes du consommateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f8f9fa;
            text-align: left;
        }
        .table-container {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<jsp:include page="css.jsp" />
    <jsp:include page="maintophomme.jsp" />
    <jsp:include page="headerhomme.jsp" />

    

    <c:if test="${empty commandes}">
        <p class="text-center">Aucune commande trouvée pour ce consommateur.</p>
    </c:if>

    <c:if test="${not empty commandes}">
        <div class="table-container">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Heure</th>
                        <th>Montant</th>
                        <th>Statut</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="commande" items="${commandes}">
                        <tr>
                            <td>${commande.date}</td>
                            <td>${commande.heure}</td>
                            <td>${commande.total}</td>
                            <td>
    <form action="ModifierStatutCommande" method="POST">
    <input type="hidden" name="user_id" value="${param.user_id}">
        <input type="hidden" name="commandeId" value="${commande.id}" />
        <select name="statut" class="form-select" ${commande.statut == 'livré' ? 'disabled' : ''}>
            <option value="en attente" ${commande.statut == 'en attente' ? 'selected' : ''}>En attente</option>
            <option value="livré" ${commande.statut == 'livré' ? 'selected' : ''}>Livré</option>
        </select>
       <td> <button type="submit" class="btn btn-danger hvr-hover" ${commande.statut == 'livré' ? 'disabled' : ''}>
            Modifier
        </button></td>
    </form>
</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="footer.jsp" />
    <jsp:include page="js.jsp" />
</body>
</html>
