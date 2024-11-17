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

    <!-- Start Promotion  -->


<div id="promotions-section" class="categories-shop"> 
    <div class="container">
        <div class="title-all text-center">
            <h1>Promotions</h1>
            <p>Don't miss our exclusive offers and take advantage of exceptional promotions starting today!</p>
        </div>
        <div id="produitsSlider" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                <c:forEach var="produit" items="${produitsAvecPromotion}" varStatus="status">
                    <c:if test="${status.index % 3 == 0}">
                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                            <div class="row">
                    </c:if>

                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="shop-cat-box">
                            <img class="img-fluid img-equal-size" src="${produit.image}" alt="Image du produit" />
                            <a class="btn hvr-hover" href="#">${produit.nom}<br>${produit.promotion.taux}% de réduction</a>
                        </div>

                        <form action="${pageContext.request.contextPath}/PanierServlet" method="post">
                            <input type="number" name="user_id" value="${param.user_id}">
                            <input type="number" name="produit_id" value="${produit.idProduit}">
                            
                            <!-- Récupérer le promotion_id depuis la Map -->
                            <input type="number" name="promotion_id" value="${produitPromotionMap[produit.idProduit]}"> <!-- Envoie le promotion_id correspondant au produit -->
                            <input type="number" name="quantite" value="1">
                            <input type="number" name="prix" value="${produit.prix * (1 - produit.promotion.taux / 100)}"> <!-- Prix après réduction -->
                            <button type="submit" class="cart">Add Discounted Product to Cart</button>
                        </form>
                    </div>

                    <c:if test="${status.index % 3 == 2 || status.index == produitsAvecPromotion.size() - 1}">
                        </div>
                    </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


                
    <!-- End Promotion   
    <div class="categories-shop">
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                    <div class="shop-cat-box">
                        <img class="img-fluid" src="../images/categories_img_01.jpg" alt="" />
                        <a class="btn hvr-hover" href="#">Lorem ipsum dolor</a>
                    </div>
                </div> -->
   <!-- Start Offre  -->             
<div id="offres-section" class="box-add-products">
    <div class="container">
        <div class="row">
            <!-- Slider pour les offres -->
            <div id="offresSlider" class="carousel slide" data-ride="carousel">
                <div class="carousel-inner">
                    <!-- Chaque slide contient les offres -->
                    <c:forEach var="i" begin="0" end="${offres.size() - 1}">
                        <div class="carousel-item ${i == 0 ? 'active' : ''}">
                            <div class="row">
                                <!-- Cadre pour la première offre -->
                                <div class="col-6">
                                    <div class="offer-section">
                                    	 <a href="${pageContext.request.contextPath}/offreDetail?user_id=${param.user_id}&offreId=${offres[i + 1].idOffre}" class="btn hvr-hover">Voir l'offre</a>
                                        <h3 class="offer-title">${offres[i].nom} - ${offres[i].tauxReduction}% de réduction</h3>
                                        <h1 class="offer-title">Only: ${offres[i].prixPack} DH</h1>
                                        
                                        <h5>Période: ${offres[i].dateDebut} - ${offres[i].dateFin}</h5>
                                        
                                        <div class="offer-products">
                                            <c:forEach var="produit" items="${produitsParOffre[offres[i].idOffre]}">
                                                <div class="product-card">
                                                    <div class="product-name-overlay">
                                                        <h4>${produit.nom}</h4>
                                                    </div>
                                                    <img class="product-image" src="${produit.image != null ? produit.image : 'default-image.jpg'}" alt="Image du produit" />
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <!-- Formulaire d'ajout pour chaque offre (affiché une seule fois par offre) -->
                                        
                                        
                                    </div>
                                    
                                </div>
								
                                <!-- Cadre pour la deuxième offre -->
                                <div class="col-6">
                                    <c:if test="${i + 1 < offres.size()}">
                                        <div class="offer-section">
                                        <a href="${pageContext.request.contextPath}/offreDetail?user_id=${param.user_id}&offreId=${offres[i + 1].idOffre}" class="btn hvr-hover">Voir l'offre</a>
                                            <h3 class="offer-title">${offres[i + 1].nom} - ${offres[i + 1].tauxReduction}% de réduction</h3>
                                            <h1 class="offer-title">Only: ${offres[i + 1].prixPack} DH</h1>
                                            
                                            <h5>Période: ${offres[i + 1].dateDebut} - ${offres[i + 1].dateFin}</h5>
                                            
                                            <div class="offer-products">
                                                <c:forEach var="produit" items="${produitsParOffre[offres[i + 1].idOffre]}">
                                                    <div class="product-card">
                                                        <div class="product-name-overlay">
                                                            <h4>${produit.nom}</h4>
                                                        </div>
                                                        <img class="product-image" src="${produit.image != null ? produit.image : 'default-image.jpg'}" alt="Image du produit" />
                                                    </div>
                                                </c:forEach>
                                                
                                            </div>
                                        </div>
                                        
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
                <!-- Contrôles pour le slider -->
                <a class="carousel-control-prev" href="#offresSlider" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Précédent</span>
                </a>
                <a class="carousel-control-next" href="#offresSlider" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Suivant</span>
                </a>
            </div>
        </div>
    </div>
