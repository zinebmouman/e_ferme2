<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Détails de l'Offre</title>
</head>
<body>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />
<div class="shop-detail-box-main">
    <div class="container">
        <div class="row">
            <div class="col-xl-5 col-lg-5 col-md-6">
                <div id="carousel-example-1" class="single-product-slider carousel slide" data-ride="carousel">
                    <div class="carousel-inner" role="listbox">
                        <c:forEach var="produit" items="${produitsAvecOffre}" varStatus="status">
                            <div class="carousel-item ${status.first ? 'active' : ''}">
                                <img class="d-block w-100" src="${produit.image}" alt="${produit.nom}">
                            </div>
                        </c:forEach>
                    </div>
                    <a class="carousel-control-prev" href="#carousel-example-1" role="button" data-slide="prev">
                        <i class="fa fa-angle-left" aria-hidden="true"></i>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carousel-example-1" role="button" data-slide="next">
                        <i class="fa fa-angle-right" aria-hidden="true"></i>
                        <span class="sr-only">Next</span>
                    </a>
                    <ol class="carousel-indicators">
                        <c:forEach var="produit" items="${produitsAvecOffre}" varStatus="status">
                            <li data-target="#carousel-example-1" data-slide-to="${status.index}" class="${status.first ? 'active' : ''}">
                                <img class="d-block w-100 img-fluid" src="${produit.image}" alt="${produit.nom}">
                            </li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
            <div class="col-xl-7 col-lg-7 col-md-6">
                <div class="single-product-details">
                    <h2>${offre.nom}</h2>
                    <h5><del>${offre.prixPack + (offre.prixPack * offre.tauxReduction / 100)}</del> ${offre.prixPack}</h5>
                    <p class="available-stock"><span>Date de début: ${offre.dateDebut} / Date de fin: ${offre.dateFin}</span></p>
                    <h4>Description:</h4>
                    <p>${offre.description}</p>
                    
                    <h4>Produits associés:</h4>
                    <ul>
                        <c:forEach var="produit" items="${produitsAvecOffre}">
                            <li>
                                <h5>${produit.nom}</h5>
                                <p>Prix: ${produit.prix}</p>
                                <p>Description: ${produit.description}</p>
                            </li>
                        </c:forEach>
                    </ul>
                    
                    <form action="${pageContext.request.contextPath}/PanierServlet" method="post">
                        <input type="hidden" name="user_id" value="${param.user_id}" required>
                        <input type="hidden" name="offre_id" value="${offre.idOffre}">
                        <input type="hidden" name="produit_id" value="${produit != null ? produit.idProduit : ''}">
                        <div class="form-group quantity-box">
                            <label class="control-label">Quantité</label>
                            <input class="form-control" type="number" name="quantite" value="1" min="1">
                        </div>
                        <input type="hidden" name="prix" value="${offre.prixPack}" required>
                        <button type="submit" class="btn btn-danger hvr-hover">Add Offer to Cart</button>
                    </form>

                    <div class="add-to-btn">
                        <div class="add-comp">
                            <a class="btn hvr-hover" href="#"><i class="fas fa-heart"></i> Add to wishlist</a>
                            <a class="btn hvr-hover" href="#"><i class="fas fa-sync-alt"></i> Add to Compare</a>
                        </div>
                        <div class="share-bar">
                            <a class="btn hvr-hover" href="#"><i class="fab fa-facebook" aria-hidden="true"></i></a>
                            <a class="btn hvr-hover" href="#"><i class="fab fa-google-plus" aria-hidden="true"></i></a>
                            <a class="btn hvr-hover" href="#"><i class="fab fa-twitter" aria-hidden="true"></i></a>
                            <a class="btn hvr-hover" href="#"><i class="fab fa-pinterest-p" aria-hidden="true"></i></a>
                            <a class="btn hvr-hover" href="#"><i class="fab fa-whatsapp" aria-hidden="true"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   </div>
<jsp:include page="footer.jsp" />
<jsp:include page="js.jsp" />
</body>
</html>
