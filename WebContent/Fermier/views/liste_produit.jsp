             
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="css.jsp" />
<jsp:include page="maintophomme.jsp" />
<jsp:include page="headerhomme.jsp" />
<div id="slides-shop" class="cover-slides">
        <ul class="slides-container">
            <li class="text-center">
                <img src="${pageContext.request.contextPath}/Client/images/banner-01.jpg" alt="">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="m-b-20"><strong>Welcome To <br> Freshshop</strong></h1>
                            <p class="m-b-40">where every bite supports a healthier you and a stronger community!</p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="text-center">
                <img src="${pageContext.request.contextPath}/Client/images/banner-02.jpg" alt="">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="m-b-20"><strong>Welcome To <br> Freshshop</strong></h1>
                            <p class="m-b-40">where every bite supports a healthier you and a stronger community!</p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="text-center">
                <img src="${pageContext.request.contextPath}/Client/images/banner-03.jpg" alt="">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h1 class="m-b-20"><strong>Welcome To <br> Freshshop</strong></h1>
                            <p class="m-b-40">where every bite supports a healthier you and a stronger community!</p>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div class="slides-navigation">
            <a href="#" class="next"><i class="fa fa-angle-right" aria-hidden="true"></i></a>
            <a href="#" class="prev"><i class="fa fa-angle-left" aria-hidden="true"></i></a>
        </div>
    </div>
    <!-- End Slider --> 

    <div class="container mt-4">
        <h1 class="text-center mb-4">Liste des Produits du Fermier</h1>

        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mb-3">Retour à l'accueil</a>

        <c:if test="${empty produits}">
            <div class="alert alert-warning text-center">
                <p>Aucun produit trouvé pour ce fermier.</p>
            </div>
        </c:if>

        <c:if test="${not empty produits}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Nom</th>
                            <th>Prix (DH)</th>
                            <th>Quantité</th>
                            <th>Description</th>
                            <th>Date Récolte</th>
                            <th>Image</th>
                            <th> ACTION</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="produit" items="${produits}">
                            <tr>
                                <td>${produit.nom}</td>
                                <td>${produit.prix}</td>
                                <td>${produit.quantite}</td>
                                <td>${produit.description}</td>
                                <td>${produit.dateRecolte}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty produit.image}">
                                            <img src="${produit.image}" alt="Image produit" class="img-thumbnail" style="max-width: 100px;">
                                        </c:when>
                                        <c:otherwise>
                                            Pas d'image
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                  <td>
                                    <!-- Ajouter Produit -->
                                    <form action="produitservlet" method="get" class="d-inline">
                                        <input type="hidden" name="action" value="ajouterPromotion">
                                        <input type="hidden" name="id_produit" value="${produit.idProduit}">
                                        <input type="hidden" name="idFermier" value="${idFermier}">
                                        <button type="submit" class="btn btn-success btn-sm">Ajouter Promotion</button>
                                    </form>
                                    <!-- Supprimer ou Modifier (si requis) -->
                                    
                                </td>
                             
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <!-- Bouton pour ajouter un produit -->
        <div class="text-center mt-4">
        <a  href="${pageContext.request.contextPath}/produitservlet?action=affichercat&idFermier=${idFermier}" class="btn btn-primary">Ajouter un Nouveau Produit</a>
        </div>
    </div>
   <!-- Start Instagram Feed  -->
    <div class="instagram-box">
        <div class="main-instagram owl-carousel owl-theme">
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-01.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-02.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-03.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-04.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-05.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-06.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-07.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-08.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-09.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
            <div class="item">
                <div class="ins-inner-box">
                    <img src="${pageContext.request.contextPath}/Client/images/instagram-img-05.jpg" alt="" />
                    <div class="hov-in">
                        <a href="#"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End Instagram Feed  -->
<jsp:include page="footer.jsp" />

<div class="footer-copyright">
        <p class="footer-company">All Rights Reserved. &copy; 2018 <a href="#">ThewayShop</a> Design By :
            <a href="https://html.design/">html design</a></p>
    </div>
    <!-- End copyright  -->

    <a href="#" id="back-to-top" title="Back to top" style="display: none;">&uarr;</a>
    <jsp:include page="js.jsp" />
    
    <style>
    
    .img-equal-size {
    width: 100%;          /* Utilise 100% de la largeur disponible */
    height: 350px;        /* Fixe la hauteur à 200px, ajustez la valeur selon votre besoin */
    object-fit: cover;    /* Garantit que l'image couvre toute la zone sans déformation */
}

.img-equal-size2 {
    width: 500px;          /* Utilise 100% de la largeur disponible */
    height: 300px;        /* Fixe la hauteur à 200px, ajustez la valeur selon votre besoin */
    object-fit: cover;    /* Garantit que l'image couvre toute la zone sans déformation */
}

   
    
    </style>
    <script>
    $(document).ready(function(){
        $('#produitsSlider').carousel({
            interval: 1000 // Défilement toutes les 2 secondes
        });
    });
    
const buttons = document.querySelectorAll('button[type="submit"]');
    
    buttons.forEach(button => {
        button.addEventListener('click', function(e) {
            // Create a hidden input for 'page' dynamically
            const pageInput = document.createElement('input');
            pageInput.type = 'hidden';
            pageInput.name = 'page';
            pageInput.value = button.getAttribute('data-page');
            
            // Append the hidden input to the form before submitting
            document.getElementById('categoryForm').appendChild(pageInput);
        });
    });

</script>
<style>
/* Ajustement de la taille des images des produits */
.product-image-container {
    position: relative;
    width: 100%;
    height: 100px; /* Taille ajustée des images */
    overflow: hidden;
    border-radius: 10px; /* Coins arrondis pour l'image */
}

.product-image {
    width: 80%;
    height: 80%;
    object-fit: cover; /* Permet de garder les proportions des images sans les déformer */
}

/* Overlay contenant le nom du produit */
.product-name-overlay {
    position: absolute;
    top: 5px; /* Positionnement du nom en haut de l'image */
    left: 10px;
    background-color: rgba(255, 255, 255, 0.5); /* Fond semi-transparent pour lisibilité */
    color: white;
    padding: 5px;
    border-radius: 5px;
    font-size: 16px;
    width: auto;
}

/* Container des produits dans chaque offre */
.offer-products {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.product-card {
    width: 48%; /* Largeur de chaque produit dans le cadre */
    margin-bottom: 10px;
    position: relative; /* Nécessaire pour la gestion de l'overlay */
}

/* Mise en page des offres */
.offer-section {
    padding: 20px;
    background-color: #f8f8f8;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.offer-title {
    text-align: center;
    font-size: 20px;
    color: #333;
}

.offer-title h1 {
    font-size: 24px;
    margin-top: 10px;
    font-weight: bold;
}

.offer-title h5 {
    font-size: 16px;
    color: #777;
    text-align: center;
}

.offer-section .btn {
        display: block;
    }

</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>