</div>



      
     <!-- End offre  -->       
    <!-- Start Products  -->
    <div id="products-section" class="products-box">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="title-all text-center">
                    <c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        ${successMessage}
    </div>
</c:if>
                    
                        <h1>Our Products</h1>
                        <p>Experience the freshness of locally grown produce and support sustainable farming – Taste the difference in every bite!</p>
                    </div>
                </div>
            </div>
            <div class="row">
    <div class="col-lg-12">
        <div class="special-menu text-center">
            <div class="button-group filter-button-group">
                <!-- Bouton All pour afficher tous les produits -->
                
                
                <!-- Boutons pour chaque catégorie -->
                <form method="get" action="ListerProduits#products-section" id="categoryForm">
                <input type="hidden" name="user_id" value="${param.user_id}" />
    <button class="active" type="submit" name="idc" value="0" data-page="home">All</button> <!-- All products button -->
    <c:forEach var="categorie" items="${categories}">
        <!-- Submit button for each category -->
        <button class="btn hvr-hover" type="submit" name="idc" value="${categorie.idCategorie}" class="btn" data-toggle="tooltip" title="${categorie.nom}" data-page="home">
            ${categorie.nom}
        </button>
    </c:forEach>
</form>

            </div>
        </div>
    </div>
</div>


            <div class="row special-list">
    <c:forEach var="produit" items="${produits}">
        <div class="col-lg-3 col-md-6 special-grid best-seller">
            <div class="products-single fix">
                <div class="box-img-hover">
                    <div class="type-lb">
                        <!-- Optionally, you can add a condition for sale, new, etc. -->
                        <p class="sale">Sale</p>
                    </div>
                    <!-- Display the image dynamically -->
                    <img src="${produit.image}" class="img-fluid" alt="Image du produit">
                    <div class="mask-icon">
                        <ul>
                            <li><a href="#" data-toggle="tooltip" data-placement="right" title="View"><i class="fas fa-eye"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" data-placement="right" title="Compare"><i class="fas fa-sync-alt"></i></a></li>
                            <li><a href="#" data-toggle="tooltip" data-placement="right" title="Add to Wishlist"><i class="far fa-heart"></i></a></li>
                        </ul>
                        <form action="${pageContext.request.contextPath}/PanierServlet" method="post">
    <input type="number" name="user_id" value="${param.user_id}">
    <input type="number" name="idc" value="${param.idc}">
    <input type="number" name="produit_id" value="${produit.idProduit}">
    <input type="number" name="quantite" value="1">
    <input type="number" name="prix" value="${produit.prix}">
    <button type="submit" class="cart">Add to Cart</button>
</form>


                    </div>
                </div>
                <div class="why-text">
                    <!-- Display the product name and price dynamically -->
                    <h4>${produit.nom}</h4>
                    <h5>${produit.prix} MAD</h5>
                </div>
            </div>
        </div>
    </c:forEach>
