<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de Commande</title>
    <!-- Ajoutez ici votre lien vers Bootstrap ou Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Si vous utilisez Tailwind CSS, vous pouvez l'intégrer comme suit : -->
    <!-- <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet"> -->
</head>

<body>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />
    
<div class="cart-box-main">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="table-main table-responsive">
        <h2 class="text-center">Confirmation de votre commande</h2>

        
            <!-- Détails de la commande -->
            <div class="col-12">
                <h3>Détails de la commande</h3>
                <p><strong>Date de la commande:</strong> ${commande.date}</p>
                <p><strong>Heure de la commande:</strong> ${commande.heure}</p>
                <p><strong>Statut de la commande:</strong> ${commande.statut}</p>
                <p><strong>Total de la commande:</strong> ${commande.total} MAD</p>
            </div>

            <!-- Liste des produits -->
            <div class="col-12">
                <h3>Produits de votre commande</h3>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Produit</th>
                            <th scope="col">Quantité</th>
                            <th scope="col">Prix Unitaire (MAD)</th>
                            <th scope="col">Total (MAD)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Afficher les produits dans la commande -->
                        <c:forEach var="produit" items="${produits}">
                            <tr>
                                <td>${produit.nom}</td>
                                <td>${produit.quantite}</td>
                                <td>${produit.prix}</td>
                                <td>${produit.prix * produit.quantite}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Total de la commande -->
            <div class="col-12 mt-3">
                <h4>Total de la commande: ${commande.total} MAD</h4>
            </div>

            <!-- Bouton pour passer au paiement -->
            <div class="col-12 mt-4 text-center">
                <a href="paymentPage.jsp" class="btn hvr-hover">Passer au paiement</a>
            </div>
        </div>
    </div>
</div>
            </div>
        </div>

    <!-- Ajout de la bibliothèque JS pour Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="footer.jsp" />
    <jsp:include page="js.jsp" />
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
