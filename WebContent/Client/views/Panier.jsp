<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<html>
<head>
    <title>Panier</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
</head>
<body>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />

<!-- Start Cart -->
<div class="cart-box-main">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="table-main table-responsive">

                    <!-- Affichage des produits -->
                    <c:if test="${not empty produits}">
                        <h3>Produits</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Images</th>
                                    <th>Nom du produit</th>
                                    <th>Prix</th>
                                    <th>Quantité</th>
                                    <th>Mettre à jour</th>
                                    <th></th>
                                    <th>Total</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="totalPanier" value="0"/>
                                <c:forEach var="produit" items="${produits}">
                                    <c:if test="${not empty produit.idProduit && empty produit.offre}">
                                        <!-- Code pour afficher les produits normaux -->
                                        <tr>
                                            <td class="thumbnail-img">
                                                <a href="#">
                                                    <img class="img-fluid" src="${produit.image}" alt="${produit.nom}" />
                                                </a>
                                            </td>
                                            <td class="name-pr">
                                                <a href="#">${produit.nom}</a>
                                            </td>
                                            <td class="price-pr">
                                                
                                                <c:set var="prix" value="0"/>
                                                <c:forEach var="panier" items="${paniers}">
                                                    <c:if test="${produit.idProduit == panier.produitId}">
                                                        <c:set var="prix" value="${panier.prixPanier}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <p>${prix} MAD
                                                </p>
                                            </td>
                                            <td class="quantity-box">
                                                <c:set var="quantite" value="0"/>
                                                <c:forEach var="panier" items="${paniers}">
                                                    <c:if test="${produit.idProduit == panier.produitId}">
                                                        <c:set var="quantite" value="${panier.quantite}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <form action="PanierServlet" method="post">
    <!-- Champ caché pour indiquer que l'action doit être de type "PUT" -->
    <input type="hidden" name="_method" value="PUT">
    
    <!-- Paramètres nécessaires pour identifier l'utilisateur et le produit -->
    <input type="hidden" name="user_id" value="${param.user_id}">
    <input type="hidden" name="produit_id" value="${produit.idProduit}">
    <c:set var="prixpanier" value="0"/>
                                                <c:forEach var="panier" items="${paniers}">
                                                    <c:if test="${produit.idProduit == panier.produitId}">
                                                        <c:set var="prixpanier" value="${panier.prixPanier}"/>
                                                    </c:if>
                                                </c:forEach>
    <input type="hidden" name="prix" value="${prixpanier}">

    <label for="quantite">Quantité :</label>
    <input  type="number" size="4" name="quantite"  value="${quantite}" step="1" min="1" class="c-input-text qty text" required>

    <button type="submit" class="btn btn-danger hvr-hover">Update</button>
</form>

                                            </td>
                                             <td></td>
                                             <td></td>
                                            <td class="total-pr">
                                            	
        <c:set var="prix" value="0"/>
                                                <c:forEach var="panier" items="${paniers}">
                                                    <c:if test="${produit.idProduit == panier.produitId}">
                                                        <c:set var="prix" value="${panier.prixPanier}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:set var="total" value="${prix * quantite}"/>
                                                <p>${total} MAD</p>
                                                <c:set var="totalPanier" value="${totalPanier + total}"/>
                                            </td>
                                            <td class="remove-pr">
    <form method="post" action="PanierServlet" onsubmit="return confirm('Voulez-vous vraiment supprimer cet article ?');">
        <input type="hidden" name="_method" value="delete"> <!-- Indique que la requête est une suppression -->
        <input type="hidden" name="produit_id" value="${produit.idProduit}"> <!-- L'ID du produit à supprimer -->
        <input type="hidden" name="user_id" value="${param.user_id}">
                                                
        <input type="hidden" name="prix" value="${prix}"> <!-- L'ID du consommateur -->
        <button type="submit" class="delete-button" style="background: none; border: none; padding: 0;">
            <i class="fas fa-times" style="color: red; cursor: pointer;"></i> <!-- Icône de suppression -->
        </button>
    </form>