</div>


               <div class="row special-list">
    <c:if test="${not empty produitsParCategorie}">
        <c:forEach var="produit" items="${produitsParCategorie}">
            <div class="col-lg-3 col-md-6 special-grid best-seller">
                <div class="products-single fix">
                    <div class="box-img-hover">
                        <!-- Vous pouvez ajouter une condition si le produit est en promotion -->
                        <div class="type-lb">
                            <p class="sale">Sale</p>
                        </div>
                        <!-- Affichage dynamique de l'image du produit -->
                        <img src="${produit.image}" class="img-fluid" alt="${produit.nom}">
                        <div class="mask-icon">
                            <ul>
                                <li><a href="#" data-toggle="tooltip" data-placement="right" title="View"><i class="fas fa-eye"></i></a></li>
                                <li><a href="#" data-toggle="tooltip" data-placement="right" title="Compare"><i class="fas fa-sync-alt"></i></a></li>
                                <li><a href="#" data-toggle="tooltip" data-placement="right" title="Add to Wishlist"><i class="far fa-heart"></i></a></li>
                            </ul>
                            <form action="${pageContext.request.contextPath}/PanierServlet" method="post">
    <input type="number" name="user_id" value="${param.user_id}">
        <input type="number" name="idc" value="${param.idc}">
        <input type="number" name="quantite" value="1">
        <input type="number" name="prix" value="${produit.prix}">
    <input type="number" name="produit_id" value="${produit.idProduit}">
    <button type="submit" class="cart">Add to Cart</button>
</form>


                        </div>
                    </div>
                    <div class="why-text">
                        <!-- Affichage dynamique du nom et prix du produit -->
                        <h4>${produit.nom}</h4>
                        <h5>${produit.prix} MAD</h5>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty produitsParCategorie}">
        <p></p>
    </c:if>
</div>
 </div>
    </div>
</div>
    <!-- End Products  -->

    <!-- Start Blog  -->
    <div class="latest-blog">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="title-all text-center">
                        <h1>latest blog</h1>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sit amet lacus enim.</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="blog-box">
                        <div class="blog-img">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/Client/images/blog-img.jpg" alt="" />
                        </div>
                        <div class="blog-content">
                            <div class="title-blog">
                                <h3>What's in Season? Fall Fruits & Vegetables</h3>
                                <p>This blog highlights the best seasonal fruits and vegetables in autumn, such as apples, cranberries, kale, and eggplant. It also provides useful tips on how to store them and delicious recipe ideas. Check it out for a comprehensive guide to fall produce.</p>
                            </div>
                            <ul class="option-blog">
                                <li><a href="#"><i class="far fa-heart"></i></a></li>
                                <li><a href="#"><i class="fas fa-eye"></i></a></li>
                                <li><a href="#"><i class="far fa-comments"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="blog-box">
                        <div class="blog-img">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/Client/images/blog-img-01.jpg" alt="" />
                        </div>
                        <div class="blog-content">
                            <div class="title-blog">
                                <h3>Seasonal Fall Fruits and Vegetables: 10 Healthiest Choices</h3>
                                <p>A great resource for finding healthy autumn produce. The blog discusses the benefits of foods like kale, acorn squash, and figs, offering insights into their health advantages and tips for incorporating them into meals.</p>
                            </div>
                            <ul class="option-blog">
                                <li><a href="#"><i class="far fa-heart"></i></a></li>
                                <li><a href="#"><i class="fas fa-eye"></i></a></li>
                                <li><a href="#"><i class="far fa-comments"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-lg-4 col-xl-4">
                    <div class="blog-box">
                        <div class="blog-img">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/Client/images/blog-img-02.jpg" alt="" />
                        </div>
                        <div class="blog-content">
                            <div class="title-blog">
                                <h3>What’s in Season in Fall: Healthy Produce to Eat</h3>
                                <p>This blog goes beyond the basics, providing a variety of fall fruits and vegetables with information on storage and creative ways to prepare them. Featured produce includes everything from pears and pomegranates to Brussels sprouts and broccoli.</p>
                            </div>
                            <ul class="option-blog">
                                <li><a href="#"><i class="far fa-heart"></i></a></li>
                                <li><a href="#"><i class="fas fa-eye"></i></a></li>
                                <li><a href="#"><i class="far fa-comments"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- End Blog  -->


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