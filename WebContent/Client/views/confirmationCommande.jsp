<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de Commande</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://www.paypal.com/sdk/js?client-id=AQnbLZYrnl6qwXp3nb7mSYjSj7jtIug0wKR_rIJPaPC4moDhlN4TKe62XAJz86HY9FA3FdncWJN3qUXR&currency=MAD&components=buttons&enable-funding=venmo"></script>
</head>

<body>
    <jsp:include page="css.jsp" />
    <jsp:include page="maintophomme.jsp" />
    <jsp:include page="headerhomme.jsp" />

    <div class="cart-box-main">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h2 class="text-center">Confirmation de votre commande</h2>

                    <!-- Détails de la commande -->
                    <div class="col-12 mb-4">
                        <h3>Détails de la commande ${commande.id}</h3>
                        
                        <p><strong>Date de la commande:</strong> ${commande.date}</p>
                        <p><strong>Heure de la commande:</strong> ${commande.heure}</p>
                        <p><strong>Statut de la commande:</strong> ${commande.statut}</p>
                        <p><strong>Total de la commande:</strong> ${commande.total} MAD</p>
                    </div>

                    <!-- Affichage des produits -->
                    <c:if test="${not empty produits}">
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
                    </c:if>

                    <!-- Affichage des offres -->
                    <c:if test="${not empty offres}">
                        <div class="col-12 mt-4">
                            <h3>Offres de votre commande</h3>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">Offre</th>
                                        <th scope="col">Prix Unitaire (MAD)</th>
                                        <th scope="col">Total (MAD)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="offre" items="${offres}">
                                        <tr>
                                            <td>${offre.nom}</td>
                                            <td>${offre.prixPack}</td>
                                            <td>${offre.prixPack }</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <!-- Total de la commande -->
                    <div class="col-12 mt-3">
                        <h4>Total de la commande: ${commande.total} MAD</h4>
                    </div>

                    <!-- Bouton pour passer au paiement -->
                    <div class="col-12 mt-4 text-center">
                        <form action="PaimentServlet" method="POST">
    <input type="hidden" name="user_id" value="${param.user_id}" />
    <input type="hidden" name="totalPanier" value="${commande.total}" />
    <input type="hidden" name="date" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" />
    <input type="hidden" name="heure" value="<%= new SimpleDateFormat("HH:mm").format(new Date()) %>" />
    <button type="submit" class="btn btn-danger hvr-hover">Passer au paiement</button>
</form>

                    </div>

                    
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    <jsp:include page="footer.jsp" />
    <jsp:include page="js.jsp" />
</body>

</html>