</td>
                                            
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>

                    <!-- Affichage des offres -->
                    <!-- Affichage des offres -->
<c:if test="${not empty offres}">
    <h3>Offres</h3>
    <table class="table">
        <thead>
            <tr>
                <th>Nom de l'offre</th>
                <th>Prix</th>
                <th>Quantité</th>
                <th>Total</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="totalPanierOffres" value="0"/>
            <c:forEach var="offre" items="${offres}">
                <c:if test="${not empty offre}">
                    <!-- Code pour afficher les offres -->
                    <tr>
                        <td class="name-pr">
                            <a href="#">${offre.nom}</a>
                        </td>
                        <td class="price-pr">
                            <p>${offre.prixPack} MAD</p>
                        </td>
                        <td class="quantity-box">
                            <c:set var="quantite" value="0"/>
                            <c:forEach var="panier" items="${paniers}">
                                <c:if test="${offre.idOffre == panier.offreId}">
                                    <c:set var="quantite" value="${panier.quantite}"/>
                                </c:if>
                            </c:forEach>
                            <p  class="c-input-text qty text">${quantite}</p>
                            <!-- Formulaire pour la mise à jour de la quantité de l'offre 
                            <form action="PanierServlet" method="post">
                                
                                <input type="hidden" name="_method" value="PUT">

                                
                                <input type="hidden" name="user_id" value="${param.user_id}">
                                <input type="hidden" name="offer_id" value="${offre.idOffre}">
                                <input type="hidden" name="prix" value="${offre.prixPack}">

                                
                                <label for="quantite">Quantité :</label>
                                <input type="number" name="quantite" value="${quantite}" step="1" min="1" class="c-input-text qty text" required>

                                <button type="submit" class="btn btn-primary">Mettre à jour</button>
                            </form>-->
                        </td>
                        
                        <td class="total-pr">
                            <c:set var="total" value="${offre.prixPack * quantite}"/>
                            <p>${total} MAD</p>
                            <c:set var="totalPanierOffres" value="${totalPanierOffres + total}"/>
                        </td>
                        <td class="remove-pr">
    <form method="post" action="PanierServlet" onsubmit="return confirm('Voulez-vous vraiment supprimer cet article ?');">
        <input type="hidden" name="_method" value="delete"> <!-- Indique que la requête est une suppression -->
        <input type="hidden" name="user_id" value="${param.user_id}"> 
        <input type="hidden" name="prix" value="${offre.prixPack}"><!-- L'ID du consommateur -->
        <input type="hidden" name="offre_id" value="${offre.idOffre}"> <!-- Si applicable, l'ID de l'offre associée -->
        <button type="submit" class="delete-button" style="background: none; border: none; padding: 0;">
            <i class="fas fa-times" style="color: red; cursor: pointer;"></i> <!-- Icône de suppression -->
        </button>
    </form>
</td>
                        
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</c:if>

                    <!-- Total Panier -->
                    <div class="total-panier">
                        <h3>Total du Panier</h3>
                        <p>Total Produits : ${totalPanier} MAD</p>
                        <p>Total Offres : ${totalPanierOffres} MAD</p>
                        <p>Total général : ${totalPanier + totalPanierOffres} MAD</p>
                        <form method="post" action="CommanderServlet?user_id=${param.user_id}">
                            <input type="hidden" name="totalPanier" value="${totalPanier + totalPanierOffres}">
                            <input type="hidden" name="user_id" value="${param.user_id}">
                            
    <input type="hidden" name="date" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
    
    
    <input type="hidden" name="heure" value="<%= new SimpleDateFormat("HH:mm").format(new Date()) %>">

                            <button type="submit" class="btn btn-danger hvr-hover">Commander</button>
                        </form>


                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- End Cart -->

<jsp:include page="footer.jsp" />
<jsp:include page="js.jsp" />
</body>
</html>
